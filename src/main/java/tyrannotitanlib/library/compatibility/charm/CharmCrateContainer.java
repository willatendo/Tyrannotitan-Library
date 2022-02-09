package tyrannotitanlib.library.compatibility.charm;

//Charm is not for 1.18.1

@Deprecated(since = "2.0.0", forRemoval = false)
public class CharmCrateContainer {}
//extends AbstractContainerMenu 
//{
//	private final Container container;
//
//	public CharmCrateContainer(int windowId, Inventory inv) 
//	{
//		this(windowId, inv, new SimpleContainer(9));
//	}
//
//	public CharmCrateContainer(int windowId, Inventory inv, FriendlyByteBuf buffer) 
//	{
//		this(windowId, inv, new SimpleContainer(9));
//	}
//
//	public CharmCrateContainer(int windowId, Inventory playerInv, Container inv) 
//	{
//		super(TyrannoContainers.CRATE_CONTAINER, windowId);
//		checkContainerSize(inv, 9);
//		this.container = inv;
//		inv.startOpen(playerInv.player);
//
//		for(int k = 0; k < 1; ++k) 
//		{
//			for(int l = 0; l < 9; ++l) 
//			{
//				this.addSlot(new ShulkerBoxSlot(inv, l + k * 9, 8 + l * 18, 18 + k * 18));
//			}
//		}
//
//		for(int i1 = 0; i1 < 3; ++i1) 
//		{
//			for(int k1 = 0; k1 < 9; ++k1) 
//			{
//				this.addSlot(new Slot(playerInv, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
//			}
//		}
//
//		for(int j1 = 0; j1 < 9; ++j1) 
//		{
//			this.addSlot(new Slot(playerInv, j1, 8 + j1 * 18, 142));
//		}
//	}
//
//	@Override
//	public boolean stillValid(Player entity) 
//	{
//		return this.container.stillValid(entity);
//	}
//
//	@Override
//	public ItemStack quickMoveStack(Player entity, int index) 
//	{
//		ItemStack itemstack = ItemStack.EMPTY;
//		Slot slot = this.slots.get(index);
//		if(slot != null && slot.hasItem()) 
//		{
//			ItemStack itemstack1 = slot.getItem();
//			itemstack = itemstack1.copy();
//			if(index < this.container.getContainerSize()) 
//			{
//				if(!this.moveItemStackTo(itemstack1, this.container.getContainerSize(), this.slots.size(), true)) 
//				{
//					return ItemStack.EMPTY;
//				}
//			} 
//			else if(!this.moveItemStackTo(itemstack1, 0, this.container.getContainerSize(), false)) 
//			{
//				return ItemStack.EMPTY;
//			}
//
//			if(itemstack1.isEmpty()) 
//			{	
//				slot.set(ItemStack.EMPTY);
//			} 
//			else 
//			{
//				slot.setChanged();
//			}
//		}
//
//		return itemstack;
//	}
//}
