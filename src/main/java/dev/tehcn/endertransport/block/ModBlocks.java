package dev.tehcn.endertransport.block;

import dev.tehcn.endertransport.EnderTransport;
import dev.tehcn.endertransport.block.custom.AlloyFurnaceBlock;
import dev.tehcn.endertransport.util.registry.RegistryHelper;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.util.Rarity;

import java.util.List;

public class ModBlocks {
    public static final Block STEEL_BLOCK = registerBlock("steel_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)), Rarity.COMMON, List.of(ItemGroups.BUILDING_BLOCKS));

    public static final Block ALLOY_FURNACE = registerBlock("alloy_furnace",
            new AlloyFurnaceBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque().requiresTool()),
            Rarity.COMMON,
            List.of(ItemGroups.FUNCTIONAL));

    public static Block registerBlock(String name, Block block, Rarity rarity, List<RegistryKey<ItemGroup>> groups) {
        groups.forEach(group -> ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(block)));
        return RegistryHelper.registerBlock(name, block, rarity);
    }

    public static void registerModBlocks() {
        EnderTransport.LOGGER.info("Registering blocks for " + EnderTransport.MOD_ID);
    }
}
