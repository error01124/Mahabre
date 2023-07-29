package ru.d78boga.mahabre.inits;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class mCreativeTabs {
	public static CreativeTabs MAHABRE;
	
	public static mCreativeTabs.Registry REGISTRY = new mCreativeTabs.Registry();;

	public static class Registry implements IRegistry {
		public void registerAll() {
			MAHABRE = new CreativeTabs("mahabre") {
				public ItemStack getTabIconItem() {
					return new ItemStack(mItems.GAS_MASK);
				}
			};
		}
	}
}
