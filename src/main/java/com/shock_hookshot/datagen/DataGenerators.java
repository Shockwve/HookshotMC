package com.shock_hookshot.datagen;

import com.shock_hookshot.util.LIB_REF;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator =  event.getGenerator();
            if (event.includeServer()){
                generator.addProvider(new Recipes(generator));
            }

            if (event.includeClient()){
                generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
                generator.addProvider(new Items(generator, event.getExistingFileHelper()));
            }
    }
}
