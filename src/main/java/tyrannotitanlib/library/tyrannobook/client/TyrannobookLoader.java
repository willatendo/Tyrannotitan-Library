package tyrannotitanlib.library.tyrannobook.client;

import java.util.HashMap;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModLoadingContext;
import tyrannotitanlib.library.tyrannobook.client.action.StringActionProcessor;
import tyrannotitanlib.library.tyrannobook.client.action.protocol.ProtocolGoToPage;
import tyrannotitanlib.library.tyrannobook.client.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.client.data.content.BlankContent;
import tyrannotitanlib.library.tyrannobook.client.data.content.BlockInteractionContent;
import tyrannotitanlib.library.tyrannobook.client.data.content.ContentCrafting;
import tyrannotitanlib.library.tyrannobook.client.data.content.ContentImage;
import tyrannotitanlib.library.tyrannobook.client.data.content.ContentImageText;
import tyrannotitanlib.library.tyrannobook.client.data.content.ContentSmelting;
import tyrannotitanlib.library.tyrannobook.client.data.content.ContentSmithing;
import tyrannotitanlib.library.tyrannobook.client.data.content.ContentStructure;
import tyrannotitanlib.library.tyrannobook.client.data.content.ContentText;
import tyrannotitanlib.library.tyrannobook.client.data.content.ContentTextImage;
import tyrannotitanlib.library.tyrannobook.client.data.content.ContentTextLeftImage;
import tyrannotitanlib.library.tyrannobook.client.data.content.ContentTextRightImage;
import tyrannotitanlib.library.tyrannobook.client.data.content.PageContent;
import tyrannotitanlib.library.tyrannobook.client.data.deserialiser.HexStringDeserialiser;
import tyrannotitanlib.library.tyrannobook.client.repository.TyrannobookRepository;
import tyrannotitanlib.library.tyrannonetwork.Tyrannonetwork;
import tyrannotitanlib.library.tyrannonetwork.packets.UpdateHeldPagePacket;
import tyrannotitanlib.library.tyrannonetwork.packets.UpdateLecturnPagePacket;

@OnlyIn(Dist.CLIENT)
public class TyrannobookLoader implements ResourceManagerReloadListener {
	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(int.class, new HexStringDeserialiser()).create();

	private static final HashMap<String, Class<? extends PageContent>> typeToContentMap = new HashMap<>();

	private static final HashMap<String, TyrannobookData> books = new HashMap<>();

	public TyrannobookLoader() {
		registerPageType("blank", BlankContent.class);
		registerPageType("text", ContentText.class);
		registerPageType("image", ContentImage.class);
		registerPageType("image_with_text_below", ContentImageText.class);
		registerPageType("text_with_image_below", ContentTextImage.class);
		registerPageType("text_with_left_image etch", ContentTextLeftImage.class);
		registerPageType("text_with_right_image etch", ContentTextRightImage.class);
		registerPageType("crafting", ContentCrafting.class);
		registerPageType("smelting", ContentSmelting.class);
		registerPageType("smithing", ContentSmithing.class);
		registerPageType("block_interaction", BlockInteractionContent.class);
		registerPageType(ContentStructure.ID, ContentStructure.class);

		StringActionProcessor.registerProtocol(new ProtocolGoToPage());
		StringActionProcessor.registerProtocol(new ProtocolGoToPage(true, ProtocolGoToPage.GO_TO_RTN));
	}

	public static void registerPageType(String name, Class<? extends PageContent> clazz) {
		if (typeToContentMap.containsKey(name)) {
			throw new IllegalArgumentException("Page type " + name + " already in use.");
		}

		typeToContentMap.put(name, clazz);
	}

	@Nullable
	public static Class<? extends PageContent> getPageType(String name) {
		return typeToContentMap.get(name);
	}

	public static TyrannobookData registerBook(String name, TyrannobookRepository repositories) {
		return registerBook(name, true, true, repositories);
	}

	public static TyrannobookData registerBook(String name, boolean appendIndex, boolean appendContentTable, TyrannobookRepository... repositories) {
		TyrannobookData info = new TyrannobookData(repositories);

		books.put(name.contains(":") ? name : ModLoadingContext.get().getActiveContainer().getNamespace() + ":" + name, info);

		if (appendIndex) {
			info.addTransformer(TyrannobookTransformer.indexTranformer());
		}
		if (appendContentTable) {
			info.addTransformer(TyrannobookTransformer.contentTableTransformer());
		}

		return info;
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
		Tyrannonetwork.INSTANCE.network.sendToServer(new UpdateLecturnPagePacket(pos, page));
	}

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		books.forEach((s, bookData) -> bookData.reset());
	}
}
