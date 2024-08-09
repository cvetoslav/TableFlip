package com.dm66.tableflip.render;

import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class RenderUtil
{
    private static long RTLastUpdateTime = 0;
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
            long d0 = Util.getNanos();
            if (d0 - RTLastUpdateTime > 100000000) // 10^8 ns = 0.1 s
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
