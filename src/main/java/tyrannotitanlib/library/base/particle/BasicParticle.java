package tyrannotitanlib.library.base.particle;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public class BasicParticle extends TextureSheetParticle {
	public BasicParticle(ClientLevel world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed) {
		super(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);

		float f = this.random.nextFloat() * 1.0f;
		this.rCol = f;
		this.gCol = f;
		this.bCol = f;

		this.setSize(0.02f, 0.02f);
		this.quadSize *= this.random.nextFloat() * 1.1F;
		this.xd *= (double) 0.02f;
		this.yd *= (double) 0.02f;
		this.zd *= (double) 0.02f;
		this.lifetime = (int) (20.0D / (Math.random() * 1.0D));
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;

		if (this.lifetime-- <= 0) {
			this.remove();
		} else {
			this.move(this.xd, this.yd, this.zd);
			this.xd *= 1.0D;
			this.yd *= 1.0D;
			this.zd *= 1.0D;
		}
	}

	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Factory(SpriteSet sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			BasicParticle particle = new BasicParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.setColor(1.0f, 1.0f, 1.0f);
			particle.pickSprite(this.spriteSet);
			return particle;
		}
	}
}
