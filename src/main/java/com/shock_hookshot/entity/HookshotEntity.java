package com.shock_hookshot.entity;

import com.shock_hookshot.items.HookshotItem;
import com.shock_hookshot.util.MathHelper;
import com.shock_hookshot.util.ShockSoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class HookshotEntity extends ThrowableItemProjectile {

    private final float TICK_RATE = 20;
    private final float speed = 1.0f * TICK_RATE; // the higher the float, the slower the travel

    private double x_distance = 0.0f;
    private double y_distance = 0.0f;
    private double z_distance = 0.0f;
    private float travelDistance = 0.25f;

    private boolean isStuckInBlock = false;
    private boolean playerIsNear = false;

    private final int lifeSpan = 20; // 3 second life span
    private int currentLife = 0;

    private HookshotItem parentItem;
    private Level level;

    public HookshotEntity(Level level, LivingEntity entity){
        super(EntityType.SNOWBALL, entity, level);
        this.level = level;
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
            killEntity();
        }

        Entity owner = this.getOwner();

        Vec3 hookPos = this.position();
        Vec3 playerPos = owner.position();
        float distance = (float)MathHelper.getDistance(hookPos, playerPos);

        if(distance >= 15.0f){
            killEntity();
        }

        if(isStuckInBlock){
            if (owner instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer)owner;
                if (serverplayer.connection.getConnection().isConnected() && serverplayer.level == this.level && !serverplayer.isSleeping()) { // Check the player isn't lagging, they are on the correct level (overworld/nether/end), isn't in a bed

                    double x_ = (x_distance / speed);
                    double y_ = (y_distance / speed);
                    double z_ = (z_distance / speed);

                    serverplayer.moveTo(playerPos.x - x_, playerPos.y - y_, playerPos.z - z_);
                }
            }
        }

        if (distance <= 1.0f){
            playerIsNear = true;
        }

        if(isStuckInBlock && playerIsNear) {
            killEntity();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult){
        super.onHitBlock(blockHitResult);

        isStuckInBlock = true;
        this.setDeltaMovement(0.0, 0.0, 0.0); //Stop all movement

        Vec3 hookPos = this.position();
        Vec3 playerPos = this.getOwner().position();

        x_distance = (Math.abs(playerPos.x) - Math.abs(hookPos.x));
        y_distance = (Math.abs(playerPos.y) - Math.abs(hookPos.y));
        z_distance = (Math.abs(playerPos.z) - Math.abs(hookPos.z));

        this.level.playSound((Player)this.getOwner(), playerPos.x, playerPos.y, playerPos.z, ShockSoundEvents.HOOKSHOT_TARGET, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    public void setParentItem(HookshotItem parentItem){
        this.parentItem = parentItem;
    }

    protected void killEntity(){
        this.discard();

        parentItem.removeChild();

        if (parentItem != null) {
            ((Player) this.getOwner()).getCooldowns().removeCooldown(parentItem);
        }
    }

    protected double getTravelDistance(double point1, double point2){
        int sign = point1 - point2 >= 0.0f ? 1 : -1;

        return Math.abs(point1 - point2) > travelDistance ? sign * travelDistance : point1 - point2;
    }
}
