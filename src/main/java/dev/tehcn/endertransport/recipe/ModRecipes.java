package dev.tehcn.endertransport.recipe;

import dev.tehcn.endertransport.EnderTransport;
import dev.tehcn.endertransport.util.registry.RegistryHelper;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
    public static void registerRecipes() {
        EnderTransport.LOGGER.info("Registering recipes for " + EnderTransport.MOD_ID);

        Registry.register(Registries.RECIPE_SERIALIZER, RegistryHelper.id(AlloyingRecipe.Serializer.ID),
                AlloyingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, RegistryHelper.id(AlloyingRecipe.Type.ID),
                AlloyingRecipe.Type.INSTANCE);
    }
}
