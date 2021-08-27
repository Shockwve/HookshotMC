package com.shock_hookshot.util;

import net.minecraft.world.phys.Vec3;

public class MathHelper {

    public static double getDistance(Vec3 first, Vec3 second){
        return Math.sqrt(Math.pow(first.x - second.x, 2) + Math.pow(first.y - second.y, 2) + Math.pow(first.z - second.z, 2));
    }
}
