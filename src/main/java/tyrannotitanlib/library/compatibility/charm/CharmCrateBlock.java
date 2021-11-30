package tyrannotitanlib.library.compatibility.charm;

//Charm is not for 1.17.1

@Deprecated
public class CharmCrateBlock {}
//extends Block 
//{
//	public static final ResourceLocation CONTENTS = new ResourceLocation("contents");
//
//	private final Item drop;
//
//	public CharmCrateBlock(Properties properties, Item drop) 
//	{
//		super(properties);
//		this.drop = drop;
//	}
//	
//	@Override
//	public boolean hasTileEntity(BlockState state) 
//	{
//		return true;
//	}
//	
//	@Override
//	public BlockEntity createTileEntity(BlockState state, BlockGetter world) 
//	{
//		return TyrannoBlockEntities.CRATE_BLOCK_ENTITY.create();
//	}
//
//	@Override
//	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player entity) 
//	{
//		BlockEntity tileentity = world.getBlockEntity(pos);
//		if(tileentity instanceof CharmCrateBlockEntity) 
//		{
//			CharmCrateBlockEntity shulkerboxtileentity = (CharmCrateBlockEntity) tileentity;
//			if(!world.isClientSide && entity.isCreative() && !shulkerboxtileentity.isEmpty()) 
//			{
//				ItemStack itemstack = drop.getDefaultInstance();
//				CompoundTag compoundnbt = shulkerboxtileentity.saveToTag(new CompoundTag());
//				if(!compoundnbt.isEmpty()) 
//				{
//					itemstack.addTagElement("BlockEntityTag", compoundnbt);
//				}
//
//				if(shulkerboxtileentity.hasCustomName()) 
//				{
//					itemstack.setHoverName(shulkerboxtileentity.getCustomName());
//				}
//
//				ItemEntity itementity = new ItemEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, itemstack);
//				itementity.setDefaultPickUpDelay();
//				world.addFreshEntity(itementity);
//			} 
//			else 
//			{
//				shulkerboxtileentity.unpackLootTable(entity);
//			}
//		}
//
//		super.playerWillDestroy(world, pos, state, entity);
//	}
//
//	@Override
//	public List<ItemStack> getDrops(BlockState state, LootContext.Builder loot) 
//	{
//		BlockEntity tileentity = loot.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
//		if(tileentity instanceof CharmCrateBlockEntity) 
//		{
//			CharmCrateBlockEntity shulkerboxtileentity = (CharmCrateBlockEntity) tileentity;
//			loot = loot.withDynamicDrop(CONTENTS, (lootcontext, stack) -> 
//			{
//				for(int i = 0; i < shulkerboxtileentity.getContainerSize(); ++i) 
//				{
//					stack.accept(shulkerboxtileentity.getItem(i));
//				}
//
//			});
//		}
//
//		return super.getDrops(state, loot);
//	}
//
//	@Override
//	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) 
//	{
//		if(stack.hasCustomHoverName()) 
//		{
//			BlockEntity tileentity = world.getBlockEntity(pos);
//			if(tileentity instanceof CharmCrateBlockEntity) 
//			{
//				((CharmCrateBlockEntity) tileentity).setCustomName(stack.getHoverName());
//			}
//		}
//	}
//
//	@Override
//	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newstate, boolean flag) 
//	{
//		if(!state.is(newstate.getBlock())) 
//		{
//			BlockEntity tileentity = world.getBlockEntity(pos);
//			if(tileentity instanceof CharmCrateBlockEntity) 
//			{
//				world.updateNeighbourForOutputSignal(pos, state.getBlock());
//			}
//
//			super.onRemove(state, world, pos, newstate, flag);
//		}
//	}
//
//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> text, TooltipFlag flag) 
//	{
//		super.appendHoverText(stack, reader, text, flag);
//		CompoundTag compoundnbt = stack.getTagElement("BlockEntityTag");
//		if(compoundnbt != null) 
//		{
//			if(compoundnbt.contains("LootTable", 8)) 
//			{
//				text.add(new TextComponent("???????"));
//			}
//
//			if(compoundnbt.contains("Items", 9)) 
//			{
//				NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
//				ContainerHelper.loadAllItems(compoundnbt, nonnulllist);
//				int i = 0;
//				int j = 0;
//
//				for(ItemStack itemstack : nonnulllist) 
//				{
//					if(!itemstack.isEmpty()) 
//					{
//						++j;
//						if(i <= 4) 
//						{
//							++i;
//							MutableComponent iformattabletextcomponent = itemstack.getHoverName().copy();
//							iformattabletextcomponent.append(" x").append(String.valueOf(itemstack.getCount()));
//							text.add(iformattabletextcomponent);
//						}
//					}
//				}
//
//				if(j - i > 0) 
//				{
//					text.add((new TranslatableComponent("container.shulkerBox.more", j - i)).withStyle(ChatFormatting.ITALIC));
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public PushReaction getPistonPushReaction(BlockState state) 
//	{
//		return PushReaction.DESTROY;
//	}
//
//	@Override
//	public boolean hasAnalogOutputSignal(BlockState state) 
//	{
//		return true;
//	}
//
//	@Override
//	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) 
//	{
//		return AbstractContainerMenu.getRedstoneSignalFromContainer((Container) world.getBlockEntity(pos));
//	}
//
//	@Override
//	public ItemStack getCloneItemStack(BlockGetter reader, BlockPos pos, BlockState state) 
//	{
//		ItemStack itemstack = super.getCloneItemStack(reader, pos, state);
//		CharmCrateBlockEntity shulkerboxtileentity = (CharmCrateBlockEntity) reader.getBlockEntity(pos);
//		CompoundTag compoundnbt = shulkerboxtileentity.saveToTag(new CompoundTag());
//		if(!compoundnbt.isEmpty()) 
//		{
//			itemstack.addTagElement("BlockEntityTag", compoundnbt);
//		}
//
//		return itemstack;
//	}
//}
