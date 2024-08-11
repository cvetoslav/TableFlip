package com.dm66.tableflip;

import com.dm66.tableflip.block.ModBlockEntityTypes;
import com.dm66.tableflip.block.ModBlocks;
import com.dm66.tableflip.block.renderer.DiceTableBlockEntityRenderer;
import com.dm66.tableflip.item.ModItems;
import com.dm66.tableflip.networking.Networking;
import com.dm66.tableflip.render.RenderUtil;
import com.mojang.logging.LogUtils;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TableFlipMod.MOD_ID)
public class TableFlipMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "tableflip";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public TableFlipMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);

        GeckoLib.initialize();
    }


    // ## Event Listeners ##
    // Maybe separate them in a new class if they get too many

    @SubscribeEvent
    public void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        Networking.registerMessages();
    }

    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event)
    {
        RenderUtil.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public void onRendererRegister(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntityTypes.DICE_TABLE_BE.get(), DiceTableBlockEntityRenderer::new);
    }

}
