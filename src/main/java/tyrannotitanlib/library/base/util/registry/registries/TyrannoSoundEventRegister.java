package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoSoundEventRegister extends AbstractTyrannoRegistry<SoundEvent>
{
	public TyrannoSoundEventRegister(TyrannoRegistry registry, DeferredRegister<SoundEvent> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoSoundEventRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, registry.getModId()));
	}
	
	public SoundEvent build(String id, SoundEvent soundEvent)
	{
		this.deferredRegister.register(id, () -> soundEvent);
		return soundEvent;
	}
}
