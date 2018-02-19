package exter.foundry.integration.minetweaker;

import com.google.common.base.Preconditions;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import exter.foundry.api.recipe.matcher.IItemMatcher;
import exter.foundry.api.recipe.matcher.ItemStackMatcher;
import exter.foundry.api.recipe.matcher.OreMatcher;
import exter.foundry.integration.minetweaker.orestack.MTOreStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MTHelper {
	static public String getFluidDescription(FluidStack stack) {
		return String.format("<liquid:%s> * %d", stack.getFluid().getName(), stack.amount);
	}

	static public IItemMatcher getIngredient(IIngredient ingr) {
		if (ingr instanceof IItemStack) { return new ItemStackMatcher(CraftTweakerMC.getItemStack((IItemStack) ingr)); }
		if (ingr instanceof IOreDictEntry) { return new OreMatcher((String) ingr.getInternal()); }
		if (ingr instanceof MTOreStack) { return (OreMatcher) ingr.getInternal(); }
		throw new IllegalArgumentException("Invalid IIngredient passed to a foundry method, " + ingr);
	}

	static public String getItemDescription(IItemMatcher obj) {
		Preconditions.checkNotNull(obj, "Cannot get description from a null matcher!");
		if (obj instanceof OreMatcher) {
			OreMatcher stack = (OreMatcher) obj;
			return String.format("<ore:%s> * %d", stack.getOreName(), stack.getAmount());
		} else {
			ItemStack stack = ((ItemStackMatcher) obj).getItem();
			String desc = String.format("<%s:%d>", stack.getItem().getRegistryName(), stack.getItemDamage());
			if (stack.getCount() > 1) desc += " * " + stack.getCount();
			if (stack.hasTagCompound()) desc += " with tag " + stack.getTagCompound().toString();
			return desc;
		}
	}

	static public String getItemDescription(ItemStack stack) {
		String desc = String.format("<%s:%d> * %d", stack.getItem().getRegistryName(), stack.getItemDamage(), stack.getCount());
		if (stack.hasTagCompound()) desc += " with tag " + stack.getTagCompound().toString();
		return desc;
	}
}
