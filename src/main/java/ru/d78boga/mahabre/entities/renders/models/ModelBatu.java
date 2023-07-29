package ru.d78boga.mahabre.entities.renders.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.d78boga.mahabre.entities.EntityBatu;

@SideOnly(Side.CLIENT)
public class ModelBatu extends ModelBiped {	
	public ModelBatu() {
        this(0.0F, false);
    }

    public ModelBatu(float modelSize, boolean p_i1168_2_) {
        super(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        bipedHead.rotateAngleX += (MathHelper.sin(ageInTicks) * 0.05F) * 10;
        bipedHead.rotateAngleY += (MathHelper.sin(ageInTicks) * 0.1F) * 30;
        bipedLeftArm.rotateAngleX = 0.0F;
        bipedRightArm.rotateAngleY = 0.0F;
        bipedLeftArm.rotateAngleY = 0.0F;
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        boolean flag = entityIn instanceof EntityBatu && ((EntityBatu)entityIn).isArmsRaised();
        float f2 = -(float)Math.PI /  2.25F;
        bipedRightArm.rotateAngleX = flag ? f2 : 0F;
    }
}
