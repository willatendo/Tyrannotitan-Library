package tyrannotitanlib.library.tyrannomation.model;

import java.util.Collections;

import javax.annotation.Nullable;

import com.eliotlash.molang.MolangParser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.NativeUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import tyrannotitanlib.library.tyrannomation.core.IAnimatable;
import tyrannotitanlib.library.tyrannomation.core.IAnimatableModel;
import tyrannotitanlib.library.tyrannomation.core.builder.Animation;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.AnimationEvent;
import tyrannotitanlib.library.tyrannomation.core.manager.AnimationData;
import tyrannotitanlib.library.tyrannomation.core.processor.AnimationProcessor;
import tyrannotitanlib.library.tyrannomation.core.processor.IBone;
import tyrannotitanlib.library.tyrannomation.file.AnimationFile;
import tyrannotitanlib.library.tyrannomation.model.provider.IAnimatableModelProvider;
import tyrannotitanlib.library.tyrannomation.model.provider.TyrannomationModelProvider;
import tyrannotitanlib.library.tyrannomation.resource.TyrannomationCache;
import tyrannotitanlib.library.tyrannomation.tyranno.exception.TyrannotitanLibException;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationBone;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;
import tyrannotitanlib.library.tyrannomation.util.MolangUtils;

public abstract class AnimatedTyrannomationModel<T extends IAnimatable> extends TyrannomationModelProvider<T> implements IAnimatableModel<T>, IAnimatableModelProvider<T> 
{
	private final AnimationProcessor animationProcessor;
	private TyrannomationModel currentModel;

	protected AnimatedTyrannomationModel() 
	{
		this.animationProcessor = new AnimationProcessor(this);
	}

	public void registerBone(TyrannomationBone bone) 
	{
		registerModelRenderer(bone);

		for(TyrannomationBone childBone : bone.childBones) 
		{
			registerBone(childBone);
		}
	}

	@Override
	public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) 
	{
		AnimationData manager = entity.getFactory().getOrCreateAnimationData(uniqueID);
		if(manager.startTick == null) 
		{
			manager.startTick = getCurrentTick();
		}

		if(!Minecraft.getInstance().isPaused() || manager.shouldPlayWhilePaused) 
		{
			manager.tick = (getCurrentTick() - manager.startTick);
			double gameTick = manager.tick;
			double deltaTicks = gameTick - lastGameTickTime;
			seekTime += deltaTicks;
			lastGameTickTime = gameTick;
		}

		AnimationEvent<T> predicate;
		if(customPredicate == null) 
		{
			predicate = new AnimationEvent<T>(entity, 0, 0, 0, false, Collections.emptyList());
		} 
		else 
		{
			predicate = customPredicate;
		}

		predicate.animationTick = seekTime;
		animationProcessor.preAnimationSetup(predicate.getAnimatable(), seekTime);
		if(!this.animationProcessor.getModelRendererList().isEmpty()) 
		{
			animationProcessor.tickAnimation(entity, uniqueID, seekTime, predicate, TyrannomationCache.getInstance().parser, shouldCrashOnMissing);
		}
	}

	@Override
	public AnimationProcessor getAnimationProcessor() 
	{
		return this.animationProcessor;
	}

	public void registerModelRenderer(IBone modelRenderer) 
	{
		animationProcessor.registerModelRenderer(modelRenderer);
	}

	@Override
	public Animation getAnimation(String name, IAnimatable animatable) 
	{
		AnimationFile animation = TyrannomationCache.getInstance().getAnimations().get(this.getAnimationFileLocation((T) animatable));
		if(animation == null) 
		{
			throw new TyrannotitanLibException(this.getAnimationFileLocation((T) animatable), "Could not find animation file. Please double check name.");
		}
		return animation.getAnimation(name);
	}

	@Override
	public TyrannomationModel getModel(ResourceLocation location) 
	{
		TyrannomationModel model = super.getModel(location);
		if(model == null) 
		{
			throw new TyrannotitanLibException(location, "Could not find model. If you are getting this with a built mod, please just restart your game.");
		}
		if(model != currentModel) 
		{
			this.animationProcessor.clearModelRendererList();
			for(TyrannomationBone bone : model.topLevelBones) 
			{
				registerBone(bone);
			}
			this.currentModel = model;
		}
		return model;
	}

	@Override
	public void setMolangQueries(IAnimatable animatable, double currentTick) 
	{
		MolangParser parser = TyrannomationCache.getInstance().parser;
		Minecraft minecraftInstance = Minecraft.getInstance();

		parser.setValue("query.actor_count", minecraftInstance.level.getEntityCount());
		parser.setValue("query.time_of_day", MolangUtils.normalizeTime(minecraftInstance.level.getDayTime()));
		parser.setValue("query.moon_phase", minecraftInstance.level.getMoonPhase());

		if (animatable instanceof Entity) 
		{
			parser.setValue("query.distance_from_camera", minecraftInstance.gameRenderer.getMainCamera().getPosition().distanceTo(((Entity) animatable).position()));
			parser.setValue("query.is_on_ground", MolangUtils.booleanToFloat(((Entity) animatable).isOnGround()));
			parser.setValue("query.is_in_water", MolangUtils.booleanToFloat(((Entity) animatable).isInWater()));
			parser.setValue("query.is_in_water_or_rain", MolangUtils.booleanToFloat(((Entity) animatable).isInWaterRainOrBubble()));

			if(animatable instanceof LivingEntity) 
			{
				LivingEntity livingEntity = (LivingEntity) animatable;
				parser.setValue("query.health", livingEntity.getHealth());
				parser.setValue("query.max_health", livingEntity.getMaxHealth());
				parser.setValue("query.is_on_fire", MolangUtils.booleanToFloat(livingEntity.isOnFire()));
				parser.setValue("query.on_fire_time", livingEntity.getRemainingFireTicks());

				Vector3d velocity = livingEntity.getDeltaMovement();
				float groundSpeed = MathHelper.sqrt((velocity.x * velocity.x) + (velocity.z * velocity.z));
				parser.setValue("query.ground_speed", groundSpeed);

				float yawSpeed = livingEntity.getViewYRot((float) currentTick) - livingEntity.getViewYRot((float) (currentTick - 0.1));
				parser.setValue("query.yaw_speed", yawSpeed);
			}
		}
	}

	@Override
	public double getCurrentTick() 
	{
		return NativeUtil.getTime() * 20;
	}
}
