package ru.d78boga.mahabre.world;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class MTeleporter extends Teleporter {
	public MTeleporter(WorldServer worldIn) {
		super(worldIn);
	}

    public void placeInPortal(Entity entityIn, float rotationYaw) {
		BlockPos pos = entityIn.getPosition();
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		BlockPos pos1;

		for (j = 256; j > 10; j--) {
			pos1 = new BlockPos(i, j - 1, k);

			if (world.getBlockState(pos1) != Blocks.AIR.getDefaultState()) {
				pos = pos1;
				break;
			}
		}

		entityIn.setLocationAndAngles((double) i, (double) j, (double) k, entityIn.rotationYaw, 0.0F);
		entityIn.motionX = 0.0D;
		entityIn.motionY = 0.0D;
		entityIn.motionZ = 0.0D;
	}
}
