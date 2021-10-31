package tyrannotitanlib.library.compatibility.charm;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CharmCrateBlock extends Block 
{
	public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

	private final Item drop;

	public CharmCrateBlock(Properties properties, Item drop) 
	{
		super(properties);
		this.drop = drop;
	}

	@Override
	public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity entity) 
	{
		TileEntity tileentity = world.getBlockEntity(pos);
		if(tileentity instanceof ShulkerBoxTileEntity) 
		{
			ShulkerBoxTileEntity shulkerboxtileentity = (ShulkerBoxTileEntity) tileentity;
			if(!world.isClientSide && entity.isCreative() && !shulkerboxtileentity.isEmpty()) 
			{
				ItemStack itemstack = drop.getDefaultInstance();
				CompoundNBT compoundnbt = shulkerboxtileentity.saveToTag(new CompoundNBT());
				if(!compoundnbt.isEmpty()) 
				{
					itemstack.addTagElement("BlockEntityTag", compoundnbt);
				}

				if(shulkerboxtileentity.hasCustomName()) 
				{
					itemstack.setHoverName(shulkerboxtileentity.getCustomName());
				}

				ItemEntity itementity = new ItemEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, itemstack);
				itementity.setDefaultPickUpDelay();
				world.addFreshEntity(itementity);
			} 
			else 
			{
				shulkerboxtileentity.unpackLootTable(entity);
			}
		}

		super.playerWillDestroy(world, pos, state, entity);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder loot) 
	{
		TileEntity tileentity = loot.getOptionalParameter(LootParameters.BLOCK_ENTITY);
		if(tileentity instanceof ShulkerBoxTileEntity) 
		{
			ShulkerBoxTileEntity shulkerboxtileentity = (ShulkerBoxTileEntity) tileentity;
			loot = loot.withDynamicDrop(CONTENTS, (lootcontext, stack) -> 
			{
				for(int i = 0; i < shulkerboxtileentity.getContainerSize(); ++i) 
				{
					stack.accept(shulkerboxtileentity.getItem(i));
				}

			});
		}

		return super.getDrops(state, loot);
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) 
	{
		if(stack.hasCustomHoverName()) 
		{
			TileEntity tileentity = world.getBlockEntity(pos);
			if(tileentity instanceof ShulkerBoxTileEntity) 
			{
				((ShulkerBoxTileEntity) tileentity).setCustomName(stack.getHoverName());
			}
		}
	}

	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState newstate, boolean flag) 
	{
		if(!state.is(newstate.getBlock())) 
		{
			TileEntity tileentity = world.getBlockEntity(pos);
			if(tileentity instanceof ShulkerBoxTileEntity) 
			{
				world.updateNeighbourForOutputSignal(pos, state.getBlock());
			}

			super.onRemove(state, world, pos, newstate, flag);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag flag) 
	{
		super.appendHoverText(stack, reader, text, flag);
		CompoundNBT compoundnbt = stack.getTagElement("BlockEntityTag");
		if(compoundnbt != null) 
		{
			if(compoundnbt.contains("LootTable", 8)) 
			{
				text.add(new StringTextComponent("???????"));
			}

			if(compoundnbt.contains("Items", 9)) 
			{
				NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
				ItemStackHelper.loadAllItems(compoundnbt, nonnulllist);
				int i = 0;
				int j = 0;

				for(ItemStack itemstack : nonnulllist) 
				{
					if(!itemstack.isEmpty()) 
					{
						++j;
						if(i <= 4) 
						{
							++i;
							IFormattableTextComponent iformattabletextcomponent = itemstack.getHoverName().copy();
							iformattabletextcomponent.append(" x").append(String.valueOf(itemstack.getCount()));
							text.add(iformattabletextcomponent);
						}
					}
				}

				if(j - i > 0) 
				{
					text.add((new TranslationTextComponent("container.shulkerBox.more", j - i)).withStyle(TextFormatting.ITALIC));
				}
			}
		}

	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) 
	{
		return PushReaction.DESTROY;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) 
	{
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, World world, BlockPos pos) 
	{
		return Container.getRedstoneSignalFromContainer((IInventory) world.getBlockEntity(pos));
	}

	@Override
	public ItemStack getCloneItemStack(IBlockReader reader, BlockPos pos, BlockState state) 
	{
		ItemStack itemstack = super.getCloneItemStack(reader, pos, state);
		ShulkerBoxTileEntity shulkerboxtileentity = (ShulkerBoxTileEntity) reader.getBlockEntity(pos);
		CompoundNBT compoundnbt = shulkerboxtileentity.saveToTag(new CompoundNBT());
		if(!compoundnbt.isEmpty()) 
		{
			itemstack.addTagElement("BlockEntityTag", compoundnbt);
		}

		return itemstack;
	}
}
