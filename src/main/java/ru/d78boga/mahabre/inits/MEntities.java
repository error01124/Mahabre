package ru.d78boga.mahabre.inits;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.mahabre.entities.EntityBatu;
import ru.d78boga.mahabre.entities.renders.RenderFactory;
import ru.d78boga.mahabre.entities.renders.models.ModelBatu;
import ru.d78boga.mahabre.util.Util;

public class MEntities {
	public static MEntities.Registry REGISTRY = new MEntities.Registry();;
	
	public static class Registry implements IRenderRegistry {
		private IForgeRegistry<EntityEntry> registry;
		
		public void registerAll() {
			registry = ForgeRegistries.ENTITIES;
			
			register(EntityBatu.class, "batu");
		}
		
		private <T extends EntityLiving> void register(Class<T> clazz, String name) {
			EntityEntry entityEntry = new EntityEntry(clazz, name);
			ResourceLocation resourceLocation = Util.locate(name);
			entityEntry.setRegistryName(resourceLocation);
			entityEntry.setEgg(new EntityEggInfo(resourceLocation, MapColor.BROWN.colorValue, MapColor.YELLOW.colorValue));
			registry.register(entityEntry);
		}
		
		@SideOnly(Side.CLIENT)
		public void registerAllRenders() {
			registerRender(EntityBatu.class, "batu", new ModelBatu());
		}

		@SideOnly(Side.CLIENT)
		private <T extends EntityLiving> void registerRender(Class<T> clazz, String name, ModelBase model) {
			RenderFactory<T> renderFactory = new RenderFactory<T>();

			renderFactory.setup(name, model);
			RenderingRegistry.registerEntityRenderingHandler(clazz, renderFactory);
		}
	}
}
