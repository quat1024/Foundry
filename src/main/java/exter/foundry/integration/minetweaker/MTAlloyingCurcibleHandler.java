package exter.foundry.integration.minetweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import exter.foundry.api.recipe.IAlloyingCrucibleRecipe;
import exter.foundry.recipes.AlloyingCrucibleRecipe;
import exter.foundry.recipes.manager.AlloyingCrucibleRecipeManager;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.foundry.AlloyingCrucible")
public class MTAlloyingCurcibleHandler {
	public static class AlloyingCrucibleAction extends AddRemoveAction {

		IAlloyingCrucibleRecipe recipe;

		public AlloyingCrucibleAction(IAlloyingCrucibleRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		protected void add() {
			AlloyingCrucibleRecipeManager.INSTANCE.addRecipe(recipe);
		}

		@Override
		public String getDescription() {
			return String.format("(%s,%s) -> %s", MTHelper.getFluidDescription(recipe.getInputA()), MTHelper.getFluidDescription(recipe.getInputB()), MTHelper.getFluidDescription(recipe.getOutput()));
		}

		@Override
		public String getRecipeType() {
			return "alloying crucible";
		}

		@Override
		protected void remove() {
			AlloyingCrucibleRecipeManager.INSTANCE.removeRecipe(recipe);
		}
	}

	@ZenMethod
	static public void addRecipe(ILiquidStack output, ILiquidStack input_a, ILiquidStack input_b) {

		FluidStack out = CraftTweakerMC.getLiquidStack(output);
		FluidStack in_a = CraftTweakerMC.getLiquidStack(input_a);
		FluidStack in_b = CraftTweakerMC.getLiquidStack(input_b);

		IAlloyingCrucibleRecipe recipe = null;
		try {
			recipe = new AlloyingCrucibleRecipe(out, in_a, in_b);
		} catch (IllegalArgumentException e) {
			CraftTweakerAPI.logError("Invalid alloying crucible recipe: " + e.getMessage());
			return;
		}
		CraftTweakerAPI.apply(new AlloyingCrucibleAction(recipe).action_add);
	}

	@ZenMethod
	static public void removeRecipe(ILiquidStack input_a, ILiquidStack input_b) {

		FluidStack in_a = CraftTweakerMC.getLiquidStack(input_a);
		FluidStack in_b = CraftTweakerMC.getLiquidStack(input_b);

		IAlloyingCrucibleRecipe recipe = AlloyingCrucibleRecipeManager.INSTANCE.findRecipe(in_a, in_b);
		if (recipe == null) {
			recipe = AlloyingCrucibleRecipeManager.INSTANCE.findRecipe(in_b, in_a);
		}
		if (recipe == null) {
			recipe = AlloyingCrucibleRecipeManager.INSTANCE.findRecipe(in_a, in_b);
			CraftTweakerAPI.logWarning("Alloy mixer recipe not found.");
			return;
		}
		CraftTweakerAPI.apply(new AlloyingCrucibleAction(recipe).action_remove);
	}
}
