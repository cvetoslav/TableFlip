package com.dm66.tableflip.render;

import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderUtil
{
    private static double RTLastUpdateTime = Double.MIN_VALUE;
    private static BlockHitResult RTBlockLooking = null;

    public static void register(IEventBus bus)
    {
        //bus.addListener(RenderUtil::onLevelRenderEvent);
        MinecraftForge.EVENT_BUS.register(RenderUtil.class);
    }

    @SubscribeEvent
    public static void onLevelRenderEvent(RenderLevelStageEvent event)
    {
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS)
        {
            double d0 = (double) Util.getNanos();
            if (d0 - RTLastUpdateTime > 1.0E8D)
            {
                RTLastUpdateTime = d0;
                Camera cam = event.getCamera();
                Vector3f look = cam.getLookVector();
                look.normalize(); look.mul(5);

                assert Minecraft.getInstance().level != null;
                RTBlockLooking = Minecraft.getInstance().level.clip(new ClipContext(cam.getPosition(), cam.getPosition().add(look.x(), look.y(), look.z()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null));

            }
        }
    }

    public static BlockHitResult getBlockLookingAt()
    {
        return RTBlockLooking;
    }
}
