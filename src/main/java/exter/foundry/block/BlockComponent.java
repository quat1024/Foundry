package exter.foundry.block;

import exter.foundry.creativetab.FoundryTabMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class BlockComponent extends Block implements IBlockVariants {

	static public enum EnumVariant implements IStringSerializable {
		CASING_STANDARD(0, "casing_standard", "componentBlockCasingStandard"),
		REFCLAYBLOCK(1, "block_refractoryclay", "componentBlockRefractoryClay"),
		CASING_ADVANCED(2, "casing_advanced", "componentBlockCasingAdvanced"),
		CASING_BASIC(3, "casing_basic", "componentBlockCasingBasic");

		static public EnumVariant fromID(int num) {
			for (EnumVariant m : values()) {
				if (m.id == num) { return m; }
			}
			return null;
		}
		public final int id;
		public final String name;

		public final String model;

		private EnumVariant(int id, String name, String model) {
			this.id = id;
			this.name = name;
			this.model = model;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return getName();
		}
	}

	public static final PropertyEnum<EnumVariant> VARIANT = PropertyEnum.create("variant", EnumVariant.class);

	public BlockComponent() {
		super(Material.ROCK);
		setHardness(1.0F);
		setResistance(8.0F);
		setSoundType(SoundType.STONE);
		setUnlocalizedName("foundry.componentBlock");
		setCreativeTab(FoundryTabMaterials.tab);
		setRegistryName("componentBlock");
	}

	public ItemStack asItemStack(EnumVariant variant) {
		return new ItemStack(this, 1, getMetaFromState(getDefaultState().withProperty(VARIANT, variant)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).id;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumVariant.fromID(meta));
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (EnumVariant m : EnumVariant.values()) {
			list.add(new ItemStack(this, 1, m.id));
		}
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return "tile.foundry." + getStateFromMeta(meta).getValue(VARIANT).model;
	}
}
