package tyrannimation.model;

import java.util.Collections;

import javax.annotation.Nullable;

import com.eliotlash.molang.MolangParser;
import com.mojang.blaze3d.Blaze3D;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import tyrannimation.animation.exception.TyrannotitanLibException;
import tyrannimation.animation.render.built.TyrannomationBone;
import tyrannimation.animation.render.built.TyrannomationModel;
import tyrannimation.core.IAnimated;
import tyrannimation.core.IAnimatedModel;
import tyrannimation.core.builder.Tyrannomation;
import tyrannimation.core.event.predicate.TyrannomationEvent;
import tyrannimation.core.manager.AnimatedData;
import tyrannimation.core.processor.AnimatedProcessor;
import tyrannimation.core.processor.IAnimatedBone;
import tyrannimation.file.AnimatedFile;
import tyrannimation.model.provider.AnimatedModelProvider;
import tyrannimation.model.provider.IAnimatedModelProvider;
import tyrannimation.resource.TyrannomationCache;
import tyrannimation.util.MolangUtils;

public abstract class AnimatedModel<T extends IAnimated> extends AnimatedModelProvider<T> implements IAnimatedModel<T>, IAnimatedModelProvider<T> {
	private final AnimatedProcessor animationProcessor;
	private TyrannomationModel currentModel;

	protected AnimatedModel() {
		this.animationProcessor = new AnimatedProcessor(this);
	}

	public void registerBone(TyrannomationBone bone) {
		this.registerModelRenderer(bone);

		for (TyrannomationBone childBone : bone.childBones) {
			this.registerBone(childBone);
		}
	}

	@Override
	public void setLivingAnimations(T entity, Integer uniqueID, @Nullable TyrannomationEvent customPredicate) {
		AnimatedData manager = entity.getAnimationFactory().getOrCreateAnimationData(uniqueID);
		if (manager.startTick == null) {
			manager.startTick = getCurrentTick();
		}

		if (!Minecraft.getInstance().isPaused() || manager.shouldPlayWhilePaused) {
			manager.tick = (getCurrentTick() - manager.startTick);
			double gameTick = manager.tick;
			double deltaTicks = gameTick - this.lastGameTickTime;
			this.seekTime += deltaTicks;
			this.lastGameTickTime = gameTick;
		}

		TyrannomationEvent<T> predicate;
		if (customPredicate == null) {
			predicate = new TyrannomationEvent<T>(entity, 0, 0, 0, false, Collections.emptyList());
		} else {
			predicate = customPredicate;
		}

		predicate.animatedTick = this.seekTime;
		this.animationProcessor.preAnimationSetup(predicate.getAnimated(), this.seekTime);
		if (!this.animationProcessor.getModelRendererList().isEmpty()) {
			this.animationProcessor.tickAnimation(entity, uniqueID, this.seekTime, predicate, TyrannomationCache.getInstance().parser, this.shouldCrashOnMissing);
		}
	}

	@Override
	public AnimatedProcessor getAnimationProcessor() {
		return this.animationProcessor;
	}

	public void registerModelRenderer(IAnimatedBone modelRenderer) {
		this.animationProcessor.registerModelRenderer(modelRenderer);
	}

	@Override
	public Tyrannomation getAnimation(String name, IAnimated animatable) {
		AnimatedFile animation = TyrannomationCache.getInstance().getAnimations().get(this.getAnimationFileLocation((T) animatable));
		if (animation == null) {
			throw new TyrannotitanLibException(this.getAnimationFileLocation((T) animatable), "Could not find animation file. Please double check name.");
		}
		return animation.getAnimation(name);
	}

	@Override
	public TyrannomationModel getModel(ResourceLocation location) {
		TyrannomationModel model = super.getModel(location);
		if (model == null) {
			throw new TyrannotitanLibException(location, "Could not find model. If you are getting this with a built mod, please just restart your game.");
		}
		if (model != currentModel) {
			this.animationProcessor.clearModelRendererList();
			for (TyrannomationBone bone : model.topLevelBones) {
				registerBone(bone);
			}
			this.currentModel = model;
		}
		return model;
	}

	@Override
	public void setMolangQueries(IAnimated animatable, double currentTick) {
		MolangParser parser = TyrannomationCache.getInstance().parser;
		Minecraft minecraftInstance = Minecraft.getInstance();

		parser.setValue("query.actor_count", minecraftInstance.level.getEntityCount());
		parser.setValue("query.time_of_day", MolangUtils.normalizeTime(minecraftInstance.level.getDayTime()));
		parser.setValue("query.moon_phase", minecraftInstance.level.getMoonPhase());

		if (animatable instanceof Entity) {
			parser.setValue("query.distance_from_camera", minecraftInstance.gameRenderer.getMainCamera().getPosition().distanceTo(((Entity) animatable).position()));
			parser.setValue("query.is_on_ground", MolangUtils.booleanToFloat(((Entity) animatable).isOnGround()));
			parser.setValue("query.is_in_water", MolangUtils.booleanToFloat(((Entity) animatable).isInWater()));
			parser.setValue("query.is_in_water_or_rain", MolangUtils.booleanToFloat(((Entity) animatable).isInWaterRainOrBubble()));

			if (animatable instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) animatable;
				parser.setValue("query.health", livingEntity.getHealth());
				parser.setValue("query.max_health", livingEntity.getMaxHealth());
				parser.setValue("query.is_on_fire", MolangUtils.booleanToFloat(livingEntity.isOnFire()));
				parser.setValue("query.on_fire_time", livingEntity.getRemainingFireTicks());

				Vec3 velocity = livingEntity.getDeltaMovement();
				float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
				parser.setValue("query.ground_speed", groundSpeed);

				float yawSpeed = livingEntity.getViewYRot((float) currentTick) - livingEntity.getViewYRot((float) (currentTick - 0.1));
				parser.setValue("query.yaw_speed", yawSpeed);
			}
		}
	}

	@Override
	public double getCurrentTick() {
		return Blaze3D.getTime() * 20;
	}
}
