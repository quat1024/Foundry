package exter.foundry.inventory;

import exter.foundry.api.firearms.IFirearmRound;
import exter.foundry.item.firearm.ItemFirearm;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class InventoryFirearm implements IInventory {
	protected NonNullList<ItemStack> items;

	protected ItemStack firearm;

	InventoryPlayer player_inv;

	public InventoryFirearm(ItemStack firearm_item, InventoryPlayer player, int size) {
		firearm = firearm_item;
		player_inv = player;
		items = NonNullList.withSize(size, ItemStack.EMPTY);
		int i;
		for (i = 0; i < items.size(); i++) {
			items.set(i, ((ItemFirearm) firearm.getItem()).getAmmo(firearm, i));
		}
	}

	@Override
	public int getSizeInventory() {
		return items.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return items.get(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (!items.get(slot).isEmpty()) {
			ItemStack is;

			if (items.get(slot).getCount() <= amount) {
				is = items.get(slot);
				items.set(slot, ItemStack.EMPTY);
				markDirty();
				return is;
			} else {
				is = items.get(slot).splitStack(amount);

				if (items.get(slot).getCount() == 0) {
					items.set(slot, ItemStack.EMPTY);
				}

				markDirty();
				return is;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		ItemStack ammo = items.get(slot);
		if (!ammo.isEmpty()) {
			items.set(slot, ItemStack.EMPTY);
			return ammo;
		} else {
			return ItemStack.EMPTY;
		}
	}

	public void setInventorySlotContents(int slot, ItemStack stack) {
		items.set(slot, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		player_inv.markDirty();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack.getItem() instanceof IFirearmRound;
	}

	public void save() {
		int i;
		for (i = 0; i < items.size(); i++) {
			((ItemFirearm) firearm.getItem()).setAmmo(firearm, i, items.get(i));
		}
		player_inv.setInventorySlotContents(player_inv.currentItem, firearm);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public boolean isEmpty() {
		for (ItemStack s : items)
			if (!s.isEmpty()) return false;
		return true;
	}
}
