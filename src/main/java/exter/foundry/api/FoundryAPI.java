package exter.foundry.api;

import exter.foundry.api.firearms.IFirearmRound;
import exter.foundry.api.heatable.IHeatProvider;
import exter.foundry.api.material.IMaterialRegistry;
import exter.foundry.api.recipe.manager.IAlloyFurnaceRecipeManager;
import exter.foundry.api.recipe.manager.IAlloyMixerRecipeManager;
import exter.foundry.api.recipe.manager.IAlloyingCrucibleRecipeManager;
import exter.foundry.api.recipe.manager.IAtomizerRecipeManager;
import exter.foundry.api.recipe.manager.IBurnerHeaterFuelManager;
import exter.foundry.api.recipe.manager.ICastingRecipeManager;
import exter.foundry.api.recipe.manager.ICastingTableRecipeManager;
import exter.foundry.api.recipe.manager.IInfuserRecipeManager;
import exter.foundry.api.recipe.manager.IMeltingRecipeManager;
import exter.foundry.api.recipe.manager.IMoldRecipeManager;
import exter.foundry.api.registry.IFluidRegistry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * API for recipes of Foundry machines.
 */
public class FoundryAPI {
	static public int FLUID_AMOUNT_INGOT = 108; //Can be changed by foundry configs!  Nobody else should be setting this value.

	static public int getAmountBlock() {
		return FLUID_AMOUNT_INGOT * 9;
	}

	static public int getAmountPlate() {
		return FLUID_AMOUNT_INGOT;
	}

	static public int getAmountRod() {
		return FLUID_AMOUNT_INGOT / 2;
	}

	static public int getAmountNugget() {
		return FLUID_AMOUNT_INGOT / 9;
	}

	static public int getAmountOre() {
		return FLUID_AMOUNT_INGOT * 2;
	}

	static public int getAmountGear() {
		return FLUID_AMOUNT_INGOT * 4;
	}

	static public int getAmountHelm() {
		return FLUID_AMOUNT_INGOT * 5;
	}

	static public int getAmountChest() {
		return FLUID_AMOUNT_INGOT * 8;
	}

	static public int getAmountLegs() {
		return FLUID_AMOUNT_INGOT * 7;
	}

	static public int getAmountBoots() {
		return FLUID_AMOUNT_INGOT * 4;
	}

	static public int getAmountPickaxe() {
		return FLUID_AMOUNT_INGOT * 3;
	}

	static public int getAmountShovel() {
		return FLUID_AMOUNT_INGOT * 1;
	}

	static public int getAmountAxe() {
		return FLUID_AMOUNT_INGOT * 3;
	}

	static public int getAmountHoe() {
		return FLUID_AMOUNT_INGOT * 2;
	}

	static public int getAmountSword() {
		return FLUID_AMOUNT_INGOT * 2;
	}

	/**
	 * Tank capacity for machines.
	 */
	static public final int CRUCIBLE_TANK_CAPACITY = 6000;
	static public final int CASTER_TANK_CAPACITY = 6000;
	static public final int INFUSER_TANK_CAPACITY = 5000;
	static public final int ALLOYMIXER_TANK_CAPACITY = 2000;
	static public final int ALLOYING_CRUCIBLE_TANK_CAPACITY = 3000;
	static public final int ATOMIZER_TANK_CAPACITY = 6000;
	static public final int ATOMIZER_WATER_TANK_CAPACITY = 6000;

	//Heat loss rates for crucibles.
	static public final int CRUCIBLE_BASIC_TEMP_LOSS_RATE = 750;
	static public final int CRUCIBLE_STANDARD_TEMP_LOSS_RATE = 750;
	static public final int CRUCIBLE_ADVANCED_TEMP_LOSS_RATE = 900;

	//Max temperatures for crucibles (in 1/100 deg Ks).
	static public final int CRUCIBLE_BASIC_MAX_TEMP = 200000;
	static public final int CRUCIBLE_STANDARD_MAX_TEMP = 250000;
	static public final int CRUCIBLE_ADVANCED_MAX_TEMP = 400000;

	@Deprecated
	static public final int CRUCIBLE_MAX_TEMP = 250000;
	@Deprecated
	static public final int CRUCIBLE_TEMP_LOSS_RATE = 750;

	//These fields are set by Foundry during it's preInit phase.
	static public IMeltingRecipeManager recipes_melting;
	static public ICastingRecipeManager recipes_casting;
	static public ICastingTableRecipeManager recipes_casting_table;
	static public IAlloyMixerRecipeManager recipes_alloymixer;
	static public IAlloyingCrucibleRecipeManager recipes_alloyingcrucible;
	static public IInfuserRecipeManager recipes_infuser;
	static public IAlloyFurnaceRecipeManager recipes_alloyfurnace;
	static public IAtomizerRecipeManager recipes_atomizer;
	static public IMoldRecipeManager recipes_mold;

	static public IBurnerHeaterFuelManager burnerheater_fuel;
	static public IMaterialRegistry materials;
	static public IFluidRegistry fluids;

	@CapabilityInject(IHeatProvider.class)
	static public Capability<IHeatProvider> capability_heatprovider;

	@CapabilityInject(IFirearmRound.class)
	static public Capability<IFirearmRound> capability_firearmround;
}
