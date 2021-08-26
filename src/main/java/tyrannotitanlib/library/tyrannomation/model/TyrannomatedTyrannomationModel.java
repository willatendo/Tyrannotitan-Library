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
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatableModel;
import tyrannotitanlib.library.tyrannomation.core.builder.Tyrannomation;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomation.core.manager.TyrannomationData;
import tyrannotitanlib.library.tyrannomation.core.processor.TyrannomationProcessor;
import tyrannotitanlib.library.tyrannomation.core.processor.IBone;
import tyrannotitanlib.library.tyrannomation.file.TyrannomationFile;
import tyrannotitanlib.library.tyrannomation.model.provider.ITyrannomatableModelProvider;
import tyrannotitanlib.library.tyrannomation.model.provider.TyrannomationModelProvider;
import tyrannotitanlib.library.tyrannomation.resource.TyrannomationCache;
import tyrannotitanlib.library.tyrannomation.tyranno.exception.TyrannotitanLibException;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationBone;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;
import tyrannotitanlib.library.tyrannomation.util.MolangUtils;

public abstract class TyrannomatedTyrannomationModel<T extends ITyrannomatable> extends TyrannomationModelProvider<T> implements ITyrannomatableModel<T>, ITyrannomatableModelProvider<T> 
{
	private final TyrannomationProcessor animationProcessor;
	private TyrannomationModel currentModel;

	protected TyrannomatedTyrannomationModel() 
	{
		this.animationProcessor = new TyrannomationProcessor(this);
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
	public void setLivingAnimations(T entity, Integer uniqueID, @Nullable TyrannomationEvent customPredicate) 
	{
		TyrannomationData manager = entity.getFactory().getOrCreateAnimationData(uniqueID);
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

		TyrannomationEvent<T> predicate;
		if(customPredicate == null) 
		{
			predicate = new TyrannomationEvent<T>(entity, 0, 0, 0, false, Collections.emptyList());
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
	public TyrannomationProcessor getAnimationProcessor() 
	{
		return this.animationProcessor;
	}

	public void registerModelRenderer(IBone modelRenderer) 
	{
		animationProcessor.registerModelRenderer(modelRenderer);
	}

	@Override
	public Tyrannomation getAnimation(String name, ITyrannomatable animatable) 
	{
		TyrannomationFile animation = TyrannomationCache.getInstance().getAnimations().get(this.getAnimationFileLocation((T) animatable));
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
	public void setMolangQueries(ITyrannomatable animatable, double currentTick) 
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
