package ru.d78boga.mahabre.world;

import javax.annotation.Nullable;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.d78boga.mahabre.inits.mDimensions;
import ru.d78boga.mahabre.world.biome.BiomeProviderMahabre;

public class WorldProviderMahabre extends WorldProvider {
	public void init() {
		biomeProvider = new BiomeProviderMahabre(world.getWorldInfo());
	}
	
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return false;
    }
    
    @Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return null;
    }
	
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
		float f = MathHelper.cos(p_76562_1_ * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		float f1 = 0.7529412F;
		float f2 = 0.84705883F;
		float f3 = 1.0F;
		f1 = f1 * (f * 0.94F + 0.06F);
		f2 = f2 * (f * 0.94F + 0.06F);
		f3 = f3 * (f * 0.91F + 0.09F);
		return new Vec3d((double)f1, (double)f2, (double)f3);
	}

	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int x, int z) {
		return true;
	}
	
    protected void generateLightBrightnessTable() {
        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float)i / 15.0F;
            lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.1F;
        }
    }
	
    public boolean canRespawnHere()
    {
        return true;
    }
    
    public boolean isSurfaceWorld()
    {
        return false;
    }
    
    public DimensionType getDimensionType() {
        return mDimensions.MAHABRE;
    }

    public String getSaveFolder() {
        return "Mahabre";
    }

    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorMahabre(world);
    }
}
