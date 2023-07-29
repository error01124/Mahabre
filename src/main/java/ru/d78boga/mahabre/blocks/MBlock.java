package ru.d78boga.mahabre.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import ru.d78boga.mahabre.inits.MCreativeTabs;

public class MBlock extends Block {
	public MBlock(Material material, String name) {
		super(material);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(MCreativeTabs.MAHABRE);
	}

    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    public boolean isFullCube(IBlockState state) {
        return true;
    }
}
