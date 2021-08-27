package com.shock_hookshot.entity;

import com.shock_hookshot.util.MathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.world.entity.EntityType.*;

public class Hookshot extends ThrowableItemProjectile {

    private final float TICK_RATE = 20;
    private final float speed = 10.0f * TICK_RATE;

    private double x_distance = 0.0f;
    private double y_distance = 0.0f;
    private double z_distance = 0.0f;

    private boolean isStuckInBlock = false;
    private boolean playerIsNear = false;

    public Hookshot(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
    }

    public Hookshot(EntityType<? extends ThrowableItemProjectile> p_37432_, double p_37433_, double p_37434_, double p_37435_, Level p_37436_) {
        super(p_37432_, p_37433_, p_37434_, p_37435_, p_37436_);
    }

    public Hookshot(EntityType<? extends ThrowableItemProjectile> p_37438_, LivingEntity p_37439_, Level p_37440_) {
        super(p_37438_, p_37439_, p_37440_);
    }

    public Hookshot(Level p_37399_, LivingEntity p_37400_){
        super(EntityType.SNOWBALL, p_37400_, p_37399_);
        this.setNoGravity(true);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    public void tick(){
        super.tick();
        Entity owner = this.getOwner();

        Vec3 hookPos = this.position();
        Vec3 playerPos = owner.position();
        float distance = (float)MathHelper.getDistance(hookPos, playerPos);

        if(isStuckInBlock){
            if (owner instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer)owner;
                if (serverplayer.connection.getConnection().isConnected() && serverplayer.level == this.level && !serverplayer.isSleeping()) { // Check the player isn't lagging, they are on the correct level (overworld/nether/end), afk????

                    double x_ = playerPos.x + (x_distance / speed);
                    double y_ = playerPos.y + (y_distance / speed);
                    double z_ = playerPos.z + (z_distance / speed);

                    System.out.println("Hookshot pos: " + hookPos.x + ", " + hookPos.y + ", " + hookPos.z);
                    System.out.println("playerPos pos: " + playerPos.x + ", " + playerPos.y + ", " + playerPos.z);

                    serverplayer.moveTo(playerPos.x + x_, playerPos.y + y_, playerPos.z + z_);
                }
            }
        }

        if (distance <= 1.0f){
            playerIsNear = true;
        }

        if(isStuckInBlock && playerIsNear){
            this.discard();
        }

        if (this.getOwner() == null){
            this.discard();
        }

    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult){
        super.onHitBlock(blockHitResult);


        isStuckInBlock = true;
        this.setDeltaMovement(0.0, 0.0, 0.0); //Stop all movement
        BlockPos blockPos = blockHitResult.getBlockPos();

        Vec3 hookPos = this.position();
        Vec3 playerPos = this.getOwner().position();

        x_distance = Math.abs(hookPos.x) - Math.abs(playerPos.x);
        System.out.println("Doing (" + Math.abs(hookPos.x) + " - " + Math.abs(playerPos.x));
        System.out.println("Result is " + (Math.abs(hookPos.x) - Math.abs(playerPos.x) / speed));
        y_distance = Math.abs(hookPos.y) - Math.abs(playerPos.y);
        z_distance = Math.abs(hookPos.z) - Math.abs(playerPos.z);
    }
}
