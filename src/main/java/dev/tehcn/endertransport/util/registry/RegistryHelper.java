package dev.tehcn.endertransport.util.registry;

import dev.tehcn.endertransport.EnderTransport;
import dev.tehcn.endertransport.mixin.AccessorEntityModelLayers;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class RegistryHelper {
    public static Identifier id(String path) {
        return new Identifier(EnderTransport.MOD_ID, path);
    }

    public static Item registerItem(String path, Item item) {
        Registry.register(Registries.ITEM, id(path), item);
        return item;
    }

    public static Block registerBlock(String path, Block block, Rarity rarity) {
        Registry.register(Registries.BLOCK, id(path), block);
        Registry.register(Registries.ITEM, id(path), new BlockItem(block, new FabricItemSettings().rarity(rarity)));
        return block;
    }

    public static Block registerBlock(String path, Block block, Rarity rarity, boolean fireproof) {
        if (fireproof) {
            Registry.register(Registries.BLOCK, id(path), block);
            Registry.register(Registries.ITEM, id(path), new BlockItem(block, new FabricItemSettings().rarity(rarity).fireproof()));
        } else {
            registerBlock(path, block, rarity);
        }
        return block;
    }

    public static EntityModelLayer registerModel(String name, String layer) {
        var result = new EntityModelLayer(id(name), layer);
        AccessorEntityModelLayers.getAllModels().add(result);
        return result;
    }

    public static EntityModelLayer registerModel(String name) {
        return registerModel(name, "main");
    }

    public static EntityType<?> registerEntityType(String path, EntityType<?> type){
        Registry.register(Registries.ENTITY_TYPE, RegistryHelper.id(path), type);
        return type;
    }
}
