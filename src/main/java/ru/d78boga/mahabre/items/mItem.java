package ru.d78boga.mahabre.items;

import net.minecraft.item.Item;
import ru.d78boga.mahabre.inits.mCreativeTabs;

public class mItem extends Item {
	public mItem(String name) {
		super();
		setRegistryName(name);
		setUnlocalizedName(name);
        setCreativeTab(mCreativeTabs.MAHABRE);
	}
}
