package ru.d78boga.mahabre.world;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class TerrainGeneratorMahabre {
	private World world;
	private Random random;
	private double[] heightMap;
	private double[] mainNoiseRegion;
	private double[] minLimitRegion;
	private double[] maxLimitRegion;
	private double[] depthRegion;
	private NoiseGeneratorOctaves minLimitPerlinNoise;
	private NoiseGeneratorOctaves maxLimitPerlinNoise;
	private NoiseGeneratorOctaves mainPerlinNoise;
	private NoiseGeneratorPerlin surfaceNoise;
	private NoiseGeneratorOctaves depthNoise;
	private float[] biomeWeights;
	private double[] depthBuffer = new double[256];
	private Biome[] biomesForGeneration;

	public TerrainGeneratorMahabre() {
		heightMap = new double[825];

		biomeWeights = new float[25];
		for (int j = -2; j <= 2; ++j) {
			for (int k = -2; k <= 2; ++k) {
				float f = 10.0F / MathHelper.sqrt((j * j + k * k) + 0.2F);
				biomeWeights[j + 2 + (k + 2) * 5] = f;
			}
		}
	}

	public void setBiomesForGeneration(Biome[] biomesForGeneration) {
		this.biomesForGeneration = biomesForGeneration;
	}

	public void setup(World world, Random rand) {
		this.world = world;
		random = rand;

		minLimitPerlinNoise = new NoiseGeneratorOctaves(rand, 16);
		maxLimitPerlinNoise = new NoiseGeneratorOctaves(rand, 16);
		mainPerlinNoise = new NoiseGeneratorOctaves(rand, 8);
		surfaceNoise = new NoiseGeneratorPerlin(rand, 4);
		NoiseGeneratorOctaves noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
		depthNoise = new NoiseGeneratorOctaves(rand, 16);
		NoiseGeneratorOctaves mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);

		InitNoiseGensEvent.ContextOverworld ctx = new InitNoiseGensEvent.ContextOverworld(minLimitPerlinNoise, maxLimitPerlinNoise, mainPerlinNoise, surfaceNoise, noiseGen5, depthNoise, mobSpawnerNoise);
		ctx = TerrainGen.getModdedNoiseGenerators(world, rand, ctx);
		minLimitPerlinNoise = ctx.getLPerlin1();
		maxLimitPerlinNoise = ctx.getLPerlin2();
		mainPerlinNoise = ctx.getPerlin();
		surfaceNoise = ctx.getHeight();
		depthNoise = ctx.getDepth();
	}

	private void generateHeightmap(int chunkX4, int chunkY4, int chunkZ4) {
		depthRegion = depthNoise.generateNoiseOctaves(depthRegion, chunkX4, chunkZ4, 5, 5, 200.0D, 200.0D, 0.5D);
		mainNoiseRegion = mainPerlinNoise.generateNoiseOctaves(mainNoiseRegion, chunkX4, chunkY4, chunkZ4, 5, 33, 5, 8.55515D, 4.277575D, 8.55515D);
		minLimitRegion = minLimitPerlinNoise.generateNoiseOctaves(minLimitRegion, chunkX4, chunkY4, chunkZ4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		maxLimitRegion = maxLimitPerlinNoise.generateNoiseOctaves(maxLimitRegion, chunkX4, chunkY4, chunkZ4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        int i = 0;
        int j = 0;

        for (int k = 0; k < 5; ++k)
        {
            for (int l = 0; l < 5; ++l)
            {
                float f2 = 0.0F;
                float f3 = 0.0F;
                float f4 = 0.0F;
                Biome biome = biomesForGeneration[k + 2 + (l + 2) * 10];

                for (int j1 = -2; j1 <= 2; ++j1)
                {
                    for (int k1 = -2; k1 <= 2; ++k1)
                    {
                        Biome biome1 = biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
                        float f5 = biome1.getBaseHeight();
                        float f6 = biome1.getHeightVariation();
                        float f7 = biomeWeights[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);

                        if (biome1.getBaseHeight() > biome.getBaseHeight())
                        {
                            f7 /= 2.0F;
                        }

                        f2 += f6 * f7;
                        f3 += f5 * f7;
                        f4 += f7;
                    }
                }

                f2 = f2 / f4;
                f3 = f3 / f4;
                f2 = f2 * 0.9F + 0.1F;
                f3 = (f3 * 4.0F - 1.0F) / 8.0F;
                double d7 = depthRegion[j] / 8000.0D;

                if (d7 < 0.0D)
                {
                    d7 = -d7 * 0.3D;
                }

                d7 = d7 * 3.0D - 2.0D;

                if (d7 < 0.0D)
                {
                    d7 = d7 / 2.0D;

                    if (d7 < -1.0D)
                    {
                        d7 = -1.0D;
                    }

                    d7 = d7 / 1.4D;
                    d7 = d7 / 2.0D;
                }
                else
                {
                    if (d7 > 1.0D)
                    {
                        d7 = 1.0D;
                    }

                    d7 = d7 / 8.0D;
                }

                ++j;
                double d8 = (double)f3;
                double d9 = (double)f2;
                d8 = d8 + d7 * 0.2D;
                d8 = d8 * 8.5D / 8.0D;
                double d0 = 8.5D + d8 * 4.0D;

                for (int l1 = 0; l1 < 33; ++l1)
                {
                    double d1 = (l1 - d0) * 12.0D * 128.0D / 256.0D / d9;

                    if (d1 < 0.0D)
                    {
                        d1 *= 4.0D;
                    }

                    double d2 = minLimitRegion[i] / 512.0D;
                    double d3 = maxLimitRegion[i] / 512.0D;
                    double d4 = (mainNoiseRegion[i] / 10.0D + 1.0D) / 2.0D;
                    double d5 = MathHelper.clampedLerp(d2, d3, d4) - d1;

                    if (l1 > 29)
                    {
                        double d6 = (double)((float)(l1 - 29) / 3.0F);
                        d5 = d5 * (1.0D - d6) + -10.0D * d6;
                    }

                    heightMap[i] = d5;
                    ++i;
                }
            }
        }
	}

	public void generate(int chunkX, int chunkZ, ChunkPrimer primer) {
		generateHeightmap(chunkX * 4, 0, chunkZ * 4);

		for (int x4 = 0; x4 < 4; ++x4) {
			int l = x4 * 5;
			int i1 = (x4 + 1) * 5;

			for (int z4 = 0; z4 < 4; ++z4) {
				int k1 = (l + z4) * 33;
				int l1 = (l + z4 + 1) * 33;
				int i2 = (i1 + z4) * 33;
				int j2 = (i1 + z4 + 1) * 33;

				for (int height32 = 0; height32 < 32; ++height32) {
					double d0 = 0.125D;
					double d1 = heightMap[k1 + height32];
					double d2 = heightMap[l1 + height32];
					double d3 = heightMap[i2 + height32];
					double d4 = heightMap[j2 + height32];
					double d5 = (heightMap[k1 + height32 + 1] - d1) * d0;
					double d6 = (heightMap[l1 + height32 + 1] - d2) * d0;
					double d7 = (heightMap[i2 + height32 + 1] - d3) * d0;
					double d8 = (heightMap[j2 + height32 + 1] - d4) * d0;

					for (int h = 0; h < 8; ++h) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						int height = (height32 * 8) + h;

						for (int x = 0; x < 4; ++x) {
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int z = 0; z < 4; ++z) {
								if (height < 2) {
									primer.setBlockState(x4 * 4 + x, height32 * 8 + h, z4 * 4 + z, Blocks.BEDROCK.getDefaultState());
								} else if ((d15 += d16) > 0.0D) {
									primer.setBlockState(x4 * 4 + x, height32 * 8 + h, z4 * 4 + z, Blocks.STONE.getDefaultState());
								}
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, IChunkGenerator generator, Biome[] biomes) {
		if (!ForgeEventFactory.onReplaceBiomeBlocks(generator, x, z, primer, world))
			return;
		depthBuffer = surfaceNoise.getRegion(depthBuffer, (x * 16), (z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				Biome biome = biomes[j + i * 16];
				biome.genTerrainBlocks(world, random, primer, x * 16 + i, z * 16 + j, depthBuffer[j + i * 16]);
			}
		}
	}
}