package com.shock_hookshot.items;

import com.shock_hookshot.entity.HookshotEntity;
import com.shock_hookshot.util.ShockSoundEvents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class HookshotItem extends Item {

    public HookshotItem(Properties properties) {
        super(properties);

    }

    public static Properties getItemProperties(){
        return new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand){

        ItemStack handItem = player.getItemInHand(interactionHand);
        //params as follows: x, y, z, SoundEvent to play, SoundSource, volume, pitch
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), ShockSoundEvents.HOOKSHOT_FIRE, SoundSource.NEUTRAL, 1.0F, 1.0F);
        if (!level.isClientSide) {
            HookshotEntity hookshotEntity = new HookshotEntity(level, player);
            hookshotEntity.setItem(handItem);
            hookshotEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(hookshotEntity);
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide());
    }
}
