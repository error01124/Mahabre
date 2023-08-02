package ru.d78boga.mahabre.world.gen.structure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.Template;
import ru.d78boga.mahabre.util.Util;

public class MGenSimpleStructure extends MGenStructure {
	public BlockPos templateSize;
	private Template template;
	private ResourceLocation location;

	public MGenSimpleStructure(MStructureProperties properties, String registryName, String name, MStructuresProvider provider) {
		super(properties, registryName, name, provider);
		location = Util.locate(registryName);
		template = manager.get(mcServer, location);
		templateSize = template.getSize();
	}

	@Override
	protected BlockPos calculateSpawnPosition(BlockPos pos) {
		int x = pos.getX() + 8 - templateSize.getX() / 2;
		int z = pos.getZ() + 8 - templateSize.getZ() / 2;
		return properties.applyHeight(x, z, rand, world);
	}
	
	@Override
	protected void generateStructure(BlockPos pos) {
		this.startPos = pos;
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
		template.addBlocksToWorldChunk(world, pos, placementSettings);
		provider.setStructure(name, pos, templateSize);
	}

	@Override
	protected void generateLoot() {
		for (int x = 0; x <= template.getSize().getX(); x++) {
			for (int y = 0; y <= template.getSize().getY(); y++) {
				for (int z = 0; z <= template.getSize().getZ(); z++) {
					BlockPos lootContainerPos = startPos.add(x, y, z);

					if (world.getTileEntity(lootContainerPos) != null) {
						if (world.getTileEntity(lootContainerPos) instanceof TileEntityChest) {
							TileEntityChest chest = (TileEntityChest) world.getTileEntity(lootContainerPos);
							chest.setLootTable(getLoot(), rand.nextLong());
						}
					}
				}
			}
		}
	}

	@Override
	protected boolean canSpawn() {
		return template != null && !world.isRemote;
	}
}
