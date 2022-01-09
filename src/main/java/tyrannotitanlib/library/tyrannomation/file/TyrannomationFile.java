package tyrannotitanlib.library.tyrannomation.file;

import java.util.Collection;
import java.util.HashMap;

import tyrannotitanlib.library.tyrannomation.core.builder.Tyrannomation;

public class TyrannomationFile {
	private HashMap<String, Tyrannomation> animations = new HashMap<>();

	public Tyrannomation getAnimation(String name) {
		return animations.get(name);
	}

	public void putAnimation(String name, Tyrannomation animation) {
		this.animations.put(name, animation);
	}

	public Collection<Tyrannomation> getAllAnimations() {
		return this.animations.values();
	}
}
