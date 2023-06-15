package dev.tehcn.endertransport.item;

import dev.tehcn.endertransport.EnderTransport;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item STEEL_INGOT = registerItem("steel_ingot",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(EnderTransport.MOD_ID, name), item);
    }

    public static void registerModItems() {
        EnderTransport.LOGGER.info("Registering items for " + EnderTransport.MOD_ID);
    }
}
