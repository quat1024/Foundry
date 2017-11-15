package exter.foundry.integration.minetweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import exter.foundry.api.recipe.ICastingRecipe;
import exter.foundry.api.recipe.matcher.IItemMatcher;
import exter.foundry.api.recipe.matcher.ItemStackMatcher;
import exter.foundry.integration.ModIntegrationMinetweaker;
import exter.foundry.recipes.CastingRecipe;
import exter.foundry.recipes.manager.CastingRecipeManager;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.foundry.Casting")
public class MTCastingHandler {
	public static class CastingAction extends AddRemoveAction {

		ICastingRecipe recipe;

		public CastingAction(ICastingRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		protected void add() {
			if (recipe.requiresExtra()) {
				CastingRecipeManager.INSTANCE.addRecipe(0, recipe);
			} else {
				CastingRecipeManager.INSTANCE.addRecipe(recipe);
			}
		}

		@Override
		public String getDescription() {
			IItemMatcher extra = recipe.getInputExtra();
			if (extra == null) { return String.format("( %s, %s ) -> %s", MTHelper.getFluidDescription(recipe.getInput()), MTHelper.getItemDescription(recipe.getMold()), MTHelper.getItemDescription(recipe.getOutput())); }
			return String.format("( %s, %s, %s ) -> %s", MTHelper.getFluidDescription(recipe.getInput()), MTHelper.getItemDescription(recipe.getMold()), MTHelper.getItemDescription(recipe.getInputExtra()), MTHelper.getItemDescription(recipe.getOutput()));
		}

		@Override
		public String getRecipeType() {
			return "casting";
		}

		@Override
		protected void remove() {
			CastingRecipeManager.INSTANCE.removeRecipe(recipe);
		}
	}

	public static class MoldAction extends AddRemoveAction {

		ItemStack mold;

		public MoldAction(ItemStack mold) {
			this.mold = mold;
		}

		@Override
		protected void add() {
			CastingRecipeManager.INSTANCE.addMold(mold);
		}

		@Override
		public String getDescription() {
			return String.format("%s", MTHelper.getItemDescription(mold));
		}

		@Override
		public String getRecipeType() {
			return "casting mold";
		}

		@Override
		protected void remove() {
			CastingRecipeManager.INSTANCE.removeMold(mold);
		}
	}

	@ZenMethod
	static public void addMold(IItemStack mold) {
		ItemStack molditem = CraftTweakerMC.getItemStack(mold);
		if (molditem.isEmpty()) {
			CraftTweakerAPI.logError("Invalid mold item");
			return;
		}
		CraftTweakerAPI.apply(new MoldAction(molditem).action_add);
	}

	@ZenMethod
	static public void addRecipe(IItemStack output, ILiquidStack input, IItemStack mold, @Optional IIngredient extra, @Optional int speed) {
		if (speed == 0) {
			speed = 100;
		}
		ICastingRecipe recipe = null;
		try {
			recipe = new CastingRecipe(new ItemStackMatcher(CraftTweakerMC.getItemStack(output)), CraftTweakerMC.getLiquidStack(input), CraftTweakerMC.getItemStack(mold), MTHelper.getIngredient(extra), speed);
		} catch (IllegalArgumentException e) {
			CraftTweakerAPI.logError("Invalid casting recipe: " + e.getMessage());
			return;
		}
		CraftTweakerAPI.apply(new CastingAction(recipe).action_add);
	}

	@ZenMethod
	static public void removeMold(IItemStack mold) {
		ModIntegrationMinetweaker.queue(() -> {

			ItemStack molditem = CraftTweakerMC.getItemStack(mold);
			if (molditem.isEmpty()) {
				CraftTweakerAPI.logWarning("Invalid mold item");
				return;
			}
			for (ItemStack m : CastingRecipeManager.INSTANCE.getMolds()) {
				if (m.isItemEqual(molditem) && ItemStack.areItemStacksEqual(m, molditem)) {
					CraftTweakerAPI.apply(new MoldAction(m).action_remove);
					return;
				}
			}
			CraftTweakerAPI.logWarning("Mold not found.");
		});
	}

	@ZenMethod
	static public void removeRecipe(ILiquidStack input, IItemStack mold, @Optional IItemStack extra) {
		ModIntegrationMinetweaker.queue(() -> {
			ICastingRecipe recipe = CastingRecipeManager.INSTANCE.findRecipe(CraftTweakerMC.getLiquidStack(input), CraftTweakerMC.getItemStack(mold), CraftTweakerMC.getItemStack(extra));
			if (recipe == null) {
				CraftTweakerAPI.logWarning("Casting recipe not found.");
				return;
			}
			CraftTweakerAPI.apply(new CastingAction(recipe).action_remove);
		});
	}
}
