package dev.tehcn.endertransport.armor;

import dev.tehcn.endertransport.EnderTransport;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import java.util.List;

public class ModArmorSets {
    public static final ModArmorSet STEEL = new ModArmorSet(ModArmorMaterials.STEEL, Item.Settings::fireproof);

    public static void registerArmorSets() {
        EnderTransport.LOGGER.info("Registering armor for " + EnderTransport.MOD_ID);
        STEEL.register("steel", List.of(ItemGroups.COMBAT));
    }
}
