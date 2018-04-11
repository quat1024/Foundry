package exter.foundry.integration;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.CraftTweaker;
import exter.foundry.integration.minetweaker.MTAlloyFurnaceHandler;
import exter.foundry.integration.minetweaker.MTAlloyMixerHandler;
import exter.foundry.integration.minetweaker.MTAlloyingCrucibleHandler;
import exter.foundry.integration.minetweaker.MTAtomizerHandler;
import exter.foundry.integration.minetweaker.MTBurnerFuelHandler;
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

	private static final List<Runnable> CRT_QUEUE = new ArrayList<>();

	@Override
	public String getName() {
		return CraftTweaker.NAME;
	}

	@Override
	public void onAfterPostInit() {
		for (Runnable r : CRT_QUEUE)
			r.run();
		CRT_QUEUE.clear();
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
	}

	@Override
	public void onPostInit() {

	}

	@Override
	public void onPreInit(Configuration config) {
		CraftTweakerAPI.registerBracketHandler(new OreStackBracketHandler());
		CraftTweakerAPI.registerClass(MTMeltingHandler.class);
		CraftTweakerAPI.registerClass(MTCastingHandler.class);
		CraftTweakerAPI.registerClass(MTCastingTableHandler.class);
		CraftTweakerAPI.registerClass(MTAlloyMixerHandler.class);
		CraftTweakerAPI.registerClass(MTAlloyFurnaceHandler.class);
		CraftTweakerAPI.registerClass(MTAlloyingCrucibleHandler.class);
		CraftTweakerAPI.registerClass(MTAtomizerHandler.class);
		CraftTweakerAPI.registerClass(MTInfuserHandler.class);
		CraftTweakerAPI.registerClass(MTMoldStationHandler.class);
		CraftTweakerAPI.registerClass(MTBurnerFuelHandler.class);
	}

	public static void queue(Runnable action) {
		CRT_QUEUE.add(action);
	}
}
