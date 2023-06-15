package dev.tehcn.endertransport;

import dev.tehcn.endertransport.armor.ModArmorSets;
import dev.tehcn.endertransport.block.ModBlocks;
import dev.tehcn.endertransport.block.entity.ModBlockEntities;
import dev.tehcn.endertransport.item.ModItemGroup;
import dev.tehcn.endertransport.item.ModItems;
import dev.tehcn.endertransport.recipe.ModRecipes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnderTransport implements ModInitializer {
    public static final String MOD_ID = "endertransport";
    public static final Logger LOGGER = LoggerFactory.getLogger("endertransport");

    @Override
    public void onInitialize() {
        LOGGER.info("Thanks for using EnderTransport!");

        ModItems.registerModItems();
        ModItemGroup.addItemsToItemGroups();
        ModBlocks.registerModBlocks();
        ModArmorSets.registerArmorSets();
        ModBlockEntities.registerBlockEntities();
        ModRecipes.registerRecipes();
    }
}
