package com.shock_hookshot.datagen;

import com.shock_hookshot.util.LIB_REF;
import com.shock_hookshot.util.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {


    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, LIB_REF.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(Registration.HOOKSHOTCOLUMNBLOCK.get());

        simpleBlock(Registration.HOOKSHOTTARGETBLOCK.get());
    }
}
