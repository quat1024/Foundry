package exter.foundry.creativetab;

import exter.foundry.item.FoundryItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class FoundryTabFirearms extends CreativeTabs {
	public static FoundryTabFirearms tab = new FoundryTabFirearms();

	private FoundryTabFirearms() {
		super("foundryFirearms");
	}

	@Override
	public ItemStack getTabIconItem() {
		return FoundryItems.item_revolver.empty();
	}
}