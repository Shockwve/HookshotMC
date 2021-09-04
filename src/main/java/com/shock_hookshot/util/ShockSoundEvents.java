package com.shock_hookshot.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ShockSoundEvents {
    public static final SoundEvent HOOKSHOT_FIRE = new SoundEvent(new ResourceLocation(LIB_REF.MODID, "hookshot_fire")).setRegistryName(new ResourceLocation(LIB_REF.MODID, "hookshot_fire"));
    public static final SoundEvent HOOKSHOT_TARGET = new SoundEvent(new ResourceLocation(LIB_REF.MODID, "hookshot_target")).setRegistryName(new ResourceLocation(LIB_REF.MODID, "hookshot_target"));

    public static void init(IForgeRegistry<SoundEvent> registry) {
        registry.register(HOOKSHOT_FIRE);
        registry.register(HOOKSHOT_TARGET);
    }
}
