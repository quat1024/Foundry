package exter.foundry.item.ammo;

import java.util.List;

import exter.foundry.creativetab.FoundryTabFirearms;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRoundNormal extends Item {
	public ItemRoundNormal() {
		setCreativeTab(FoundryTabFirearms.INSTANCE);
		setRegistryName("roundNormal");
		setUnlocalizedName("roundNormal");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag par4) {
		super.addInformation(stack, player, list, par4);
		if (GuiScreen.isShiftKeyDown()) {
			list.add(TextFormatting.BLUE + "Base Damage: 10");
			list.add(TextFormatting.BLUE + "Base Range: 60");
			list.add(TextFormatting.BLUE + "Falloff Range: 30");
		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FirearmRoundBase(10, 60, 30);
	}
}
