package tyrannotitanlib.library.base.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import tyrannotitanlib.library.base.util.TyrannoTeleporter;
import tyrannotitanlib.library.base.util.TyrannoUtils;

/*
 * This is a Item class that makes a Item and gives it a ItemGroup, and name without too much trouble as well as makes it so, upon a left click, it telports to a World.
 * To change the name in another language, use the name that you set.
 * 
 * In your item class, make a register that fills in the ItemGroup.
 * 
 * ---
 * 
 * Author: Willatendo
 * 
 * Creation Date: July 24, 2021
 * Final Edit Date: July 24, 2021
 */

public class TyrannoTeleportItem extends Item
{
	public RegistryKey<World> teleportToWorld;
	
	public TyrannoTeleportItem(final Properties properties, final RegistryKey<World> teleportToWorld) 
	{
		super(properties);
		this.teleportToWorld = teleportToWorld;
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) 
	{
		if(!player.isPassenger() && !player.isVehicle() && player.canChangeDimensions()) 
		{
			if(player.level instanceof ServerWorld) 
			{
				ServerWorld serverworld = (ServerWorld)player.level;
				MinecraftServer minecraftserver = serverworld.getServer();
				RegistryKey<World> registrykey = player.level.dimension() == this.teleportToWorld ? World.OVERWORLD : this.teleportToWorld;
				ServerWorld serverworld1 = minecraftserver.getLevel(registrykey);
				if(serverworld1 != null && !player.isPassenger()) 
				{
					player.changeDimension(serverworld1, new TyrannoTeleporter());
					if(registrykey.equals(this.teleportToWorld))
					{
						player.sendMessage(TyrannoUtils.tTC("message", "transport_to_" + this.teleportToWorld.toString().toLowerCase()), player.getUUID());
					}
					else
					{
						player.sendMessage(TyrannoUtils.tTC("message", "transport_to_overworld"), player.getUUID());
					}
				}
			}
		}
		return super.use(world, player, hand);
	}
}
