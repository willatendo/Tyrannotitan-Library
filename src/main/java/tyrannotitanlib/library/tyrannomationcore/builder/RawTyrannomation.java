package tyrannotitanlib.library.tyrannomationcore.builder;

import java.util.Objects;

public class RawTyrannomation {
	public String animationName;

	public Boolean loop;

	public RawTyrannomation(String animationName, Boolean loop) {
		this.animationName = animationName;
		this.loop = loop;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof RawTyrannomation)) {
			return false;
		}

		RawTyrannomation animation = (RawTyrannomation) obj;

		if (animation.loop == this.loop && animation.animationName.equals(this.animationName)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(animationName, loop);
	}
}
