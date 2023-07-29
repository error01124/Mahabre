package ru.d78boga.mahabre.inits;

import java.util.ArrayList;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.mahabre.blocks.MBlock;
import ru.d78boga.mahabre.items.ItemGasMask;
import ru.d78boga.mahabre.items.MItem;
import ru.d78boga.mahabre.util.Util;

public class MItems {
	public static Item GARBAGE_LAYING;
	public static Item CONSTRUCTION_DEBRIS;
	public static Item RESOURCE_LAYING;
	public static Item RECYCLER;

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
			
			GAS_MASK = register(new ItemGasMask());
		}
		
		private <T extends MBlock> Item register(T block) {
			Item item = new ItemBlock(block).setRegistryName(block.getRegistryName());
			registry.register(item);
			items.add(item);
			return item;
		}
		
		private <T extends MItem> T register(T item) {
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
