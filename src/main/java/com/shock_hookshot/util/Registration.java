package com.shock_hookshot.util;

import com.shock_hookshot.blocks.HookshotColumnBlock;
import com.shock_hookshot.blocks.HookshotTargetBlock;
import com.shock_hookshot.items.HookshotItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LIB_REF.MODID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LIB_REF.MODID);

    public static void init(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        BLOCKS.register(bus);
    }

    public static final RegistryObject<HookshotItem> HOOKSHOTITEM = ITEMS.register("hookshot", () -> new HookshotItem(HookshotItem.getItemProperties()));

    public static final RegistryObject<HookshotColumnBlock> HOOKSHOTCOLUMNBLOCK = BLOCKS.register("hookshot_column", HookshotColumnBlock::new);
    public static final RegistryObject<Item> HOOKSHOTCOLUMNBLOCK_ITEM = ITEMS.register("hookshot_column", () -> new BlockItem(HOOKSHOTCOLUMNBLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<HookshotTargetBlock> HOOKSHOTTARGETBLOCK = BLOCKS.register("hookshot_target", HookshotTargetBlock::new);
    public static final RegistryObject<Item> HOOKSHOTTARGETBLOCK_ITEM = ITEMS.register("hookshot_target", () -> new BlockItem(HOOKSHOTTARGETBLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
