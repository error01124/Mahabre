package ru.d78boga.mahabre.world.gen.structure;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.structure.template.Template;
import ru.d78boga.mahabre.util.Util;

public class MGenComplexStructure extends MGenStructure {
	private List<Block> blocks = Lists.newArrayList();
	private List<BlockPos> blockPoses = Lists.newArrayList();
	
	public MGenComplexStructure(MStructureProperties properties, String registryName, String name, MStructuresProvider provider) {
		super(properties, registryName, name, provider);
	}

	public void addBlock(Block block) {
		blocks.add(block);
	}
	
	@Override
	protected void generateStructure(BlockPos pos) {
		for (int i = 0; i < properties.blocksCount; ++i) {
			int j = rand.nextInt(blocks.size());
			Block currentBlock = blocks.get(j);

			if (currentBlock.generate(pos)) {
				blockPoses.add(pos);
				
				do {
					pos = applyRandomDirection(pos, currentBlock.templateSize);
				} while (blockPoses.contains(pos));
			}
		}
	}
	
	private BlockPos applyRandomDirection(BlockPos pos, BlockPos size) {
		int x = getDirectionUnit() * size.getX();
		int z = getDirectionUnit() * size.getZ();
		BlockPos result = pos.add(x, 0, z);
		int i = rand.nextInt(3);
		
		if (i == 0) {
			result = pos.add(x, 0, 0);
		} else if (i == 1) {
			result = pos.add(0, 0, z);
		}
		
		return result;
	}
	
	private int getDirectionUnit() {
		return rand.nextInt(2) == 0 ? -1 : 1;
	}

	@Override
	protected void generateLoot() {
		for (Block currentBlock : blocks) {
			currentBlock.generateLoot();
		}
	}
	
	@Override
	public List<SpawnListEntry> getSpawnList() {
		if (checkedBlock != null) {
			List<SpawnListEntry> result = checkedBlock.getSpawnList();
			checkedBlock = null;
			return result; 
		}
		
		return Lists.newArrayList();
	}

	private Block checkedBlock;
	
	@Override
	public boolean isInsideStructure(BlockPos pos) {
		for (MGenComplexStructure.Block currentBlock : blocks) {
			if (currentBlock.isInsideStructure(pos))
				checkedBlock = currentBlock;
				return true;
		}
		
		return false;
	}
	
	public class Block extends MGenStructure {
		private BlockPos templateSize;
		private Template template;
		private ResourceLocation location;
		private MGenComplexStructure parent;
		
		public Block(MStructureProperties properties, String registryName, String name, MStructuresProvider provider) {
			super(properties, registryName, name, provider);
			location = Util.locate(registryName);
			template = manager.get(mcServer, location);
			templateSize = template.getSize();
			parent = MGenComplexStructure.this;
			Collections.copy(spawnList, parent.spawnList);
		}

		@Override
		public boolean generate(int chunkX, int chunkZ) { 
			return false; 
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
			for (int x = 0; x <= templateSize.getX(); x++) {
				for (int y = 0; y <= templateSize.getY(); y++) {
					for (int z = 0; z <= templateSize.getZ(); z++) {
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
		public ResourceLocation getLoot() {
			if (properties.hasLoot) {
				return properties.getLoot(registryName);
			} else {
				return parent.getLoot();
			}
		}
		
		@Override
		protected boolean canSpawnStructureAtCoords(BlockPos pos) {
			return canSpawn();
		}

		@Override
		protected boolean canSpawn() {
			return template != null && !world.isRemote;
		}
	}
}
