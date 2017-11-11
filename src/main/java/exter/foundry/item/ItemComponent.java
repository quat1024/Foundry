package exter.foundry.item;

import java.util.HashMap;
import java.util.Map;

import exter.foundry.creativetab.FoundryTabMaterials;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemComponent extends Item {
	static public enum SubItem {
		HEATINGCOIL(0, "componentHeatingCoil"),
		REFRACTORYCLAY(1, "componentRefractoryClay"),
		REFRACTORYBRICK(2, "componentRefractoryBrick"),
		GUN_BARREL(3, "componentGunBarrel"),
		REVOLVER_DRUM(4, "componentRevolverDrum"),
		REVOLVER_FRAME(5, "componentRevolverFrame"),
		AMMO_CASING(6, "componentRoundCasing"),
		AMMO_BULLET(7, "componentBullet"),
		AMMO_BULLET_HOLLOW(8, "componentBulletHollow"),
		AMMO_BULLET_JACKETED(9, "componentBulletJacketed"),
		AMMO_PELLET(10, "componentPellet"),
		AMMO_CASING_SHELL(11, "componentShellCasing"),
		SHOTGUN_PUMP(12, "componentShotgunPump"),
		SHOTGUN_FRAME(13, "componentShotgunFrame"),
		AMMO_BULLET_STEEL(14, "componentBulletSteel"),
		AMMO_PELLET_STEEL(15, "componentPelletSteel"),
		REFRACTORYCLAY_SMALL(16, "componentSmallRefractoryClay"),
		INFERNOCLAY(17, "componentInfernoClay"),
		INFERNOBRICK(18, "componentInfernoBrick"),
		AMMO_BULLET_LUMIUM(19, "componentBulletLumium"),
		AMMO_PELLET_LUMIUM(20, "componentPelletLumium"),
		COAL_COKE(21, "componentCoalCoke");

		static private final Map<Integer, SubItem> value_map = new HashMap<>();
		static {
			for (SubItem sub : values()) {
				value_map.put(sub.id, sub);
			}
		}

		static public SubItem fromId(int id) {
			return value_map.get(id);
		}

		public final int id;

		public final String name;

		SubItem(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}

	public ItemComponent() {
		super();
		setCreativeTab(FoundryTabMaterials.tab);
		setHasSubtypes(true);
		setUnlocalizedName("component");
		setRegistryName("component");
	}

	@Override
	public int getItemBurnTime(ItemStack fuel) {
		if (fuel.getItem() == this && fuel.getMetadata() == SubItem.COAL_COKE.id) { return 3200; }
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
		if (isInCreativeTab(tabs)) for (SubItem c : SubItem.values()) {
			list.add(new ItemStack(this, 1, c.id));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "item.foundry." + SubItem.fromId(itemstack.getItemDamage()).name;
	}
}
