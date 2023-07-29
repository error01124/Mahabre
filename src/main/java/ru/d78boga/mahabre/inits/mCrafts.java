package ru.d78boga.mahabre.inits;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import ru.d78boga.mahabre.util.Util;

public class mCrafts {
	public static mCrafts.Registry REGISTRY = new mCrafts.Registry();;

	public static class Registry implements IRegistry {
		public void registerAll() {
			register("gas_mask");
			register("recycler");
		}
		
		private void register(String name) {
			CraftingHelper.register(Util.locate(name), (IRecipeFactory) (context, json) -> CraftingHelper.getRecipe(json, context));
		}
	}
}
