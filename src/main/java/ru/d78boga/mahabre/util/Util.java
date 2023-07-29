package ru.d78boga.mahabre.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.d78boga.mahabre.Mahabre;

public class Util {
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static ResourceLocation locate(String name) {
		return new ResourceLocation(Mahabre.MODID, name);
	}
}
