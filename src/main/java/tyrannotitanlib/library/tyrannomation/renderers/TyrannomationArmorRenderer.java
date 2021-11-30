package tyrannotitanlib.library.tyrannomation.renderers;

import java.awt.Color;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomation.core.processor.IBone;
import tyrannotitanlib.library.tyrannomation.model.TyrannomatedTyrannomationModel;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;
import tyrannotitanlib.library.tyrannomation.util.TyrannomationUtils;

public abstract class TyrannomationArmorRenderer<T extends ArmorItem & ITyrannomatable> extends HumanoidModel implements ITyrannomationRenderer<T> 
{
	private static Map<Class<? extends ArmorItem>, TyrannomationArmorRenderer> renderers = new ConcurrentHashMap<>();

	static 
	{
		TyrannomationController.addModelFetcher((ITyrannomatable object) -> 
		{
			if(object instanceof ArmorItem) 
			{
				TyrannomationArmorRenderer renderer = renderers.get(object.getClass());
				return renderer == null ? null : renderer.getGeoModelProvider();
			}
			return null;
		});
	}

	protected T currentArmorItem;
	protected LivingEntity entityLiving;
	protected ItemStack itemStack;
	protected EquipmentSlot armorSlot;

	public String headBone = "armorHead";
	public String bodyBone = "armorBody";
	public String rightArmBone = "armorRightArm";
	public String leftArmBone = "armorLeftArm";
	public String rightLegBone = "armorRightLeg";
	public String leftLegBone = "armorLeftLeg";
	public String rightBootBone = "armorRightBoot";
	public String leftBootBone = "armorLeftBoot";

	public static void registerArmorRenderer(Class<? extends ArmorItem> itemClass, TyrannomationArmorRenderer renderer) 
	{
		renderers.put(itemClass, renderer);
	}

	public static TyrannomationArmorRenderer getRenderer(Class<? extends ArmorItem> item) 
	{
		final TyrannomationArmorRenderer renderer = renderers.get(item);
		if(renderer == null) 
		{
			throw new IllegalArgumentException("Renderer not registered for item " + item);
		}
		return renderer;
	}

	private final TyrannomatedTyrannomationModel<T> modelProvider;

	public TyrannomationArmorRenderer(TyrannomatedTyrannomationModel<T> modelProvider) 
	{
		super(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
		this.modelProvider = modelProvider;
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) 
	{
		this.render(0, matrixStackIn, bufferIn, packedLightIn);
	}

	public void render(float partialTicks, PoseStack stack, VertexConsumer bufferIn, int packedLightIn) 
	{
		stack.translate(0.0D, 24 / 16F, 0.0D);
		stack.scale(-1.0F, -1.0F, 1.0F);
		TyrannomationModel model = modelProvider.getModel(modelProvider.getModelLocation(currentArmorItem));

		TyrannomationEvent itemEvent = new TyrannomationEvent(this.currentArmorItem, 0, 0, 0, false, Arrays.asList(this.itemStack, this.entityLiving, this.armorSlot));
		modelProvider.setLivingAnimations(currentArmorItem, this.getUniqueID(this.currentArmorItem), itemEvent);
		this.fitToBiped();
		stack.pushPose();
		Minecraft.getInstance().textureManager.bindForSetup(getTextureLocation(currentArmorItem));
		Color renderColor = getRenderColor(currentArmorItem, partialTicks, stack, null, bufferIn, packedLightIn);
		RenderType renderType = getRenderType(currentArmorItem, partialTicks, stack, null, bufferIn, packedLightIn, getTextureLocation(currentArmorItem));
		render(model, currentArmorItem, partialTicks, renderType, stack, null, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
		stack.popPose();
		stack.scale(-1.0F, -1.0F, 1.0F);
		stack.translate(0.0D, -24 / 16F, 0.0D);
	}

	protected void fitToBiped() 
	{
		if(!(this.entityLiving instanceof ArmorStand)) 
		{
			if(this.headBone != null) 
			{
				IBone headBone = this.modelProvider.getBone(this.headBone);
				TyrannomationUtils.copyRotations(this.head, headBone);
				headBone.setPositionX(this.head.x);
				headBone.setPositionY(-this.head.y);
				headBone.setPositionZ(this.head.z);
			}

			if(this.bodyBone != null) 
			{
				IBone bodyBone = this.modelProvider.getBone(this.bodyBone);
				TyrannomationUtils.copyRotations(this.body, bodyBone);
				bodyBone.setPositionX(this.body.x);
				bodyBone.setPositionY(-this.body.y);
				bodyBone.setPositionZ(this.body.z);
			}

			if(this.rightArmBone != null) 
			{
				IBone rightArmBone = this.modelProvider.getBone(this.rightArmBone);
				TyrannomationUtils.copyRotations(this.rightArm, rightArmBone);
				rightArmBone.setPositionX(this.rightArm.x + 5);
				rightArmBone.setPositionY(2 - this.rightArm.y);
				rightArmBone.setPositionZ(this.rightArm.z);
			}

			if(this.leftArmBone != null) 
			{
				IBone leftArmBone = this.modelProvider.getBone(this.leftArmBone);
				TyrannomationUtils.copyRotations(this.leftArm, leftArmBone);
				leftArmBone.setPositionX(this.leftArm.x - 5);
				leftArmBone.setPositionY(2 - this.leftArm.y);
				leftArmBone.setPositionZ(this.leftArm.z);
			}

			if(this.rightLegBone != null) 
			{
				IBone rightLegBone = this.modelProvider.getBone(this.rightLegBone);
				TyrannomationUtils.copyRotations(this.rightLeg, rightLegBone);
				rightLegBone.setPositionX(this.rightLeg.x + 2);
				rightLegBone.setPositionY(12 - this.rightLeg.y);
				rightLegBone.setPositionZ(this.rightLeg.z);
				if(this.rightBootBone != null) 
				{
					IBone rightBootBone = this.modelProvider.getBone(this.rightBootBone);
					TyrannomationUtils.copyRotations(this.rightLeg, rightBootBone);
					rightBootBone.setPositionX(this.rightLeg.x + 2);
					rightBootBone.setPositionY(12 - this.rightLeg.y);
					rightBootBone.setPositionZ(this.rightLeg.z);
				}
			}

			if(this.leftLegBone != null) 
			{
				IBone leftLegBone = this.modelProvider.getBone(this.leftLegBone);
				TyrannomationUtils.copyRotations(this.leftLeg, leftLegBone);
				leftLegBone.setPositionX(this.leftLeg.x - 2);
				leftLegBone.setPositionY(12 - this.leftLeg.y);
				leftLegBone.setPositionZ(this.leftLeg.z);
				if(this.leftBootBone != null) 
				{
					IBone leftBootBone = this.modelProvider.getBone(this.leftBootBone);
					TyrannomationUtils.copyRotations(this.leftLeg, leftBootBone);
					leftBootBone.setPositionX(this.leftLeg.x - 2);
					leftBootBone.setPositionY(12 - this.leftLeg.y);
					leftBootBone.setPositionZ(this.leftLeg.z);
				}
			}
		}
	}

	@Override
	public TyrannomatedTyrannomationModel<T> getGeoModelProvider() 
	{
		return this.modelProvider;
	}

	@Override
	public ResourceLocation getTextureLocation(T instance) 
	{
		return this.modelProvider.getTextureLocation(instance);
	}

	public TyrannomationArmorRenderer setCurrentItem(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot) 
	{
		this.entityLiving = entityLiving;
		this.itemStack = itemStack;
		this.armorSlot = armorSlot;
		this.currentArmorItem = (T) itemStack.getItem();
		return this;
	}

	public final TyrannomationArmorRenderer applyEntityStats(HumanoidModel defaultArmor) 
	{
		this.young = defaultArmor.young;
		this.crouching = defaultArmor.crouching;
		this.riding = defaultArmor.riding;
		this.rightArmPose = defaultArmor.rightArmPose;
		this.leftArmPose = defaultArmor.leftArmPose;
		return this;
	}

	public TyrannomationArmorRenderer applySlot(EquipmentSlot slot) 
	{
		modelProvider.getModel(modelProvider.getModelLocation(currentArmorItem));

		IBone headBone = this.getAndHideBone(this.headBone);
		IBone bodyBone = this.getAndHideBone(this.bodyBone);
		IBone rightArmBone = this.getAndHideBone(this.rightArmBone);
		IBone leftArmBone = this.getAndHideBone(this.leftArmBone);
		IBone rightLegBone = this.getAndHideBone(this.rightLegBone);
		IBone leftLegBone = this.getAndHideBone(this.leftLegBone);
		IBone rightBootBone = this.getAndHideBone(this.rightBootBone);
		IBone leftBootBone = this.getAndHideBone(this.leftBootBone);

		switch(slot) 
		{
		case HEAD:
			if(headBone != null)
				headBone.setHidden(false);
			break;
		case CHEST:
			if(bodyBone != null)
				bodyBone.setHidden(false);
			if(rightArmBone != null)
				rightArmBone.setHidden(false);
			if(leftArmBone != null)
				leftArmBone.setHidden(false);
			break;
		case LEGS:
			if(rightLegBone != null)
				rightLegBone.setHidden(false);
			if(leftLegBone != null)
				leftLegBone.setHidden(false);
			break;
		case FEET:
			if(rightBootBone != null)
				rightBootBone.setHidden(false);
			if(leftBootBone != null)
				leftBootBone.setHidden(false);
			break;
		}
		return this;
	}

	protected IBone getAndHideBone(String boneName) 
	{
		if(boneName != null) 
		{
			final IBone bone = this.modelProvider.getBone(boneName);
			bone.setHidden(true);
			return bone;
		}
		return null;
	}

	@Override
	public Integer getUniqueID(T animatable) 
	{
		return Objects.hash(this.armorSlot, itemStack.getItem(), itemStack.getCount(), itemStack.hasTag() ? itemStack.getTag().toString() : 1, this.entityLiving.getUUID().toString());
	}
}
