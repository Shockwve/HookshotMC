package com.shock_hookshot.util;

import net.minecraft.resources.ResourceLocation;

public class LIB_REF {

    public static final String MODID = "shock_hookshot";

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MODID, path);
    }
}
