package tyrannotitanlib.content.client;

import java.util.ArrayList;
import java.util.List;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class Capes 
{
	private final Minecraft minecraft = Minecraft.getInstance();
	private List<String> players = new ArrayList<>();

	@SubscribeEvent
	public final void renderPlayer(final RenderPlayerEvent.Pre event) 
	{
		PlayerEntity player = event.getPlayer();
		AbstractClientPlayerEntity acp = (AbstractClientPlayerEntity) player;
		String username = player.getName().getString();

		if(TyrannoUtils.TYRANNOTITANS.contains(username)) 
		{
			if(acp.isCapeLoaded() && acp.getCloakTextureLocation() == null && !players.contains(username)) 
			{
				this.players.add(username);

				NetworkPlayerInfo playerInfo = acp.getPlayerInfo();

				Util.backgroundExecutor().execute(() -> 
				{
					ResourceLocation resourceLocation = TyrannoUtils.rL("textures/entities/capes/tyrannotitan_cape.png");

					playerInfo.textureLocations.put(Type.CAPE, resourceLocation);
					playerInfo.textureLocations.put(Type.ELYTRA, resourceLocation);
				});
			}
		}
	}

	@SubscribeEvent
	public final void clientTick(final ClientTickEvent event) 
	{
		if(minecraft.player == null && !this.players.isEmpty()) 
		{
			this.players.clear();
		}
	}
}
