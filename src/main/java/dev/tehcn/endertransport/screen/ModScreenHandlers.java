package dev.tehcn.endertransport.screen;

import dev.tehcn.endertransport.EnderTransport;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<AlloyFurnaceScreenHandler> ALLOY_FURNACE_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
        EnderTransport.LOGGER.info("Registering screen handlers for " + EnderTransport.MOD_ID);
        ALLOY_FURNACE_SCREEN_HANDLER = new ScreenHandlerType<>(AlloyFurnaceScreenHandler::new, FeatureSet.empty());
    }
}
