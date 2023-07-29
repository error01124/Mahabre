package ru.d78boga.mahabre.entities.renders;

import javax.annotation.Nonnull;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import ru.d78boga.mahabre.util.Util;

public class RenderEntity<T extends EntityLiving> extends RenderLiving<T> {
    private ResourceLocation texture;

    public RenderEntity(RenderManager rendermanagerIn, String name, ModelBase model) {
        super(rendermanagerIn, model, 0.5F);
        
        texture = Util.locate("textures/entities/" + name + ".png");
    }

    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull T entity) {
        return texture;
    }
}