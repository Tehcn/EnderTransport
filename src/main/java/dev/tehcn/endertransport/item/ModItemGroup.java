package dev.tehcn.endertransport.item;

import dev.tehcn.endertransport.EnderTransport;
import dev.tehcn.endertransport.armor.ModArmorSets;
import dev.tehcn.endertransport.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static ItemGroup ENDER_TRANSPORT = Registry.register(Registries.ITEM_GROUP, new Identifier(EnderTransport.MOD_ID, "ender_transport"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.endertransport"))
                    .icon(() -> new ItemStack(ModItems.STEEL_INGOT)).entries((displayContext, entries) -> {
                        entries.add(ModItems.STEEL_INGOT);
                        entries.add(ModBlocks.STEEL_BLOCK);
                        ModArmorSets.STEEL.getArmorSet().forEach(entries::add);
                        entries.add(ModBlocks.ALLOY_FURNACE);
                    }).build());

    public static void addItemsToItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ModItems.STEEL_INGOT);
            ModArmorSets.STEEL.getArmorSet().forEach(entries::add);
        });
    }
}
