package tyrannotitanlib.content.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
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
        final PlayerEntity player = event.getPlayer();
        final AbstractClientPlayerEntity acp = (AbstractClientPlayerEntity) player;
        final String username = player.getName().getString();

        if(TyrannoUtils.TYRANNOTITANS.contains(username))
        {
            if(acp.isCapeLoaded() && acp.getCloakTextureLocation() == null && !players.contains(username))
            {
                this.players.add(username);

                final NetworkPlayerInfo playerInfo = acp.getPlayerInfo();

                Util.backgroundExecutor().execute(() -> 
                {
                    try
                    {
                        final URL url = new URL(String.format("http://s.optifine.net/capes/%s.png", username));
                        final NativeImage nativeImage = NativeImage.read(url.openStream());
                        final DynamicTexture dynamicTexture = new DynamicTexture(this.parseCape(nativeImage));
                        final ResourceLocation resourceLocation = minecraft.getTextureManager().register("optifinecapes/", dynamicTexture);

                        playerInfo.textureLocations.put(Type.CAPE, resourceLocation);
                        playerInfo.textureLocations.put(Type.ELYTRA, resourceLocation);
                    }
                    catch (final IOException e) { }
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

    private final NativeImage parseCape(final NativeImage nativeImageIn)
    {
        int imageWidth = 64;
        int imageHeight = 32;
        int imageSrcWidth = nativeImageIn.getWidth();
        int imageSrcHeight = nativeImageIn.getHeight();

        while(imageWidth < imageSrcWidth || imageHeight < imageSrcHeight)
        {
            imageWidth *= 2;
            imageHeight *= 2;
        }

        NativeImage nativeImage = new NativeImage(imageWidth, imageHeight, true);

        for(int x = 0; x < imageSrcWidth; x++)
        {
            for(int y = 0; y < imageSrcHeight; y++)
            {
                nativeImage.setPixelRGBA(x, y, nativeImageIn.getPixelRGBA(x, y));
            }
        }

        nativeImageIn.close();

        return nativeImage;
    }
}
