package exter.foundry.integration.minetweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import exter.foundry.api.recipe.matcher.IItemMatcher;
import exter.foundry.api.recipe.matcher.ItemStackMatcher;
import exter.foundry.api.recipe.matcher.OreMatcher;
import exter.foundry.integration.minetweaker.orestack.MTOreStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class MTHelper {
	static public String getFluidDescription(FluidStack stack) {
		return String.format("F(%s,%s)", stack.getFluid().getName(), stack.amount);
	}

	static public IItemMatcher getIngredient(IIngredient ingr) {
		if (ingr instanceof IItemStack) { return new ItemStackMatcher(CraftTweakerMC.getItemStack((IItemStack) ingr)); }
		if (ingr instanceof IOreDictEntry) { return new OreMatcher((String) ingr.getInternal()); }
		if (ingr instanceof MTOreStack) { return (OreMatcher) ingr.getInternal(); }
		return null;
	}

	static public String getItemDescription(IItemMatcher obj) {
		if (obj instanceof OreMatcher) {
			OreMatcher stack = (OreMatcher) obj;
			return String.format("O(%s,%d)", stack.getOreName(), stack.getAmount());
		} else {
			ItemStack stack = ((ItemStackMatcher) obj).getItem();
			ResourceLocation item = stack.getItem().getRegistryName();
			return String.format("I(%s:%s:%d,%d)", item.getResourceDomain(), item.getResourcePath(), stack.getItemDamage(), stack.getCount());
		}
	}

	static public String getItemDescription(ItemStack stack) {
		ResourceLocation item = stack.getItem().getRegistryName();
		return String.format("I(%s:%s:%d,%d)", item.getResourceDomain(), item.getResourcePath(), stack.getItemDamage(), stack.getCount());
	}
}
