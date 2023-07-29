package ru.d78boga.mahabre.inits;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import ru.d78boga.mahabre.world.WorldProviderMahabre;

public class MDimensions {
	public static DimensionType MAHABRE;

	public static MDimensions.Registry REGISTRY = new MDimensions.Registry();;
	
	public static class Registry implements IRegistry {	
		public void registerAll() {
			MAHABRE = register(4, "mahabre", WorldProviderMahabre.class);
		}
		
		private DimensionType register(int id, String name, Class<? extends WorldProvider> clazz) {
			DimensionType result = DimensionType.register(name, name, id, clazz, false);
			DimensionManager.registerDimension(id, result);
			return result;
		}
	}
}
