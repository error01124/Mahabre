package ru.d78boga.mahabre.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockGarbageLaying extends mBlock {
	public BlockGarbageLaying() {
		super(Material.ROCK, "garbage_laying");
        setHardness(3.0F);
        setResistance(5.0F);
        setSoundType(SoundType.STONE);
	}
}
