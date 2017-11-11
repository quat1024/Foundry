package exter.foundry.sound;

import exter.foundry.FoundryRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class FoundrySounds {
	static public SoundEvent sound_revolver_fire;
	static public SoundEvent sound_shotgun_fire;
	static public SoundEvent sound_shotgun_cock;

	static public void init() {
		sound_revolver_fire = register("foundry:revolver_fire");
		sound_shotgun_fire = register("foundry:shotgun_fire");
		sound_shotgun_cock = register("foundry:shotgun_cock");
	}

	static private SoundEvent register(String name) {
		ResourceLocation res = new ResourceLocation(name);
		SoundEvent sound = new SoundEvent(res).setRegistryName(res);
		FoundryRegistry.SOUNDS.add(sound);
		return sound;
	}
}
