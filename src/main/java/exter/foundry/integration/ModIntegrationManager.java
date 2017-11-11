package exter.foundry.integration;

import java.util.HashMap;
import java.util.Map;

import exter.foundry.Foundry;
import exter.foundry.config.FoundryConfig;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class ModIntegrationManager {
	static private Map<String, IModIntegration> integrations = new HashMap<>();

	static public void afterPostInit() {
		for (IModIntegration m : integrations.values()) {
			Foundry.log.info("AfterPostInit integration: " + m.getName());
			m.onAfterPostInit();
		}
	}

	@SideOnly(Side.CLIENT)
	static public void clientInit() {
		for (IModIntegration m : integrations.values()) {
			m.onClientInit();
		}
	}

	@SideOnly(Side.CLIENT)
	static public void clientPostInit() {
		for (IModIntegration m : integrations.values()) {
			m.onClientPostInit();
		}
	}

	@SideOnly(Side.CLIENT)
	static public void clientPreInit() {
		for (IModIntegration m : integrations.values()) {
			m.onClientPreInit();
		}
	}

	static public IModIntegration getIntegration(String name) {
		return integrations.get(name);
	}

	static public void init() {
		for (IModIntegration m : integrations.values()) {
			Foundry.log.info("Init integration: " + m.getName());
			m.onInit();
		}
	}

	static public void postInit() {
		for (IModIntegration m : integrations.values()) {
			Foundry.log.info("PostInit integration: " + m.getName());
			m.onPostInit();
		}
	}

	static public void preInit(Configuration config) {
		for (IModIntegration m : integrations.values()) {
			Foundry.log.info("PreInit integration: " + m.getName());
			m.onPreInit(config);
		}
	}

	static public void registerIntegration(Configuration config, IModIntegration imod) {
		String name = imod.getName();
		boolean enable = FoundryConfig.getAndRemove(config, "integration", "enable." + name, true);
		enable = config.getBoolean("enable", "integration." + name, true, "Enable/disable mod integration.");
		if (enable) {
			integrations.put(name, imod);
		}
	}

}
