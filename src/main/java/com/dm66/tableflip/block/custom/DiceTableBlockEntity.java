package com.dm66.tableflip.block.custom;

import com.dm66.tableflip.TableFlipMod;
import com.dm66.tableflip.block.ModBlockEntityTypes;
import com.dm66.tableflip.logic.GameState;
import net.minecraft.client.Game;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.molang.LazyVariable;
import software.bernie.geckolib3.core.molang.MolangParser;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DiceTableBlockEntity extends BlockEntity implements IAnimatable
{
    // ## GeckoLib animation stuff ##
    protected static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("animation.dice_table.rng", ILoopType.EDefaultLoopTypes.LOOP);
    protected static final AnimationBuilder GAMBA = new AnimationBuilder().addAnimation("animation.dice_table.gamba", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    // ## BlockEntity Data ##
    // [unused] Last click location, used for rendering debugging (doesn't belong here)
    private Vec3 last_loc = null;

    // Game state
    private GameState state = null;

    public DiceTableBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.DICE_TABLE_BE.get(), pos, state);
    }

    // ## Field access modifiers ##
    public GameState getGameState(){return state;}

    public void setGameState(GameState state){this.state = state;}

    public Vec3 getLastLoc()
    {
        return last_loc;
    }

    public void setLastLoc(Vec3 loc)
    {
        last_loc = loc;
    }

    @Override
    public void registerControllers(final AnimationData data)
    {
        data.addAnimationController(new AnimationController<>(this, "Random", 0, this::deployAnimController));
    }


    // TODO: controlling variable for rendering, sync with GS
    public boolean roll = false;

    protected <E extends DiceTableBlockEntity> PlayState deployAnimController(final AnimationEvent<E> event)
    {
        //event.getController().setAnimation(IDLE);

        if(event.getController().getAnimationState().equals(AnimationState.Stopped) && roll)
        {
            // ## Set animation parameters ##
            // TODO: hard-code constant values

            MolangParser parser = GeckoLibCache.getInstance().parser;
            double h = 5.0/16, k = 0.7, g = 9.81;
            double t1 = Math.sqrt(2*h/g);
            double t2 = (2*k+1)*t1;
            double t3 = t2 + (2*k*k)*t1;
            double t4 = t3 + (2*k*k*k)*t1;

            Random rng = new Random();
            int dice_roll = rng.nextInt(1, 7);  // Simulate random dice roll; TODO: sync that with GS
            double roll1x = 0, roll1z = 0;
            switch (dice_roll)
            {
                case 1 -> roll1x = roll1z = 0;
                case 2 -> roll1z = 90;
                case 3 -> roll1x = -90;
                case 4 -> roll1x = 90;
                case 5 -> roll1z = -90;
                case 6 -> roll1z = 180;
            }
            dice_roll = rng.nextInt(1, 7);
            double roll2x = 0, roll2z = 0;
            switch (dice_roll)
            {
                case 1 -> roll2x = roll2z = 0;
                case 2 -> roll2z = 90;
                case 3 -> roll2x = -90;
                case 4 -> roll2x = 90;
                case 5 -> roll2z = -90;
                case 6 -> roll2z = 180;
            }

            parser.setValue("query.h", h);
            parser.setValue("query.k", k);
            parser.setValue("query.g", g);
            parser.setValue("query.t1", t1);
            parser.setValue("query.t2", t2);
            parser.setValue("query.t3", t3);
            parser.setValue("query.t4", t4);

            // TODO: Hmm, seems like the dice fell off the table. Whadowedo?
            // also, add 'sidedness' calculation for player's POV
            List<Double> p1, p2;
            do
            {
                p1 = generateDicePath(rng);
                p2 = generateDicePath(rng);
                double d1_x = p1.get(0) + p1.get(2) + p1.get(4) + p1.get(6);
                double d1_z = p1.get(1) + p1.get(3) + p1.get(5) + p1.get(7);
                double d2_x = p2.get(0) + p2.get(2) + p2.get(4) + p2.get(6);
                double d2_z = p2.get(1) + p2.get(3) + p2.get(5) + p2.get(7) + 2;
                double dist_sqr = (d1_x - d2_x) * (d1_x - d2_x) + (d1_z - d2_z) * (d1_z - d2_z);
                if(dist_sqr >= 2) break;
            }while (true);

            parser.setValue("query.v1_x", p1.get(0));
            parser.setValue("query.v1_z", p1.get(1));
            parser.setValue("query.v2_x", p1.get(2));
            parser.setValue("query.v2_z", p1.get(3));
            parser.setValue("query.v3_x", p1.get(4));
            parser.setValue("query.v3_z", p1.get(5));
            parser.setValue("query.v4_x", p1.get(6));
            parser.setValue("query.v4_z", p1.get(7));

            parser.setValue("query.v1_2_x", p2.get(0));
            parser.setValue("query.v1_2_z", p2.get(1));
            parser.setValue("query.v2_2_x", p2.get(2));
            parser.setValue("query.v2_2_z", p2.get(3));
            parser.setValue("query.v3_2_x", p2.get(4));
            parser.setValue("query.v3_2_z", p2.get(5));
            parser.setValue("query.v4_2_x", p2.get(6));
            parser.setValue("query.v4_2_z", p2.get(7));

            parser.setValue("query.roll_x", roll1x);
            parser.setValue("query.roll_z", roll1z);
            parser.setValue("query.roll2_x", roll2x);
            parser.setValue("query.roll2_z", roll2z);

            event.getController().setAnimation(GAMBA);
            event.getController().markNeedsReload();
            roll = false;
        }
        else if(roll) roll = false;  // Don't allow player to reroll the dice while in animation

        return PlayState.CONTINUE;
    }

    // Helper method
    private List<Double> generateDicePath(Random rng)
    {
        return List.of(-rng.nextDouble(3,5), 0d, -rng.nextDouble(0.5, 4), rng.nextDouble(-3,3), -rng.nextDouble(0.5, 3), rng.nextDouble(-3,3), -rng.nextDouble(0.5, 2), rng.nextDouble(-2,2));
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }
    

}
