package exter.foundry.item.ammo;

import java.util.List;

import exter.foundry.creativetab.FoundryTabFirearms;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRoundLumium extends Item {
	static private class FirearmRound extends FirearmRoundBase {
		public FirearmRound() {
			super(5, 60, 30);
		}

		@Override
		public double getBaseDamage(EntityLivingBase entity_hit) {
			double damage = super.getBaseDamage(entity_hit);
			if (entity_hit.isEntityUndead()) {
				damage += 15;
			}
			return damage;
		}
	}

	public ItemRoundLumium() {
		setCreativeTab(FoundryTabFirearms.INSTANCE);
		setUnlocalizedName("roundLumium");
		setRegistryName("roundLumium");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag par4) {
		if (GuiScreen.isShiftKeyDown()) {
			list.add(TextFormatting.BLUE + "Base Damage: 5");
			list.add(TextFormatting.BLUE + "Base Range: 60");
			list.add(TextFormatting.BLUE + "Falloff Range: 30");
			list.add(TextFormatting.YELLOW + "+15 damage to undead creatures.");
		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FirearmRound();
	}
}
