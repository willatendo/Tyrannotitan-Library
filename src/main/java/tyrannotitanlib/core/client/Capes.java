package tyrannotitanlib.core.client;

import static tyrannotitanlib.TyrannotitanLibrary.TYRANNOTITANS;
import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_UTILS;

import java.util.ArrayList;
import java.util.List;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class Capes {
	private final Minecraft minecraft = Minecraft.getInstance();
	private List<String> players = new ArrayList<>();

	@SubscribeEvent
	public final void renderPlayer(final RenderPlayerEvent.Pre event) {
		Player player = event.getPlayer();
		AbstractClientPlayer acp = (AbstractClientPlayer) player;
		String username = player.getName().getString();

		if (TYRANNOTITANS.contains(username)) {
			if (acp.isCapeLoaded() && acp.getCloakTextureLocation() == null && !players.contains(username)) {
				this.players.add(username);

				PlayerInfo playerInfo = acp.getPlayerInfo();

				Util.backgroundExecutor().execute(() -> {
					ResourceLocation resourceLocation = TYRANNO_UTILS.mod("textures/entities/capes/tyrannotitan_cape.png");

					playerInfo.textureLocations.put(Type.CAPE, resourceLocation);
					playerInfo.textureLocations.put(Type.ELYTRA, resourceLocation);
				});
			}
		}
	}

	@SubscribeEvent
	public final void clientTick(final ClientTickEvent event) {
		if (minecraft.player == null && !this.players.isEmpty()) {
			this.players.clear();
		}
	}
}
