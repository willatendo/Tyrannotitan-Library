package tyrannotitanlib.tyrannimation.model;

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
import tyrannotitanlib.tyrannimation.animation.exception.AnimationException;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationBone;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationModel;
import tyrannotitanlib.tyrannimation.core.IAnimated;
import tyrannotitanlib.tyrannimation.core.IAnimatedModel;
import tyrannotitanlib.tyrannimation.core.builder.Animation;
import tyrannotitanlib.tyrannimation.core.event.predicate.AnimationEvent;
import tyrannotitanlib.tyrannimation.core.manager.AnimatedData;
import tyrannotitanlib.tyrannimation.core.processor.AnimatedProcessor;
import tyrannotitanlib.tyrannimation.core.processor.IAnimatedBone;
import tyrannotitanlib.tyrannimation.file.AnimatedFile;
import tyrannotitanlib.tyrannimation.model.provider.AnimatedModelProvider;
import tyrannotitanlib.tyrannimation.model.provider.IAnimatedModelProvider;
import tyrannotitanlib.tyrannimation.resource.AnimatedCashe;
import tyrannotitanlib.tyrannimation.util.MolangUtils;

public abstract class AnimatedModel<T extends IAnimated> extends AnimatedModelProvider<T> implements IAnimatedModel<T>, IAnimatedModelProvider<T> {
	private final AnimatedProcessor animationProcessor;
	private AnimationModel currentModel;

	protected AnimatedModel() {
		this.animationProcessor = new AnimatedProcessor(this);
	}

	public void registerBone(AnimationBone bone) {
		this.registerModelRenderer(bone);

		for (AnimationBone childBone : bone.childBones) {
			this.registerBone(childBone);
		}
	}

	@Override
	public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
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

		AnimationEvent<T> predicate;
		if (customPredicate == null) {
			predicate = new AnimationEvent<T>(entity, 0, 0, 0, false, Collections.emptyList());
		} else {
			predicate = customPredicate;
		}

		predicate.animatedTick = this.seekTime;
		this.animationProcessor.preAnimationSetup(predicate.getAnimated(), this.seekTime);
		if (!this.animationProcessor.getModelRendererList().isEmpty()) {
			this.animationProcessor.tickAnimation(entity, uniqueID, this.seekTime, predicate, AnimatedCashe.getInstance().parser, this.shouldCrashOnMissing);
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
	public Animation getAnimation(String name, IAnimated animatable) {
		AnimatedFile animation = AnimatedCashe.getInstance().getAnimations().get(this.getAnimationFileLocation((T) animatable));
		if (animation == null) {
			throw new AnimationException(this.getAnimationFileLocation((T) animatable), "Could not find animation file. Please double check name.");
		}
		return animation.getAnimation(name);
	}

	@Override
	public AnimationModel getModel(ResourceLocation location) {
		AnimationModel model = super.getModel(location);
		if (model == null) {
			throw new AnimationException(location, "Could not find model. If you are getting this with a built mod, please just restart your game.");
		}
		if (model != currentModel) {
			this.animationProcessor.clearModelRendererList();
			for (AnimationBone bone : model.topLevelBones) {
				registerBone(bone);
			}
			this.currentModel = model;
		}
		return model;
	}

	@Override
	public void setMolangQueries(IAnimated animatable, double currentTick) {
		MolangParser parser = AnimatedCashe.getInstance().parser;
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
