package ru.d78boga.mahabre.inits;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.mahabre.blocks.BlockConstructionDebris;
import ru.d78boga.mahabre.blocks.BlockGarbageLaying;
import ru.d78boga.mahabre.blocks.BlockMushroomGrowth;
import ru.d78boga.mahabre.blocks.BlockRecycler;
import ru.d78boga.mahabre.blocks.BlockResourceLaying;

public class MBlocks {
	public static BlockGarbageLaying GARBAGE_LAYING;
	public static BlockConstructionDebris CONSTRUCTION_DEBRIS;
	public static BlockResourceLaying RESOURCE_LAYING;
	public static BlockRecycler RECYCLER;
	public static BlockMushroomGrowth MUSHROOM_GROWTH;

	public static MBlocks.Registry REGISTRY = new MBlocks.Registry();;

	public static class Registry implements IRegistry {
		private IForgeRegistry<Block> registry;

		public void registerAll() {
			registry = ForgeRegistries.BLOCKS;

			GARBAGE_LAYING = register(new BlockGarbageLaying());
			CONSTRUCTION_DEBRIS = register(new BlockConstructionDebris());
			RESOURCE_LAYING = register(new BlockResourceLaying());
			RECYCLER = register(new BlockRecycler());
			MUSHROOM_GROWTH = register(new BlockMushroomGrowth());
			
		}

		private <T extends Block> T register(T block) {
			registry.register(block);
			return block;
		}
	}
}
