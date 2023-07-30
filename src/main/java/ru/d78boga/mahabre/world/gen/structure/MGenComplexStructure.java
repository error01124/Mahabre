package ru.d78boga.mahabre.world.gen.structure;

import java.util.List;
import java.util.Random;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.template.Template;
import ru.d78boga.mahabre.util.Util;

public class MGenComplexStructure extends MGenStructure {
	public List<MGenComplexStructure.Block> blocks = Lists.newArrayList();
	private ResourceLocation lootLocation;
	private int blocksCount = 1;
	
	public MGenComplexStructure(MStructureProperties properties, String registryName, String name, MStructuresProvider provider) {
		super(properties, registryName, name, provider);
		
		if (properties.complex) {
			blocksCount = properties.blocksCount;
		}
		
		lootLocation = Util.locate("chests/" + registryName);
	}

	protected void generateStructure(World world, Random rand, BlockPos pos) {
		for (int i = 1; i <= blocksCount; ++i) {
			int j = rand.nextInt(blocks.size());
			MGenComplexStructure.Block currentBlock = blocks.get(j);
			
			while (currentBlock.positions.contains(pos)) {				
				pos = applyRandomDirection(pos, currentBlock.templateSize);
			}
			
			currentBlock.generate(world, rand, pos);
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
	
	public List<Biome.SpawnListEntry> getSpawnList() {
		if (involvedBlock != null) {	
			List<Biome.SpawnListEntry> result = involvedBlock.getSpawnList();
			involvedBlock = null;
			return result;
		}
		
		return null;
	}
	
	private MGenComplexStructure.Block involvedBlock;
	
	public boolean isInsideStructure(BlockPos pos) {
		for (MGenComplexStructure.Block currentBlock : blocks) {
			if (currentBlock.isInsideStructure(pos))
				involvedBlock = currentBlock;
				return true;
		}
		
		return false;
	}
	
	public class Block extends MGenStructure {
		public BlockPos templateSize;
		public List<BlockPos> positions = Lists.newArrayList();
		private Template template;
		private ResourceLocation location;

		public Block(MStructureProperties properties, String registryName, String name, MStructuresProvider provider) {
			super(properties, registryName, name, provider);
			location = Util.locate(registryName);
			template = manager.get(mcServer, location);
			templateSize = template.getSize();
			blocks.add(this);
		}

		public boolean generate(World worldIn, Random rand, int chunkX, int chunkZ) {
			return false;
		}
		
		public boolean generate(World worldIn, Random rand, BlockPos pos) {
			if (!canSpawnStructureAtCoords(pos)) return false;
			
			generateStructure(worldIn, rand, pos);
			return true;
		}
		
		protected void generateStructure(World world, Random rand, BlockPos pos) {
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
			template.addBlocksToWorldChunk(world, pos, settings);
			generateLoot(pos);
			provider.setStructure(pos, name);
			positions.add(pos);
		}

		protected void generateLoot(BlockPos pos) {
			for (int x = 0; x <= templateSize.getX(); x++) {
				for (int y = 0; y <= templateSize.getY(); y++) {
					for (int z = 0; z <= templateSize.getZ(); z++) {
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
		
		protected boolean canSpawnStructureAtCoords(BlockPos pos) {
			return canSpawn();
		}

		protected boolean canSpawn() {
			return template != null && !world.isRemote;
		}
	}
}
