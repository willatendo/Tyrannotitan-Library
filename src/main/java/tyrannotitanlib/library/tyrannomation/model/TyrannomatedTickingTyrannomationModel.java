package tyrannotitanlib.library.tyrannomation.model;

import java.util.Collections;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomationTickable;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomation.core.manager.TyrannomationData;
import tyrannotitanlib.library.tyrannomation.resource.TyrannomationCache;

public abstract class TyrannomatedTickingTyrannomationModel<T extends ITyrannomatable & ITyrannomationTickable> extends TyrannomatedTyrannomationModel<T> {
	public TyrannomatedTickingTyrannomationModel() {
	}

	public boolean isInitialized() {
		return !this.getAnimationProcessor().getModelRendererList().isEmpty();
	}

	@Override
	public void setLivingAnimations(T entity, Integer uniqueID, @Nullable TyrannomationEvent customPredicate) {
		TyrannomationData manager = entity.getFactory().getOrCreateAnimationData(uniqueID);
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

		predicate.animationTick = seekTime;
		getAnimationProcessor().preAnimationSetup(predicate.getAnimatable(), seekTime);
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
