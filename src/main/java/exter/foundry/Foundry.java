package exter.foundry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exter.foundry.api.FoundryAPI;
import exter.foundry.block.FoundryBlocks;
import exter.foundry.capability.CapabilityFirearmRound;
import exter.foundry.capability.CapabilityHeatProvider;
import exter.foundry.config.FoundryConfig;
import exter.foundry.entity.EntitySkeletonGun;
import exter.foundry.fluid.FoundryFluids;
import exter.foundry.fluid.LiquidMetalRegistry;
import exter.foundry.init.InitRecipes;
import exter.foundry.integration.ModIntegrationBotania;
import exter.foundry.integration.ModIntegrationEnderIO;
import exter.foundry.integration.ModIntegrationManager;
import exter.foundry.integration.ModIntegrationMinetweaker;
import exter.foundry.integration.ModIntegrationMolten;
import exter.foundry.integration.ModIntegrationTiCon;
import exter.foundry.item.FoundryItems;
import exter.foundry.material.MaterialRegistry;
import exter.foundry.network.MessageTileEntitySync;
import exter.foundry.proxy.CommonFoundryProxy;
import exter.foundry.recipes.manager.AlloyFurnaceRecipeManager;
import exter.foundry.recipes.manager.AlloyMixerRecipeManager;
import exter.foundry.recipes.manager.AlloyingCrucibleRecipeManager;
import exter.foundry.recipes.manager.AtomizerRecipeManager;
import exter.foundry.recipes.manager.BurnerHeaterFuelManager;
import exter.foundry.recipes.manager.CastingRecipeManager;
import exter.foundry.recipes.manager.CastingTableRecipeManager;
import exter.foundry.recipes.manager.InfuserRecipeManager;
import exter.foundry.recipes.manager.MeltingRecipeManager;
import exter.foundry.recipes.manager.MoldRecipeManager;
import exter.foundry.sound.FoundrySounds;
import exter.foundry.tileentity.TileEntityAlloyFurnace;
import exter.foundry.tileentity.TileEntityAlloyMixer;
import exter.foundry.tileentity.TileEntityAlloyingCrucible;
import exter.foundry.tileentity.TileEntityBurnerHeater;
import exter.foundry.tileentity.TileEntityCastingTableBlock;
import exter.foundry.tileentity.TileEntityCastingTableIngot;
import exter.foundry.tileentity.TileEntityCastingTablePlate;
import exter.foundry.tileentity.TileEntityCastingTableRod;
import exter.foundry.tileentity.TileEntityCokeOven;
import exter.foundry.tileentity.TileEntityInductionHeater;
import exter.foundry.tileentity.TileEntityMaterialRouter;
import exter.foundry.tileentity.TileEntityMeltingCrucibleAdvanced;
import exter.foundry.tileentity.TileEntityMeltingCrucibleBasic;
import exter.foundry.tileentity.TileEntityMeltingCrucibleStandard;
import exter.foundry.tileentity.TileEntityMetalAtomizer;
import exter.foundry.tileentity.TileEntityMetalCaster;
import exter.foundry.tileentity.TileEntityMetalInfuser;
import exter.foundry.tileentity.TileEntityMoldStation;
import exter.foundry.tileentity.TileEntityRefractoryHopper;
import exter.foundry.tileentity.TileEntityRefractorySpout;
import exter.foundry.tileentity.TileEntityRefractoryTankAdvanced;
import exter.foundry.tileentity.TileEntityRefractoryTankBasic;
import exter.foundry.tileentity.TileEntityRefractoryTankStandard;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Foundry.MODID, name = Foundry.MODNAME, version = Foundry.MODVERSION, dependencies = "required-after:thermalfoundation;after:jei;after:tconstruct;after:mekanism")
public class Foundry {
	public static final String MODID = "foundry";
	public static final String MODNAME = "Foundry";
	public static final String MODVERSION = "3.0.1.1";

	@Instance(MODID)
	public static Foundry instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "exter.foundry.proxy.ClientFoundryProxy", serverSide = "exter.foundry.proxy.CommonFoundryProxy")
	public static CommonFoundryProxy proxy;

	public static Logger log = LogManager.getLogger(MODID);

	public static SimpleNetworkWrapper network_channel;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ModIntegrationManager.init();
		FoundrySounds.init();
		InitRecipes.init();

		GameRegistry.registerTileEntity(TileEntityMeltingCrucibleBasic.class, MODID + ":melt_crucible_basic");
		GameRegistry.registerTileEntity(TileEntityMeltingCrucibleStandard.class, MODID + ":melt_crucible_standard");
		GameRegistry.registerTileEntity(TileEntityMetalCaster.class, MODID + ":metal_caster");
		GameRegistry.registerTileEntity(TileEntityAlloyMixer.class, MODID + ":alloy_mixer");
		GameRegistry.registerTileEntity(TileEntityMetalInfuser.class, MODID + ":metal_infuser");
		GameRegistry.registerTileEntity(TileEntityAlloyFurnace.class, MODID + ":alloy_furnace");
		GameRegistry.registerTileEntity(TileEntityMoldStation.class, MODID + ":mold_station");
		GameRegistry.registerTileEntity(TileEntityMaterialRouter.class, MODID + ":material_router");
		GameRegistry.registerTileEntity(TileEntityRefractoryHopper.class, MODID + ":refractory_hopper");
		GameRegistry.registerTileEntity(TileEntityMetalAtomizer.class, MODID + ":atomizer");
		GameRegistry.registerTileEntity(TileEntityInductionHeater.class, MODID + ":induction_heater");
		GameRegistry.registerTileEntity(TileEntityBurnerHeater.class, MODID + ":burner_heater");
		GameRegistry.registerTileEntity(TileEntityCastingTableIngot.class, MODID + ":cast_table_ingot");
		GameRegistry.registerTileEntity(TileEntityCastingTablePlate.class, MODID + ":cast_table_plate");
		GameRegistry.registerTileEntity(TileEntityCastingTableRod.class, MODID + ":cast_table_rod");
		GameRegistry.registerTileEntity(TileEntityCastingTableBlock.class, MODID + ":cast_table_block");
		GameRegistry.registerTileEntity(TileEntityRefractorySpout.class, MODID + ":refractory_spout");
		GameRegistry.registerTileEntity(TileEntityMeltingCrucibleAdvanced.class, MODID + ":melt_crucible_advanced");
		GameRegistry.registerTileEntity(TileEntityRefractoryTankBasic.class, MODID + ":tank_basic");
		GameRegistry.registerTileEntity(TileEntityRefractoryTankStandard.class, MODID + ":tank_standard");
		GameRegistry.registerTileEntity(TileEntityRefractoryTankAdvanced.class, MODID + ":tank_advanced");
		GameRegistry.registerTileEntity(TileEntityAlloyingCrucible.class, MODID + ":alloy_crucible");
		if (FoundryConfig.block_cokeoven) {
			GameRegistry.registerTileEntity(TileEntityCokeOven.class, MODID + ":coke_oven");
		}

		EntityRegistry.registerModEntity(new ResourceLocation("foundry", "gun_skeleton"), EntitySkeletonGun.class, "gunSkeleton", 0, this, 80, 1, true);
		LootTableList.register(new ResourceLocation("foundry", "gun_skeleton"));

		EntityRegistry.addSpawn(EntitySkeletonGun.class, 8, 1, 2, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS).toArray(new Biome[0]));

		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModIntegrationManager.postInit();
		InitRecipes.postInit();
		proxy.postInit();
		ModIntegrationManager.afterPostInit();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new FoundryRegistry());
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		if (Loader.isModLoaded("crafttweaker")) ModIntegrationManager.registerIntegration(config, new ModIntegrationMinetweaker());
		if (Loader.isModLoaded("tconstruct")) ModIntegrationManager.registerIntegration(config, new ModIntegrationTiCon());
		ModIntegrationManager.registerIntegration(config, new ModIntegrationMolten());
		if (Loader.isModLoaded("enderio")) ModIntegrationManager.registerIntegration(config, new ModIntegrationEnderIO());
		if (Loader.isModLoaded("botania")) ModIntegrationManager.registerIntegration(config, new ModIntegrationBotania());

		FoundryAPI.fluids = LiquidMetalRegistry.instance;

		FoundryAPI.recipes_melting = MeltingRecipeManager.INSTANCE;
		FoundryAPI.recipes_casting = CastingRecipeManager.INSTANCE;
		FoundryAPI.recipes_casting_table = CastingTableRecipeManager.INSTANCE;
		FoundryAPI.recipes_alloymixer = AlloyMixerRecipeManager.INSTANCE;
		FoundryAPI.recipes_infuser = InfuserRecipeManager.INSTANCE;
		FoundryAPI.recipes_alloyfurnace = AlloyFurnaceRecipeManager.INSTANCE;
		FoundryAPI.recipes_atomizer = AtomizerRecipeManager.INSTANCE;
		FoundryAPI.recipes_mold = MoldRecipeManager.INSTANCE;
		FoundryAPI.recipes_alloyingcrucible = AlloyingCrucibleRecipeManager.INSTANCE;

		FoundryAPI.materials = MaterialRegistry.instance;
		FoundryAPI.burnerheater_fuel = BurnerHeaterFuelManager.INSTANCE;

		CapabilityHeatProvider.init();
		CapabilityFirearmRound.init();

		FoundryConfig.load(config);
		FoundryItems.registerItems(config);
		FoundryBlocks.registerBlocks(config);

		FoundryFluids.init();

		ModIntegrationManager.preInit(config);

		config.save();

		network_channel = NetworkRegistry.INSTANCE.newSimpleChannel("EXTER.FOUNDRY");
		network_channel.registerMessage(MessageTileEntitySync.Handler.class, MessageTileEntitySync.class, 0, Side.SERVER);
		network_channel.registerMessage(MessageTileEntitySync.Handler.class, MessageTileEntitySync.class, 0, Side.CLIENT);

		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		proxy.preInit();
	}
}
