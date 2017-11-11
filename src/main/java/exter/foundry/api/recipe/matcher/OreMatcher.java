package exter.foundry.api.recipe.matcher;

import java.util.ArrayList;
import java.util.List;

import exter.foundry.api.FoundryUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreMatcher implements IItemMatcher {
	private final String match;
	private final int amount;

	public OreMatcher(String match) {
		this(match, 1);
	}

	public OreMatcher(String match, int amount) {
		if (amount < 1) { throw new IllegalArgumentException("Amount must be > 1"); }
		this.match = match;
		this.amount = amount;
	}

	@Override
	public boolean apply(ItemStack input) {
		return FoundryUtils.isItemInOreDictionary(match, input) && input.getCount() >= amount;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public ItemStack getItem() {
		List<ItemStack> list = OreDictionary.getOres(match);
		if (list.isEmpty()) { return null; }
		ItemStack res = list.get(0).copy();
		res.setCount(amount);
		return res;
	}

	@Override
	public List<ItemStack> getItems() {
		List<ItemStack> list = new ArrayList<>();
		for (ItemStack ore : OreDictionary.getOres(match)) {
			ore = ore.copy();
			ore.setCount(amount);
			list.add(ore);
		}
		return list;
	}

	public String getOreName() {
		return match;
	}
}
