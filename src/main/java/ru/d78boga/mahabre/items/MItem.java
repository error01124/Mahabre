package ru.d78boga.mahabre.items;

import net.minecraft.item.Item;
import ru.d78boga.mahabre.inits.MCreativeTabs;

public class MItem extends Item {
	public MItem(String name) {
		super();
		setRegistryName(name);
		setUnlocalizedName(name);
        setCreativeTab(MCreativeTabs.MAHABRE);
	}
}
