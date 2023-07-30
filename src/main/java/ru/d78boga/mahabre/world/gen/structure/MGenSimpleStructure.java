package ru.d78boga.mahabre.world.gen.structure;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;
import ru.d78boga.mahabre.util.Util;

public class MGenSimpleStructure extends MGenStructure {
	public BlockPos templateSize;
	private Template template;
	private ResourceLocation location;
	private ResourceLocation lootLocation;

	public MGenSimpleStructure(MStructureProperties properties, String registryName, String name, MStructuresProvider provider) {
		super(properties, registryName, name, provider);
		location = Util.locate(registryName);
		template = manager.get(mcServer, location);
		templateSize = template.getSize();
		lootLocation = Util.locate("chests/" + registryName);
	}

	protected BlockPos calculateSpawnPosition(int chunkX, int chunkZ) {
		int x = chunkX * 16 + 8 + templateSize.getX() / 2;
		int z = chunkZ * 16 + 8 + templateSize.getZ() / 2;
		int y = calculateGenerationHeight(x, z);
		return new BlockPos(x, y, z);
	}

	protected void generateStructure(World world, Random rand, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
		template.addBlocksToWorldChunk(world, pos, settings);
		generateLoot(pos);
		provider.setStructure(pos, name);
	}

	protected void generateLoot(BlockPos pos) {
		for (int x = 0; x <= template.getSize().getX(); x++) {
			for (int y = 0; y <= template.getSize().getY(); y++) {
				for (int z = 0; z <= template.getSize().getZ(); z++) {
					BlockPos lootContainerPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

					if (world.getTileEntity(lootContainerPos) != null) {
						if (world.getTileEntity(lootContainerPos) instanceof TileEntityChest) {
							TileEntityChest chest = (TileEntityChest) world.getTileEntity(lootContainerPos);
							chest.setLootTable(lootLocation, rand.nextLong());
						}
					}
				}
			}
		}
	}

	protected boolean canSpawn() {
		return template != null && !world.isRemote;
	}
}
