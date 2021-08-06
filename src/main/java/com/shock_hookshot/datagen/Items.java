package com.shock_hookshot.datagen;

import com.shock_hookshot.util.LIB_REF;
import com.shock_hookshot.util.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, LIB_REF.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture(
                Registration.HOOKSHOTITEM.get().getRegistryName().getPath(),
                new ResourceLocation("item/handheld"),
                new ResourceLocation(LIB_REF.MODID, "item/hookshot.json"));
    }
}
