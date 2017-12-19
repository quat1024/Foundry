package exter.foundry.creativetab;

import exter.foundry.item.FoundryItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class FoundryTabFluids extends CreativeTabs {
	
	public static final FoundryTabFluids INSTANCE = new FoundryTabFluids();

	private FoundryTabFluids() {
		super("foundryFluids");
	}

	@Override
	public ItemStack getTabIconItem() {
		return FoundryItems.item_container.empty(1);
	}

}
