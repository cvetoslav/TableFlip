package com.dm66.tableflip.block;

import com.dm66.tableflip.TableFlipMod;
import com.dm66.tableflip.block.custom.DiceTableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TableFlipMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<DiceTableBlockEntity>> DICE_TABLE_BE =
            BLOCK_ENTITIES.register("dice_table", () -> BlockEntityType.Builder.of(DiceTableBlockEntity::new, ModBlocks.DICE_TABLE.get()).build(null));

    public static void register(IEventBus bus)
    {
        BLOCK_ENTITIES.register(bus);
    }
}
