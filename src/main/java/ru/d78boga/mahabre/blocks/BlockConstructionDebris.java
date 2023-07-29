package ru.d78boga.mahabre.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockConstructionDebris extends mBlock {
	public BlockConstructionDebris() {
        super(Material.ROCK, "construction_debris");
        setHardness(3.0F);
        setResistance(5.0F);
        setSoundType(SoundType.STONE);
    }
}
