package ru.d78boga.mahabre.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import ru.d78boga.mahabre.blocks.BlockRecycler;
import ru.d78boga.mahabre.inventory.ContainerRecycler;
import ru.d78boga.mahabre.recipes.RecyclerRecipes;

public class TileEntityRecycler extends TileEntityLockable implements ITickable {
	private NonNullList<ItemStack> itemStacks = NonNullList.<ItemStack>withSize(7, ItemStack.EMPTY);
	private int recycleTime;
	private int totalRecycleTime;
	private ItemStack[] stacks = new ItemStack[6];

	public int getSizeInventory() {
		return itemStacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : itemStacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return itemStacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(itemStacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(itemStacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = itemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		itemStacks.set(index, stack);

		if (stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			totalRecycleTime = getRecycleTime();
			recycleTime = 0;
		}
	}

	public boolean hasCustomName() {
		return false;
	}

	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}

	public String getName() {
		return "container.recycler";
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		itemStacks = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, itemStacks);
		recycleTime = compound.getInteger("RecycleTime");
		totalRecycleTime = compound.getInteger("TotalRecycleTime");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("RecycleTime", (short) recycleTime);
		compound.setInteger("TotalRecycleTime", (short) totalRecycleTime);
		ItemStackHelper.saveAllItems(compound, itemStacks);
		return compound;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void update() {
		if (!world.isRemote) {
			boolean flag = recycleTime == totalRecycleTime;
			if (canRecycle()) {
				++recycleTime;

				if (recycleTime == totalRecycleTime) {
					recycleTime = 0;
					totalRecycleTime = getRecycleTime();
					recycleItem();
				}
			} else {
				recycleTime = 0;
			}

			if (flag != (recycleTime == totalRecycleTime)) {
				BlockRecycler.setState(!flag, world, pos);
				markDirty();
			}
		}
	}

	public boolean isWorking() {
		return 0 < recycleTime && recycleTime < totalRecycleTime;
	}

	public int getRecycleTime() {
		return 200;
	}

	private boolean canRecycle() {
		ItemStack stack0 = itemStacks.get(0);

		if (stack0.isEmpty()) {
			return false;
		} else {
			ItemStack[] recipeStacks = RecyclerRecipes.INSTANCE.getRecyclingResultRandom(stack0);

			if (recipeStacks[0].isEmpty()) {
				return false;
			}

			for (int i = 1; i <= 6; ++i) {
				stacks[i - 1] = itemStacks.get(i);
			}

			for (ItemStack recipeStack : recipeStacks) {
				if (!merge(recipeStack)) return false;
			}

			return true;
		}
	}

	private boolean merge(ItemStack stack) {
		int limit = Math.min(getInventoryStackLimit(), stack.getMaxStackSize());
		int count = stack.getCount();
		Item item = stack.getItem();

		for (int i = 0; i < stacks.length; ++i) {
			if (count <= 0)
				return true;
			ItemStack stack1 = stacks[i];
			if (stack1.isEmpty() || (stack1.getItem() == item)) {
				int y = stack1.getCount();
				int a = count + y;
				int r = limit >= a ? a : limit;
				stacks[i] = new ItemStack(item, r);
				count -= (r - y);
			}
		}

		return false;
	}

	public void recycleItem() {
		ItemStack itemstack = itemStacks.get(0);

		for (int i = 1; i <= 6; ++i) {
			itemStacks.set(i, stacks[i - 1]);
		}

		itemstack.shrink(1);

	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		if (world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	public void openInventory(EntityPlayer player) {
	}

	public void closeInventory(EntityPlayer player) {
	}

	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0;
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return isItemValidForSlot(index, itemStackIn);
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return recycleTime;
		case 1:
			return totalRecycleTime;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 0:
			recycleTime = value;
			break;
		case 1:
			totalRecycleTime = value;
		}
	}

	public int getFieldCount() {
		return 2;
	}

	public void clear() {
		itemStacks.clear();
	}

	public String getGuiID() {
		return "mahabre:recycler";
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerRecycler(playerInventory, this);
	}
}
