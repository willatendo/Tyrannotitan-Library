package tyrannimation.model;

import java.util.Collections;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import tyrannimation.core.IAnimated;
import tyrannimation.core.ITickableAnimated;
import tyrannimation.core.event.predicate.TyrannomationEvent;
import tyrannimation.core.manager.AnimatedData;
import tyrannimation.resource.TyrannomationCache;

public abstract class TickingAnimatedModel<T extends IAnimated & ITickableAnimated> extends AnimatedModel<T> {
	public boolean isInitialized() {
		return !this.getAnimationProcessor().getModelRendererList().isEmpty();
	}

	@Override
	public void setLivingAnimations(T entity, Integer uniqueID, @Nullable TyrannomationEvent customPredicate) {
		AnimatedData manager = entity.getAnimationFactory().getOrCreateAnimationData(uniqueID);
		if (manager.startTick == null) {
			manager.startTick = (double) (entity.tickTimer() + Minecraft.getInstance().getFrameTime());
		}

		if (!Minecraft.getInstance().isPaused() || manager.shouldPlayWhilePaused) {
			manager.tick = (entity.tickTimer() + Minecraft.getInstance().getFrameTime());
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

		predicate.animatedTick = seekTime;
		getAnimationProcessor().preAnimationSetup(predicate.getAnimated(), seekTime);
		if (!this.getAnimationProcessor().getModelRendererList().isEmpty()) {
			getAnimationProcessor().tickAnimation(entity, uniqueID, seekTime, predicate, TyrannomationCache.getInstance().parser, shouldCrashOnMissing);
		}

		if (!Minecraft.getInstance().isPaused() || manager.shouldPlayWhilePaused) {
			this.codeAnimations(entity, uniqueID, customPredicate);
		}
	}

	public void codeAnimations(T entity, Integer uniqueID, TyrannomationEvent<?> customPredicate) {
	}
}
