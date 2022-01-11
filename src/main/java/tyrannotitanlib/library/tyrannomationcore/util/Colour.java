package tyrannotitanlib.library.tyrannomationcore.util;

public final class Colour {
	private final int color;

	private Colour(int color) {
        this.color = color;
    }

	public static Colour ofTransparent(int color) {
		return new Colour(color);
	}

	public static Colour ofOpaque(int color) {
		return new Colour(0xFF000000 | color);
	}

	public static Colour ofRGB(float r, float g, float b) {
		return ofRGBA(r, g, b, 1f);
	}

	public static Colour ofRGB(int r, int g, int b) {
		return ofRGBA(r, g, b, 255);
	}

	public static Colour ofRGBA(float r, float g, float b, float a) {
		return ofRGBA((int) (r * 255 + 0.5), (int) (g * 255 + 0.5), (int) (b * 255 + 0.5), (int) (a * 255 + 0.5));
	}

	public static Colour ofRGBA(int r, int g, int b, int a) {
		return new Colour(((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF));
	}

	public static Colour ofHSB(float hue, float saturation, float brightness) {
		return ofOpaque(HSBtoRGB(hue, saturation, brightness));
	}

	public static int HSBtoRGB(float hue, float saturation, float brightness) {
		int r = 0, g = 0, b = 0;
		if (saturation == 0) {
			r = g = b = (int) (brightness * 255.0f + 0.5f);
		} else {
			float h = (hue - (float) Math.floor(hue)) * 6.0f;
			float f = h - (float) Math.floor(h);
			float p = brightness * (1.0f - saturation);
			float q = brightness * (1.0f - saturation * f);
			float t = brightness * (1.0f - (saturation * (1.0f - f)));
			switch ((int) h) {
			case 0:
				r = (int) (brightness * 255.0f + 0.5f);
				g = (int) (t * 255.0f + 0.5f);
				b = (int) (p * 255.0f + 0.5f);
				break;
			case 1:
				r = (int) (q * 255.0f + 0.5f);
				g = (int) (brightness * 255.0f + 0.5f);
				b = (int) (p * 255.0f + 0.5f);
				break;
			case 2:
				r = (int) (p * 255.0f + 0.5f);
				g = (int) (brightness * 255.0f + 0.5f);
				b = (int) (t * 255.0f + 0.5f);
				break;
			case 3:
				r = (int) (p * 255.0f + 0.5f);
				g = (int) (q * 255.0f + 0.5f);
				b = (int) (brightness * 255.0f + 0.5f);
				break;
			case 4:
				r = (int) (t * 255.0f + 0.5f);
				g = (int) (p * 255.0f + 0.5f);
				b = (int) (brightness * 255.0f + 0.5f);
				break;
			case 5:
				r = (int) (brightness * 255.0f + 0.5f);
				g = (int) (p * 255.0f + 0.5f);
				b = (int) (q * 255.0f + 0.5f);
				break;
			}
		}
		return 0xff000000 | (r << 16) | (g << 8) | b;
	}

	public int getColour() {
		return this.color;
	}

	public int getAlpha() {
		return this.color >> 24 & 0xFF;
	}

	public int getRed() {
		return this.color >> 16 & 0xFF;
	}

	public int getGreen() {
		return this.color >> 8 & 0xFF;
	}

	public int getBlue() {
		return this.color & 0xFF;
	}

	public Colour brighter(double factor) {
		int r = getRed(), g = getGreen(), b = getBlue();
		int i = (int) (1.0 / (1.0 - (1 / factor)));
		if (r == 0 && g == 0 && b == 0) {
			return ofRGBA(i, i, i, getAlpha());
		}
		if (r > 0 && r < i)
			r = i;
		if (g > 0 && g < i)
			g = i;
		if (b > 0 && b < i)
			b = i;
		return ofRGBA(Math.min((int) (r / (1 / factor)), 255), Math.min((int) (g / (1 / factor)), 255), Math.min((int) (b / (1 / factor)), 255), getAlpha());
	}
	
	public Colour darker(double factor) {
		return ofRGBA(Math.max((int) (getRed() * (1 / factor)), 0), Math.max((int) (getGreen() * (1 / factor)), 0), Math.max((int) (getBlue() * (1 / factor)), 0), getAlpha());
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || getClass() != other.getClass())
			return false;
		return this.color == ((Colour) other).color;
	}

	@Override
	public int hashCode() {
		return this.color;
	}

	@Override
	public String toString() {
		return String.valueOf(this.color);
	}
}
