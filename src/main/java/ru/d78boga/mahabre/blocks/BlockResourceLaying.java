package ru.d78boga.mahabre.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockResourceLaying extends mBlock {
	public BlockResourceLaying() {
		super(Material.ROCK, "resource_laying");
        setHardness(3.0F);
        setResistance(5.0F);
        setSoundType(SoundType.STONE);
	}
}
