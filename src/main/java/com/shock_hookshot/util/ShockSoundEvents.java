package com.shock_hookshot.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;

public class ShockSoundEvents {
    static HashSet<SoundEvent> registeredEvents = new HashSet<SoundEvent>();
    public static final SoundEvent HOOKSHOT_FIRE = createSoundEvent("hookshot_fire");
    public static final SoundEvent HOOKSHOT_TARGET = createSoundEvent("hookshot_target");

    public static void init(IForgeRegistry<SoundEvent> registry) {

    }

    private static SoundEvent createSoundEvent(String name){
        SoundEvent event = new SoundEvent(new ResourceLocation(LIB_REF.MODID, name));
        registeredEvents.add(event);
        return event;
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
        for(SoundEvent event : registeredEvents)
            evt.getRegistry().register(event);
    }
}
