package exter.foundry.integration.minetweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import exter.foundry.api.recipe.IMoldRecipe;
import exter.foundry.integration.jei.MoldStationJEI;
import exter.foundry.recipes.MoldRecipe;
import exter.foundry.recipes.manager.MoldRecipeManager;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.foundry.MoldStation")
public class MTMoldStationHandler {
	public static class MoldStationAction extends AddRemoveAction {

		IMoldRecipe recipe;

		public MoldStationAction(IMoldRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		protected void add() {
			MoldRecipeManager.instance.recipes.add(recipe);
			CraftTweakerAPI.getIjeiRecipeRegistry().addRecipe(new MoldStationJEI.Wrapper(null, recipe));
		}

		@Override
		protected void remove() {
			MoldRecipeManager.instance.recipes.remove(recipe);
			CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new MoldStationJEI.Wrapper(null, recipe));
		}

		@Override
		public String getRecipeType() {
			return "mold station";
		}

		@Override
		public String getDescription() {
			StringBuilder builder = new StringBuilder();
			builder.append(String.format("( %d, %d, [", recipe.getWidth(), recipe.getHeight()));
			boolean comma = false;
			for (int r : recipe.getRecipeGrid()) {
				if (comma) {
					builder.append(',');
				}
				builder.append(String.format(" %d", r));
				comma = true;
			}
			builder.append(String.format("] ) -> %s", MTHelper.getItemDescription(recipe.getOutput())));
			return builder.toString();
		}
	}

	@ZenMethod
	static public void addRecipe(IItemStack output, int width, int height, int[] grid) {
		IMoldRecipe recipe = null;
		try {
			recipe = new MoldRecipe(CraftTweakerMC.getItemStack(output), width, height, grid);
		} catch (IllegalArgumentException e) {
			CraftTweakerAPI.logError("Invalid mold station recipe: " + e.getMessage());
			return;
		}
		CraftTweakerAPI.apply((new MoldStationAction(recipe).action_add));
	}

	@ZenMethod
	static public void removeRecipe(int[] grid) {
		if (grid.length != 36) {
			CraftTweakerAPI.logWarning("Invalid mold station grid size: expected 36 instead of " + grid.length);
			return;
		}
		IMoldRecipe recipe = MoldRecipeManager.instance.findRecipe(grid);
		if (recipe == null) {
			CraftTweakerAPI.logWarning("Mold station recipe not found.");
			return;
		}
		CraftTweakerAPI.apply((new MoldStationAction(recipe)).action_remove);
	}
}