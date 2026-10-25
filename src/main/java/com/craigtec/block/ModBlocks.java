package com.craigtec.block;

import com.craigtec.MengerSponge;
import com.craigtec.block.custom.MengerSpongeBlock;
import com.craigtec.block.custom.WetMengerSpongeBlock;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

import static com.craigtec.MengerSponge.id;

public class ModBlocks {
    public static final Block MENGER_SPONGE_BLOCK = registerBlock(
            "menger_sponge_block",
            properties -> new MengerSpongeBlock(
                    properties.mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.SPONGE)
            )
    );

    public static final Block WET_MENGER_SPONGE_BLOCK = registerBlock(
            "wet_menger_sponge_block",
            properties -> new WetMengerSpongeBlock(
                    properties.mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.WET_SPONGE)
            )
    );

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> function) {
        Block toRegister = function.apply(
                BlockBehaviour.Properties.of().setId(
                        ResourceKey.create(
                                Registries.BLOCK,
                                id(name)
                        )
                )
        );
        registerBlockItem(name, toRegister);
        return Registry.register(
                BuiltInRegistries.BLOCK,
                id(name),
                toRegister
        );
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(
                BuiltInRegistries.ITEM,
                id(name),
                new BlockItem(
                        block,
                        new Item.Properties().useBlockDescriptionPrefix().setId(
                                ResourceKey.create(
                                        Registries.ITEM,
                                        id(name)
                                )
                        )
                )
        );
    }

    public static void registerModBlocks() {
        MengerSponge.LOGGER.info("Registering Mod Blocks for " + MengerSponge.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> {
            output.accept(MENGER_SPONGE_BLOCK);
            output.accept(WET_MENGER_SPONGE_BLOCK);
        });
    }
}
