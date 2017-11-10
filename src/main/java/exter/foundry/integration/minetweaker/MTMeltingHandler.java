package exter.foundry.integration.minetweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import exter.foundry.api.recipe.IMeltingRecipe;
import exter.foundry.integration.jei.MeltingJEI;
import exter.foundry.recipes.MeltingRecipe;
import exter.foundry.recipes.manager.MeltingRecipeManager;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.foundry.Melting")
public class MTMeltingHandler {
	public static class MeltingAction extends AddRemoveAction {

		IMeltingRecipe recipe;

		public MeltingAction(IMeltingRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		protected void add() {
			MeltingRecipeManager.INSTANCE.recipes.add(recipe);
			CraftTweakerAPI.getIjeiRecipeRegistry().addRecipe(new MeltingJEI.Wrapper(null, recipe));
		}

		@Override
		protected void remove() {
			MeltingRecipeManager.INSTANCE.recipes.remove(recipe);
			CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new MeltingJEI.Wrapper(null, recipe));
		}

		@Override
		public String getRecipeType() {
			return "melting";
		}

		@Override
		public String getDescription() {
			return String.format(" %s -> %s", MTHelper.getItemDescription(recipe.getInput()), MTHelper.getFluidDescription(recipe.getOutput()));
		}
	}

	@ZenMethod
	static public void addRecipe(ILiquidStack output, IIngredient input, @Optional int melting_point, @Optional int speed) {

		if (melting_point == 0) {
			melting_point = output.getTemperature();
		}
		if (speed == 0) {
			speed = 100;
		}
		IMeltingRecipe recipe = null;
		try {
			recipe = new MeltingRecipe(MTHelper.getIngredient(input), CraftTweakerMC.getLiquidStack(output), melting_point, speed);
		} catch (IllegalArgumentException e) {
			CraftTweakerAPI.logError("Invalid melting recipe.");
			return;
		}
		CraftTweakerAPI.apply((new MeltingAction(recipe).action_add));
	}

	@ZenMethod
	static public void removeRecipe(IItemStack input) {
		IMeltingRecipe recipe = MeltingRecipeManager.INSTANCE.findRecipe(CraftTweakerMC.getItemStack(input));
		if (recipe == null) {
			CraftTweakerAPI.logWarning("Melting recipe not found.");
			return;
		}
		CraftTweakerAPI.apply((new MeltingAction(recipe)).action_remove);
	}
}
