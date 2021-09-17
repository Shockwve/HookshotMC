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

public class HookshotEntity extends ThrowableItemProjectile {

    private final float TICK_RATE = 20;
    private final float speed = 1.0f * TICK_RATE; // the higher the float, the slower the travel

    private double x_distance = 0.0f;
    private double y_distance = 0.0f;
    private double z_distance = 0.0f;

    private boolean isStuckInBlock = false;
    private boolean playerIsNear = false;

    private final int lifeSpan = 40; // 3 second life span
    private int currentLife = 0;

    public HookshotEntity(Level p_37399_, LivingEntity p_37400_){
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

        if (!isStuckInBlock){
            currentLife++;
        }

        if (!isStuckInBlock && currentLife >= lifeSpan){
            this.discard();
        }

        Entity owner = this.getOwner();

        Vec3 hookPos = this.position();
        Vec3 playerPos = owner.position();
        float distance = (float)MathHelper.getDistance(hookPos, playerPos);

        if(isStuckInBlock){
            if (owner instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer)owner;
                if (serverplayer.connection.getConnection().isConnected() && serverplayer.level == this.level && !serverplayer.isSleeping()) { // Check the player isn't lagging, they are on the correct level (overworld/nether/end), afk????

                    double x_ = (x_distance / speed);
                    double y_ = (y_distance / speed);
                    double z_ = (z_distance / speed);

                    //System.out.println(String.format("Player pos: %s, %s, %s", playerPos.x, playerPos.y, playerPos.z));
                    //System.out.println(String.format("Moving by: %s, %s, %s", x_, y_, z_));
                    //System.out.println(String.format("Moving player to: %s, %s, %s", playerPos.x + x_, playerPos.y + y_, playerPos.z + z_));
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

        Vec3 hookPos = this.position();
        Vec3 playerPos = this.getOwner().position();

        x_distance = Math.abs(hookPos.x) - Math.abs(playerPos.x);
        y_distance = Math.abs(hookPos.y) - Math.abs(playerPos.y);
        z_distance = Math.abs(hookPos.z) - Math.abs(playerPos.z);
    }
}
