package core.library.compatibility.charm;

//Charm is not for 1.18.1

@Deprecated(since = "2.0.0", forRemoval = false)
public class CharmCrateBlockEntity {}
//extends RandomizableContainerBlockEntity
//{
//	private NonNullList<ItemStack> itemStacks = NonNullList.withSize(9, ItemStack.EMPTY);
//
//	public CharmCrateBlockEntity(BlockPos pos, BlockState state) 
//	{
//		super(TyrannoBlockEntities.CRATE_BLOCK_ENTITY, pos, state);
//	}
//
//	@Override
//	public int getContainerSize() 
//	{
//		return this.itemStacks.size();
//	}
//
//	@Override
//	protected Component getDefaultName() 
//	{
//		return new TranslatableComponent("container.crate");
//	}
//	
//	@Override
//	public void load(CompoundTag nbt) 
//	{
//		super.load(nbt);
//		this.loadFromTag(nbt);
//	}
//
//	@Override
//	public CompoundTag save(CompoundTag nbt) 
//	{
//		super.save(nbt);
//		return this.saveToTag(nbt);
//	}
//
//	public void loadFromTag(CompoundTag nbt) 
//	{
//		this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
//		if(!this.tryLoadLootTable(nbt) && nbt.contains("Items", 9)) 
//		{
//			ContainerHelper.loadAllItems(nbt, this.itemStacks);
//		}
//
//	}
//
//	public CompoundTag saveToTag(CompoundTag nbt) 
//	{
//		if(!this.trySaveLootTable(nbt)) 
//		{
//			ContainerHelper.saveAllItems(nbt, this.itemStacks, false);
//		}
//
//		return nbt;
//	}
//
//	@Override
//	protected NonNullList<ItemStack> getItems() 
//	{
//		return this.itemStacks;
//	}
//
//	@Override
//	protected void setItems(NonNullList<ItemStack> items) 
//	{
//		this.itemStacks = items;
//	}
//
//	@Override
//	protected AbstractContainerMenu createMenu(int windowId, Inventory player) 
//	{
//		return new CharmCrateContainer(windowId, player, this);
//	}
//}
