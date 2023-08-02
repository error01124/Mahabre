package ru.d78boga.mahabre.inits;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeedFood;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.mahabre.items.ItemGasMask;
import ru.d78boga.mahabre.util.Util;

public class MItems {
	public static Item GARBAGE_LAYING;
	public static Item CONSTRUCTION_DEBRIS;
	public static Item RESOURCE_LAYING;
	public static Item RECYCLER;
	
	public static Item MUSHROOM_GROWTH;
	public static ItemGasMask GAS_MASK;
	
	public static MItems.Registry REGISTRY = new MItems.Registry();;

	public static class Registry implements IRenderRegistry {		
		private ArrayList<Item> items;
		private RenderItem renderItem;
		private IForgeRegistry<Item> registry;

		public void registerAll() {
			registry = ForgeRegistries.ITEMS;
			items = new ArrayList<Item>();
			
			GARBAGE_LAYING = register(MBlocks.GARBAGE_LAYING);
			CONSTRUCTION_DEBRIS = register(MBlocks.CONSTRUCTION_DEBRIS);
			RESOURCE_LAYING = register(MBlocks.RESOURCE_LAYING);
			RECYCLER = register(MBlocks.RECYCLER);
			
			MUSHROOM_GROWTH = register(new ItemSeedFood(1, 0.3F, MBlocks.MUSHROOM_GROWTH, Blocks.SAND), "mushroom_growth");
			GAS_MASK = register(new ItemGasMask());
		}
		
		private <T extends Block> Item register(T block) {
			return register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
		
		private <T extends ItemSeedFood> Item register(T item, String name) {
			return register(item.setRegistryName(name).setUnlocalizedName(name));
		}
		
		private <T extends Item> T register(T item) {
			registry.register(item);
			items.add(item);
			return item;
		}

		@SideOnly(Side.CLIENT)
		public void registerAllRenders() {
			renderItem = Util.mc.getRenderItem();
			
			for (Item item : items) {			
				registerRender(item);
			}
		}
		
		@SideOnly(Side.CLIENT)
		private void registerRender(Item item) {
			renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
