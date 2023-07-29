package ru.d78boga.mahabre.inits;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.mahabre.blocks.BlockConstructionDebris;
import ru.d78boga.mahabre.blocks.BlockGarbageLaying;
import ru.d78boga.mahabre.blocks.BlockRecycler;
import ru.d78boga.mahabre.blocks.BlockResourceLaying;
import ru.d78boga.mahabre.blocks.mBlock;

public class mBlocks {
	// public static ArrayList<Block> BLOCKS = new ArrayList<Block>();
	// BLOCKS
	public static BlockGarbageLaying GARBAGE_LAYING;
	public static BlockConstructionDebris CONSTRUCTION_DEBRIS;
	public static BlockResourceLaying RESOURCE_LAYING;
	public static BlockRecycler RECYCLER;

	public static mBlocks.Registry REGISTRY = new mBlocks.Registry();;

	public static class Registry implements IRegistry {
		private IForgeRegistry<Block> registry;

		public void registerAll() {
			registry = ForgeRegistries.BLOCKS;

			GARBAGE_LAYING = register(new BlockGarbageLaying());
			CONSTRUCTION_DEBRIS = register(new BlockConstructionDebris());
			RESOURCE_LAYING = register(new BlockResourceLaying());
			RECYCLER = register(new BlockRecycler());
		}

		private <T extends mBlock> T register(T block) {
			registry.register(block);
			return block;
		}
	}
}
