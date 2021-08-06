package com.shock_hookshot.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class HookshotItem extends Item {

    public HookshotItem(Properties properties) {
        super(properties);

    }

    public static Properties getItemProperties(){
        return new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC);
    }

    /*public InteractionResultHolder<ItemStack> use(Level p_43142_, Player p_43143_, InteractionHand p_43144_){


    }*/
}
