package exter.foundry.integration;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.CraftTweaker;
import exter.foundry.integration.minetweaker.MTAlloyFurnaceHandler;
import exter.foundry.integration.minetweaker.MTAlloyMixerHandler;
import exter.foundry.integration.minetweaker.MTAlloyingCurcibleHandler;
import exter.foundry.integration.minetweaker.MTAtomizerHandler;
import exter.foundry.integration.minetweaker.MTCastingHandler;
import exter.foundry.integration.minetweaker.MTCastingTableHandler;
import exter.foundry.integration.minetweaker.MTInfuserHandler;
import exter.foundry.integration.minetweaker.MTMeltingHandler;
import exter.foundry.integration.minetweaker.MTMoldStationHandler;
import exter.foundry.integration.minetweaker.orestack.OreStackBracketHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModIntegrationMinetweaker implements IModIntegration {

	public static final String CRT = CraftTweaker.MODID;

	@Override
	public String getName() {
		return CRT;
	}

	@Override
	public void onAfterPostInit() {

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onClientInit() {

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onClientPostInit() {

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onClientPreInit() {

	}

	@Override
	public void onInit() {
		CraftTweakerAPI.registerBracketHandler(new OreStackBracketHandler());
		CraftTweakerAPI.registerClass(MTMeltingHandler.class);
		CraftTweakerAPI.registerClass(MTCastingHandler.class);
		CraftTweakerAPI.registerClass(MTCastingTableHandler.class);
		CraftTweakerAPI.registerClass(MTAlloyMixerHandler.class);
		CraftTweakerAPI.registerClass(MTAlloyFurnaceHandler.class);
		CraftTweakerAPI.registerClass(MTAlloyingCurcibleHandler.class);
		CraftTweakerAPI.registerClass(MTAtomizerHandler.class);
		CraftTweakerAPI.registerClass(MTInfuserHandler.class);
		CraftTweakerAPI.registerClass(MTMoldStationHandler.class);
	}

	@Override
	public void onPostInit() {

	}

	@Override
	public void onPreInit(Configuration config) {

	}
}
