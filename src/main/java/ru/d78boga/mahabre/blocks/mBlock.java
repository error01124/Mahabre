package ru.d78boga.mahabre.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import ru.d78boga.mahabre.inits.mCreativeTabs;

public class mBlock extends Block {
	public mBlock(Material material, String name) {
		super(material);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(mCreativeTabs.MAHABRE);
	}

    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    public boolean isFullCube(IBlockState state) {
        return true;
    }
}
