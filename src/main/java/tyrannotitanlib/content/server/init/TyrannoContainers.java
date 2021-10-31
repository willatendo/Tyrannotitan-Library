package tyrannotitanlib.content.server.init;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import tyrannotitanlib.library.compatibility.charm.CharmCrateContainer;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TyrannoContainers 
{
	public static final ContainerType<CharmCrateContainer> CRATE_CONTAINER = TyrannoRegistries.create("crate_contaier", IForgeContainerType.create(CharmCrateContainer::new));
	
	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Containers"); }
}
