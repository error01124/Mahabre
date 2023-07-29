package ru.d78boga.mahabre.inits;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MCreativeTabs {
	public static CreativeTabs MAHABRE;
	
	public static MCreativeTabs.Registry REGISTRY = new MCreativeTabs.Registry();;

	public static class Registry implements IRegistry {
		public void registerAll() {
			MAHABRE = new CreativeTabs("mahabre") {
				public ItemStack getTabIconItem() {
					return new ItemStack(MItems.GAS_MASK);
				}
			};
		}
	}
}
