package exter.foundry.creativetab;

import exter.foundry.item.FoundryItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class FoundryTabFirearms extends CreativeTabs {

	public static final FoundryTabFirearms INSTANCE = new FoundryTabFirearms();

	private FoundryTabFirearms() {
		super("foundryFirearms");
	}

	@Override
	public ItemStack getTabIconItem() {
		return FoundryItems.item_revolver.empty();
	}
}