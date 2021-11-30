package tyrannotitanlib.library.tyrannonetwork.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import tyrannotitanlib.library.tyrannobook.client.TyrannobookHelper;
import tyrannotitanlib.library.tyrannonetwork.util.TileEntityHelper;

public class UpdateLecturnPagePacket implements IThreadSafePacket 
{
	private final BlockPos pos;
	private final String page;

	public UpdateLecturnPagePacket(FriendlyByteBuf buffer) 
	{
		this.pos = buffer.readBlockPos();
		this.page = buffer.readUtf(100);
	}
	
	public UpdateLecturnPagePacket(BlockPos pos, String page) 
	{
		this.pos = pos;
		this.page = page;
	}

	@Override
	public void encode(FriendlyByteBuf buf) 
	{
		buf.writeBlockPos(pos);
		buf.writeUtf(page);
	}

	@Override
	public void handleThreadsafe(Context context) 
	{
		Player player = context.getSender();
		if(player != null && this.page != null) 
		{
			Level world = player.getCommandSenderWorld();
			TileEntityHelper.getTile(LecternBlockEntity.class, world, this.pos).ifPresent(te -> 
			{
				ItemStack stack = te.getBook();
				if(!stack.isEmpty()) 
				{
					TyrannobookHelper.writeSavedPageToBook(stack, this.page);
				}
			});
		}
	}
}
