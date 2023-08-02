package ru.d78boga.mahabre.blocks;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.d78boga.mahabre.inits.MCreativeTabs;

public class BlockMushroomGrowth extends BlockBush {
	public BlockMushroomGrowth() {
		super(Material.VINE);
		setRegistryName("mushroom_growth");
		setUnlocalizedName("mushroom_growth");
		setSoundType(SoundType.PLANT);
		setCreativeTab(MCreativeTabs.MAHABRE);
	}
    
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Blocks.SAND;
	}

	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}

	public int quantityDropped(Random random) {
		return random.nextInt(3);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.STICK;
	}
}
