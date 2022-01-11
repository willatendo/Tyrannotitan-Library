package tyrannotitanlib.library.tyrannomationcore.builder;

import java.util.ArrayList;
import java.util.List;

public class TyrannomationBuilder {
	private List<RawTyrannomation> animationList = new ArrayList<>();

	public TyrannomationBuilder addAnimation(String animationName, Boolean shouldLoop) {
		animationList.add(new RawTyrannomation(animationName, shouldLoop));
		return this;
	}

	public TyrannomationBuilder addAnimation(String animationName) {
		animationList.add(new RawTyrannomation(animationName, null));
		return this;
	}

	public TyrannomationBuilder addRepeatingAnimation(String animationName, int timesToRepeat) {
		assert timesToRepeat > 0;
		for (int i = 0; i < timesToRepeat; i++) {
			addAnimation(animationName, false);
		}
		return this;
	}

	public TyrannomationBuilder clearAnimations() {
		animationList.clear();
		return this;
	}

	public List<RawTyrannomation> getRawAnimationList() {
		return animationList;
	}
}
