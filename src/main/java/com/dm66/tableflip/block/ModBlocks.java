package com.dm66.tableflip.block;

import com.dm66.tableflip.TableFlipMod;
import com.dm66.tableflip.block.custom.DiceTableBlock;
import com.dm66.tableflip.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TableFlipMod.MOD_ID);

    public static final RegistryObject<DiceTableBlock> DICE_TABLE = registerBlock("dice_table", ()-> new DiceTableBlock(BlockBehaviour.Properties.of(Material.DECORATION)), CreativeModeTab.TAB_DECORATIONS);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab)
    {
        RegistryObject<T> ret = BLOCKS.register(name, block);
        registerBlockItem(name, ret, tab);
        return ret;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab)
    {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
