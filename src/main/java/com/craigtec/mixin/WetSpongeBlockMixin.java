package com.craigtec.mixin;

import com.craigtec.MengerSpongeShape;
import com.craigtec.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.craigtec.MengerSpongeShape.checkForMengerSpongeShape;

@Mixin(WetSpongeBlock.class)
public abstract class WetSpongeBlockMixin extends Block {
    public WetSpongeBlockMixin(Properties properties) {
        super(properties);
    }

    // when a wet sponge block is placed, check if it completes a menger shape in the world
    // if so, delete the sponge blocks in that menger shape and spawn a menger sponge item
    @Inject(method = "onPlace", at = @At(value = "TAIL"))
    protected void onPlace(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston, CallbackInfo info) {
        if (!level.isClientSide()) {
            checkForMengerSpongeShape(level, pos);
        }
    }
}
