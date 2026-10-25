package com.craigtec;

import com.craigtec.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.Block.UPDATE_ALL;

public class MengerSpongeShape {
    // this represents each position in the 3 x 3 menger shape, with one corner at 0, 0, 0
    // and extending in the positive x, y, and z directions
    private static final Vec3i[] mengerShapeBlockPositions = {
            new Vec3i(0, 0, 0),
            new Vec3i(0, 0, 1),
            new Vec3i(0, 0, 2),
            new Vec3i(1, 0, 0),
            new Vec3i(1, 0, 2),
            new Vec3i(2, 0, 0),
            new Vec3i(2, 0, 1),
            new Vec3i(2, 0, 2),
            new Vec3i(0, 1, 0),
            new Vec3i(0, 1, 2),
            new Vec3i(2, 1, 0),
            new Vec3i(2, 1, 2),
            new Vec3i(0, 2, 0),
            new Vec3i(0, 2, 1),
            new Vec3i(0, 2, 2),
            new Vec3i(1, 2, 0),
            new Vec3i(1, 2, 2),
            new Vec3i(2, 2, 0),
            new Vec3i(2, 2, 1),
            new Vec3i(2, 2, 2)
    };

    // this represents the coordinates of the center of the 3 x 3 menger sponge shape
    // this is where the new itemstack will be spawned
    private static final Vec3i mengerShapeCenterPosition = new Vec3i(1, 1, 1);

    // called when a sponge block is placed
    // check if it completes a menger shape in the world
    // if so, delete the sponge blocks in that menger shape and spawn a menger sponge item
    public static void checkForMengerSpongeShape(final Level level, final BlockPos pos) {
        // use each menger shape coordinate as an offset
        // subtracting this offset from the menger shape coordinates effectively checks for the placement of the
        // last block in the shape, no matter where that last block is added
        for (Vec3i offset : mengerShapeBlockPositions) {
            // track whether each block we check in the menger shape is a sponge
            boolean blockPositionsAreAllSponge = true;
            for (Vec3i blockPosition : mengerShapeBlockPositions) {
                BlockState block = level.getBlockState(pos.offset(blockPosition.subtract(offset)));
                if (block.getBlock() != Blocks.SPONGE && block.getBlock() != Blocks.WET_SPONGE) {
                    blockPositionsAreAllSponge = false;
                    break;
                }
            }

            // if each block in the menger shape was a sponge
            if (blockPositionsAreAllSponge) {
                // delete the sponge blocks
                for (Vec3i blockPosition : mengerShapeBlockPositions) {
                    level.setBlock(pos.offset(blockPosition.subtract(offset)), Blocks.AIR.defaultBlockState(), UPDATE_ALL);
                }

                // spawn the menger sponge item
                Vec3i centerBlockPosition = pos.offset(mengerShapeCenterPosition.subtract(offset));
                ItemEntity mengerSpongeItemEntity = new ItemEntity(
                        level,
                        centerBlockPosition.getX() + 0.5,
                        centerBlockPosition.getY() + 0.5,
                        centerBlockPosition.getZ() + 0.5,
                        new ItemStack(ModBlocks.MENGER_SPONGE_BLOCK, 1)
                );
                level.addFreshEntity(mengerSpongeItemEntity);

                // play a sound effect
                level.playSound(mengerSpongeItemEntity, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 2f, 1f);

                break;
            }
        }
    }
}
