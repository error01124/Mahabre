package ru.d78boga.mahabre.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.d78boga.mahabre.recipes.RecyclerRecipes;

public class ContainerRecycler extends Container {
	private IInventory recyclerInventory;
	private int recycleTime;
	private int totalRecycleTime;

	public ContainerRecycler(InventoryPlayer playerInventory, IInventory recyclerInventory) {
		this.recyclerInventory = recyclerInventory;
		recyclerInventory.openInventory(playerInventory.player);
		addSlotToContainer(new Slot(recyclerInventory, 0, 8, 20));
		
		for (int i = 0; i < 6; ++i) {
			addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, recyclerInventory, i + 1, 62 + 18 * i, 20));
		}

        for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 9; ++k)
            {
                addSlotToContainer(new Slot(playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 109));
        }
	}

	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, recyclerInventory);
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < listeners.size(); ++i) {
			IContainerListener icontainerlistener = listeners.get(i);

			if (recycleTime != recyclerInventory.getField(0)) {
				icontainerlistener.sendWindowProperty(this, 0, recyclerInventory.getField(0));
			}

			if (totalRecycleTime != recyclerInventory.getField(1)) {
				icontainerlistener.sendWindowProperty(this, 1, recyclerInventory.getField(1));
			}
		}

		recycleTime = recyclerInventory.getField(0);
		totalRecycleTime = recyclerInventory.getField(1);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		recyclerInventory.setField(id, data);
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return recyclerInventory.isUsableByPlayer(playerIn);
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (1 <= index && index <= 6) {
				if (!mergeItemStack(itemstack1, 7, 43, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 0) {
				if (!RecyclerRecipes.INSTANCE.getRecyclingResultRandom(itemstack1)[0].isEmpty()) {
					if (!mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (7 <= index && index < 34) {
					if (!mergeItemStack(itemstack1, 34, 43, false)) {
						return ItemStack.EMPTY;
					}
				} else if (34 <= index && index < 43 && !mergeItemStack(itemstack1, 7, 34, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(itemstack1, 7, 43, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		recyclerInventory.closeInventory(playerIn);
	}
}
