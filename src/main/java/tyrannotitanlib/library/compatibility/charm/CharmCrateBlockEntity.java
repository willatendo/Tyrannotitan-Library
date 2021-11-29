package tyrannotitanlib.library.compatibility.charm;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;

public class CharmCrateBlockEntity extends LockableLootTileEntity
{
	private NonNullList<ItemStack> itemStacks = NonNullList.withSize(9, ItemStack.EMPTY);

	public CharmCrateBlockEntity() 
	{
		super(TyrannoBlockEntities.CRATE_BLOCK_ENTITY);
	}

	@Override
	public int getContainerSize() 
	{
		return this.itemStacks.size();
	}

	@Override
	protected ITextComponent getDefaultName() 
	{
		return new TranslationTextComponent("container.crate");
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) 
	{
		super.load(state, nbt);
		this.loadFromTag(nbt);
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) 
	{
		super.save(nbt);
		return this.saveToTag(nbt);
	}

	public void loadFromTag(CompoundNBT nbt) 
	{
		this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if(!this.tryLoadLootTable(nbt) && nbt.contains("Items", 9)) 
		{
			ItemStackHelper.loadAllItems(nbt, this.itemStacks);
		}

	}

	public CompoundNBT saveToTag(CompoundNBT nbt) 
	{
		if(!this.trySaveLootTable(nbt)) 
		{
			ItemStackHelper.saveAllItems(nbt, this.itemStacks, false);
		}

		return nbt;
	}

	@Override
	protected NonNullList<ItemStack> getItems() 
	{
		return this.itemStacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) 
	{
		this.itemStacks = items;
	}

	@Override
	protected Container createMenu(int windowId, PlayerInventory player) 
	{
		return new CharmCrateContainer(windowId, player, this);
	}
}
