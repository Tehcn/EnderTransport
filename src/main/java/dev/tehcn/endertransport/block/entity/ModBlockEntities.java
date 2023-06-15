package dev.tehcn.endertransport.block.entity;

import dev.tehcn.endertransport.EnderTransport;
import dev.tehcn.endertransport.block.ModBlocks;
import dev.tehcn.endertransport.util.registry.RegistryHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<AlloyFurnaceBlockEntity> ALLOY_FURNACE;

    public static void registerBlockEntities() {
        EnderTransport.LOGGER.info("Registering block entities for " + EnderTransport.MOD_ID);
        ALLOY_FURNACE = Registry.register(Registries.BLOCK_ENTITY_TYPE, RegistryHelper.id("alloy_furnace"),
                FabricBlockEntityTypeBuilder.create(AlloyFurnaceBlockEntity::new, ModBlocks.ALLOY_FURNACE).build(null));
    }
}
