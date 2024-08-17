package com.dm66.tableflip.render;

import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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


    public static final float[] rowX = {6.5f, 5.5f, 4.49f, 3.49f, 2.49f, 1.48f, -0.55f, -1.55f, -2.575f, -3.575f, -4.575f, -5.575f};

    public static int getHoveredStack(Vec3 lookPos)
    {
        for(int i=0;i<12;i++)
        {
            float x1 = (rowX[i] + 8f - 0.16f) / 16f;
            float z1 = (4.45f + 8f - 0.16f - 5 * 0.875f) / 16f;
            float x2 = x1 + 0.05f;
            float z2 = (4.45f + 8f - 0.16f) / 16f + 0.05f;
            if(x1 <= lookPos.x && lookPos.x <= x2 && z1 <= lookPos.z && lookPos.z <= z2) return i;

            z1 = (-4.9f + 8f - 0.16f) / 16f;
            z2 = (-4.9f + 8f - 0.16f + 5 * 0.875f) / 16f + 0.05f;
            if(x1 <= lookPos.x && lookPos.x <= x2 && z1 <= lookPos.z && lookPos.z <= z2) return i + 12;
        }

        return -1;
    }
}
