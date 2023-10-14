package com.dm66.tableflip.item;

import com.dm66.tableflip.TableFlipMod;
import com.dm66.tableflip.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TableFlipMod.MOD_ID);

    public static final RegistryObject<Item> DICE_TABLE_ITEM = ITEMS.register("dice_table_item", () -> new DiceTableBlockItem(ModBlocks.DICE_TABLE.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
