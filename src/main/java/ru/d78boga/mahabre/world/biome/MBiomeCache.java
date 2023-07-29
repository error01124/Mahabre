package ru.d78boga.mahabre.world.biome;

import java.util.List;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.biome.Biome;

public class MBiomeCache {
	private final BiomeProviderMahabre provider;
	private long lastCleanupTime;
	private final Long2ObjectMap<MBiomeCache.Block> cacheMap = new Long2ObjectOpenHashMap<MBiomeCache.Block>(4096);
	private final List<MBiomeCache.Block> cache = Lists.<MBiomeCache.Block>newArrayList();

	public MBiomeCache(BiomeProviderMahabre provider) {
		this.provider = provider;
	}

	public MBiomeCache.Block getBiomeCacheBlock(int x, int z) {
		x = x >> 4;
		z = z >> 4;
		long i = (long) x & 4294967295L | ((long) z & 4294967295L) << 32;
		MBiomeCache.Block biomecache$block = (MBiomeCache.Block) cacheMap.get(i);

		if (biomecache$block == null) {
			biomecache$block = new MBiomeCache.Block(x, z);
			cacheMap.put(i, biomecache$block);
			cache.add(biomecache$block);
		}

		biomecache$block.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
		return biomecache$block;
	}

	public Biome getBiome(int x, int z, Biome defaultValue) {
		Biome biome = getBiomeCacheBlock(x, z).getBiome(x, z);
		return biome == null ? defaultValue : biome;
	}

	public void cleanupCache() {
		long i = MinecraftServer.getCurrentTimeMillis();
		long j = i - lastCleanupTime;

		if (j > 7500L || j < 0L) {
			lastCleanupTime = i;

			for (int k = 0; k < cache.size(); ++k) {
				MBiomeCache.Block biomecache$block = cache.get(k);
				long l = i - biomecache$block.lastAccessTime;

				if (l > 30000L || l < 0L) {
					cache.remove(k--);
					long i1 = (long) biomecache$block.x & 4294967295L | ((long) biomecache$block.z & 4294967295L) << 32;
					cacheMap.remove(i1);
				}
			}
		}
	}

	public Biome[] getCachedBiomes(int x, int z) {
		return getBiomeCacheBlock(x, z).biomes;
	}

	public class Block {
		public Biome[] biomes = new Biome[256];
		public int x;
		public int z;

		public long lastAccessTime;

		public Block(int x, int z) {
			this.x = x;
			this.z = z;
			MBiomeCache.this.provider.getBiomes(biomes, x << 4, z << 4, 16, 16, false);
		}

		public Biome getBiome(int x, int z) {
			return biomes[x & 15 | (z & 15) << 4];
		}
	}
}
