package tyrannotitanlib.library.tyrannomation.core.event;

import java.util.List;

import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;

public class CustomInstructionKeyframeEvent<T> extends KeyframeEvent<T> {
	public final List<String> instructions;

	public CustomInstructionKeyframeEvent(T entity, double animationTick, List<String> instructions, TyrannomationController controller) {
		super(entity, animationTick, controller);
		this.instructions = instructions;
	}
}
