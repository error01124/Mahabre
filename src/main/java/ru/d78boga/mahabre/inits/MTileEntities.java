package ru.d78boga.mahabre.inits;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.d78boga.mahabre.tileentities.TileEntityRecycler;

public class MTileEntities {
	public static MTileEntities.Registry REGISTRY = new MTileEntities.Registry();;

	public static class Registry implements IRegistry {
		public void registerAll() {
			register(TileEntityRecycler.class, "tileEntityRecycler");
		}

		private <T extends TileEntity> void register(Class<T> clazz, String name) {
			GameRegistry.registerTileEntity(clazz, name);
		}
	}
}