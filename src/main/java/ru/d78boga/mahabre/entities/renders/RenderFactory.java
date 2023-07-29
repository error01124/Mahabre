package ru.d78boga.mahabre.entities.renders;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactory<T extends EntityLiving> implements IRenderFactory<T> {
	private String name;
	private ModelBase model;
	
	public void setup(String name, ModelBase model) {
		this.name = name;
		this.model = model;
	}
	
    @Override
    public Render<? super T> createRenderFor(RenderManager manager) {
        return new RenderEntity<T>(manager, name, model);
    }
}
