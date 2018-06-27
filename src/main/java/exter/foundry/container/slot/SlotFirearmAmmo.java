package exter.foundry.container.slot;

import exter.foundry.api.FoundryAPI;
import exter.foundry.inventory.InventoryFirearm;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class SlotFirearmAmmo extends Slot {
	private final String type;

	public SlotFirearmAmmo(InventoryFirearm inventory, int index, int x, int y, String ammo_type) {
		super(inventory, index, x, y);
		type = ammo_type;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.hasCapability(FoundryAPI.FIREARM_ROUND_CAP, null) && stack.getCapability(FoundryAPI.FIREARM_ROUND_CAP, null).getRoundType().equals(type);
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
			((InventoryFirearm) inventory).save();
		}
	}
}
