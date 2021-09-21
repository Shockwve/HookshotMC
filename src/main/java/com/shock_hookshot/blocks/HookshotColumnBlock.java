package com.shock_hookshot.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

public class HookshotColumnBlock extends Block {

    public HookshotColumnBlock() {
        super(Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(1.0f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1));
    }
}
