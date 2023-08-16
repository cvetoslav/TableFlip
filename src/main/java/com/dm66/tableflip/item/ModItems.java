package com.dm66.tableflip.item;

import com.dm66.tableflip.TableFlipMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TableFlipMod.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
