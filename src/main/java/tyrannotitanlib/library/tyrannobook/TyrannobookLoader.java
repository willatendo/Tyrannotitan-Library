package tyrannotitanlib.library.tyrannobook;

import java.lang.reflect.Type;
import java.util.HashMap;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.conditions.ICondition;
import tyrannotitanlib.content.TyrannoUtils;
import tyrannotitanlib.library.tyrannobook.action.StringActionProcessor;
import tyrannotitanlib.library.tyrannobook.action.protocol.ProtocolGoToPage;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.data.content.BlankContent;
import tyrannotitanlib.library.tyrannobook.data.content.BlockInteractionContent;
import tyrannotitanlib.library.tyrannobook.data.content.ContentPadding;
import tyrannotitanlib.library.tyrannobook.data.content.CraftingContent;
import tyrannotitanlib.library.tyrannobook.data.content.ImageContent;
import tyrannotitanlib.library.tyrannobook.data.content.ImageTextContent;
import tyrannotitanlib.library.tyrannobook.data.content.IndexContent;
import tyrannotitanlib.library.tyrannobook.data.content.PageContent;
import tyrannotitanlib.library.tyrannobook.data.content.ShowcaseContent;
import tyrannotitanlib.library.tyrannobook.data.content.SmeltingContent;
import tyrannotitanlib.library.tyrannobook.data.content.SmithingContent;
import tyrannotitanlib.library.tyrannobook.data.content.StructureContent;
import tyrannotitanlib.library.tyrannobook.data.content.TextContent;
import tyrannotitanlib.library.tyrannobook.data.content.TextImageContent;
import tyrannotitanlib.library.tyrannobook.data.content.TextLeftImageContent;
import tyrannotitanlib.library.tyrannobook.data.content.TextRightImageContent;
import tyrannotitanlib.library.tyrannobook.data.deserializer.ConditionDeserializer;
import tyrannotitanlib.library.tyrannobook.data.deserializer.HexStringDeserializer;
import tyrannotitanlib.library.tyrannobook.data.element.IngredientData;
import tyrannotitanlib.library.tyrannobook.repository.BookRepository;
import tyrannotitanlib.library.tyrannobook.transformer.IndexTransformer;
import tyrannotitanlib.library.tyrannobook.transformer.TyrannobookTransformer;
import tyrannotitanlib.library.tyrannonetwork.Tyrannonetwork;
import tyrannotitanlib.library.tyrannonetwork.packets.UpdateHeldPagePacket;
import tyrannotitanlib.library.tyrannonetwork.packets.UpdateLecternPagePacket;

public class TyrannobookLoader implements ResourceManagerReloadListener {
	private static Gson gson;
	private static boolean gsonDirty = true;
	private static final HashMap<Type, Object> gsonTypeAdapters = new HashMap<>();

	private static final HashMap<ResourceLocation, Class<? extends PageContent>> typeToContentMap = new HashMap<>();

	private static final HashMap<ResourceLocation, TyrannobookData> books = new HashMap<>();

	public TyrannobookLoader() {
		registerPageType(BlankContent.ID, BlankContent.class);
		registerPageType(TextContent.ID, TextContent.class);
		registerPageType(ContentPadding.LEFT_ID, ContentPadding.ContentLeftPadding.class);
		registerPageType(ContentPadding.RIGHT_ID, ContentPadding.ContentRightPadding.class);
		registerPageType(ImageContent.ID, ImageContent.class);
		registerPageType(ImageTextContent.ID, ImageTextContent.class);
		registerPageType(TextImageContent.ID, TextImageContent.class);
		registerPageType(TextLeftImageContent.ID, TextLeftImageContent.class);
		registerPageType(TextRightImageContent.ID, TextRightImageContent.class);
		registerPageType(CraftingContent.ID, CraftingContent.class);
		registerPageType(SmeltingContent.ID, SmeltingContent.class);
		registerPageType(SmithingContent.ID, SmithingContent.class);
		registerPageType(BlockInteractionContent.ID, BlockInteractionContent.class);
		registerPageType(StructureContent.ID, StructureContent.class);
		registerPageType(IndexContent.ID, IndexContent.class);
		registerPageType(ShowcaseContent.ID, ShowcaseContent.class);

		StringActionProcessor.registerProtocol(TyrannoUtils.rL("go-to-page"), new ProtocolGoToPage(false));
		StringActionProcessor.registerProtocol(TyrannoUtils.rL("go-to-page-rtn"), new ProtocolGoToPage(true));

		registerGsonTypeAdapter(ResourceLocation.class, ResourceLocationSerializer.resourceLocation(TyrannoUtils.TYRANNO_ID));
		registerGsonTypeAdapter(int.class, new HexStringDeserializer());
		registerGsonTypeAdapter(ICondition.class, new ConditionDeserializer());
		registerGsonTypeAdapter(IngredientData.class, new IngredientData.Deserializer());

		IndexTransformer.addHiddenPageType(BlankContent.ID);
		IndexTransformer.addHiddenPageType(ContentPadding.LEFT_ID);
		IndexTransformer.addHiddenPageType(ContentPadding.RIGHT_ID);
		IndexTransformer.addHiddenPageType(IndexContent.ID);
	}

	public static void registerPageType(ResourceLocation id, Class<? extends PageContent> clazz) {
		if (typeToContentMap.containsKey(id)) {
			throw new IllegalArgumentException("Page type " + id + " already in use.");
		}

		typeToContentMap.put(id, clazz);
	}

	@Nullable
	public static Class<? extends PageContent> getPageType(ResourceLocation name) {
		return typeToContentMap.get(name);
	}

	public static TyrannobookData registerBook(ResourceLocation id, BookRepository... repositories) {
		return registerBook(id, true, true, repositories);
	}

	public static TyrannobookData registerBook(ResourceLocation id, boolean appendIndex, boolean appendContentTable, BookRepository... repositories) {
		TyrannobookData info = new TyrannobookData(repositories);

		if (appendIndex) {
			info.addTransformer(TyrannobookTransformer.indexTranformer());
		}
		if (appendContentTable) {
			info.addTransformer(TyrannobookTransformer.contentTableTransformer());
		}

		books.put(id, info);
		return info;
	}

	@Nullable
	public static TyrannobookData getBook(ResourceLocation id) {
		return books.getOrDefault(id, null);
	}

	public static void updateSavedPage(@Nullable Player player, InteractionHand hand, String page) {
		if (player != null) {
			ItemStack item = player.getItemInHand(hand);
			if (!item.isEmpty()) {
				TyrannobookHelper.writeSavedPageToBook(item, page);
				Tyrannonetwork.INSTANCE.network.sendToServer(new UpdateHeldPagePacket(hand, page));
			}
		}
	}

	public static void updateSavedPage(BlockPos pos, String page) {
		Tyrannonetwork.INSTANCE.network.sendToServer(new UpdateLecternPagePacket(pos, page));
	}

	public static Gson getGson() {
		if (gson == null || gsonDirty) {
			GsonBuilder builder = new GsonBuilder();

			for (Type type : gsonTypeAdapters.keySet()) {
				builder.registerTypeAdapter(type, gsonTypeAdapters.get(type));
			}

			gson = builder.create();
			gsonDirty = false;
		}

		return gson;
	}

	public static void registerGsonTypeAdapter(Type type, Object adapter) {
		gsonTypeAdapters.put(type, adapter);
		gsonDirty = true;
	}

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		books.forEach((s, bookData) -> bookData.reset());
	}
}
