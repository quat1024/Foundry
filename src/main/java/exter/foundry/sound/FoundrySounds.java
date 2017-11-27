package exter.foundry.sound;

import exter.foundry.Foundry;
import exter.foundry.FoundryRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Foundry.MODID)
public class FoundrySounds {

	public static final SoundEvent REVOLVER_FIRE = null;
	public static final SoundEvent SHOTGUN_FIRE = null;
	public static final SoundEvent SHOTGUN_COCK = null;

	public static void init() {
		register("foundry:revolver_fire");
		register("foundry:shotgun_fire");
		register("foundry:shotgun_cock");
	}

	static void register(String name) {
		ResourceLocation res = new ResourceLocation(name);
		FoundryRegistry.SOUNDS.add(new SoundEvent(res).setRegistryName(res));
	}
}
