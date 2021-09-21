package com.shock_hookshot.items;

import com.shock_hookshot.entity.HookshotEntity;
import com.shock_hookshot.util.ShockSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HookshotItem extends Item {

    private HookshotEntity childEntity;

    public HookshotItem(Properties properties) {
        super(properties);

    }

    public static Properties getItemProperties(){
        return new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC);
    }

    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);


    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand){

        ItemStack handItem = player.getItemInHand(interactionHand);
        //params as follows: x, y, z, SoundEvent to play, SoundSource, volume, pitch
        level.playSound(player, player.getX(), player.getY(), player.getZ(), ShockSoundEvents.HOOKSHOT_FIRE, SoundSource.NEUTRAL, 1.0F, 1.0F);
        player.getCooldowns().addCooldown(this, 100);
        if (!level.isClientSide) {
            if(childEntity == null) {
                HookshotEntity hookshotEntity = new HookshotEntity(level, player);
                hookshotEntity.setParentItem(this);
                hookshotEntity.setItem(handItem);
                hookshotEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(hookshotEntity);

                childEntity = hookshotEntity;
            }
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide());
    }

    public void removeChild(){
        childEntity = null;
    }
}
