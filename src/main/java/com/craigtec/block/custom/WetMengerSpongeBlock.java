package com.craigtec.block.custom;

import com.craigtec.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.craigtec.MengerSponge.MOD_ID;
import static java.lang.Math.floorMod;

public class WetMengerSpongeBlock extends WetSpongeBlock {
    /**
     * Access widened by fabric-transitive-access-wideners-v1 to accessible
     *
     * @param properties
     */
    public WetMengerSpongeBlock(Properties properties) {
        super(properties.randomTicks());
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide()) {
            for (Direction direction : Direction.values()) {
                BlockPos adjacentBlockPos = pos.offset(direction.getUnitVec3i());
                if ((level.getBlockState(adjacentBlockPos) == Blocks.AIR.defaultBlockState() ||
                        level.getBlockState(adjacentBlockPos) == Blocks.WATER.defaultBlockState())
                        && isOnMengerLattice(adjacentBlockPos)) {
                    level.setBlock(adjacentBlockPos, ModBlocks.MENGER_SPONGE_BLOCK.defaultBlockState(), 3);
                }
            }
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    protected void onPlace(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
        if (level.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, pos)) {
            level.setBlock(pos, ModBlocks.MENGER_SPONGE_BLOCK.defaultBlockState(), 3);
            level.levelEvent(2009, pos, 0);
            level.playSound(null, pos, SoundEvents.WET_SPONGE_DRIES, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        }
    }

    private boolean isOnMengerLattice(final BlockPos pos) {
        int xMod10 = floorMod(pos.getX(), 10);
        int yMod10 = floorMod(pos.getY(), 10);
        int zMod10 = floorMod(pos.getZ(), 10);

        boolean xCondLvl1 = xMod10 > 2 && xMod10 < 6;
        boolean xCondLvl2 = xMod10 == 1 || xMod10 ==4 || xMod10 == 7;
        boolean xCondBound = xMod10 == 9;
        boolean yCondLvl1 = yMod10 > 2 && yMod10 < 6;
        boolean yCondLvl2 = yMod10 == 1 || yMod10 ==4 || yMod10 == 7;
        boolean yCondBound = yMod10 == 9;
        boolean zCondLvl1 = zMod10 > 2 && zMod10 < 6;
        boolean zCondLvl2 = zMod10 == 1 || zMod10 ==4 || zMod10 == 7;
        boolean zCondBound = zMod10 == 9;

        return !(xCondLvl1 && yCondLvl1 || yCondLvl1 && zCondLvl1 || zCondLvl1 && xCondLvl1 ||
                xCondLvl2 && yCondLvl2 || yCondLvl2 && zCondLvl2 || zCondLvl2 && xCondLvl2 ||
                xCondBound || yCondBound || zCondBound
        );
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    }
}

