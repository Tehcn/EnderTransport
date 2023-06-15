package dev.tehcn.endertransport.armor;

import dev.tehcn.endertransport.util.registry.RegistryHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModArmorSet {
    private final ArmorItem helmet;
    private final ArmorItem chestplate;
    private final ArmorItem leggings;
    private final ArmorItem boots;

    private final List<Item> armorSet;

    public ArmorItem baseArmorItem(ArmorMaterial material, ArmorItem.Type type, Consumer<Item.Settings> settingsConsumer) {
        final FabricItemSettings settings = new FabricItemSettings();
        settingsConsumer.accept(settings);
        return this.makeItem(material, type, settings);
    }

    public ModArmorSet(ArmorMaterial material) {
        this(material, settings -> { });
    }

    public ModArmorSet(ArmorMaterial material, Consumer<Item.Settings> settingsConsumer) {
        this.helmet = baseArmorItem(material, ArmorItem.Type.HELMET, settingsConsumer);
        this.chestplate = baseArmorItem(material, ArmorItem.Type.CHESTPLATE, settingsConsumer);
        this.leggings = baseArmorItem(material, ArmorItem.Type.LEGGINGS, settingsConsumer);
        this.boots = baseArmorItem(material, ArmorItem.Type.BOOTS, settingsConsumer);
        this.armorSet = List.of(helmet, chestplate, leggings, boots);
    }

    public void register(String name, List<RegistryKey<ItemGroup>> groups) {
        ArrayList<Item> items = new ArrayList<>();
        items.add(RegistryHelper.registerItem(name + "_helmet", helmet));
        items.add(RegistryHelper.registerItem(name + "_chestplate", chestplate));
        items.add(RegistryHelper.registerItem(name + "_leggings", leggings));
        items.add(RegistryHelper.registerItem(name + "_boots", boots));
        groups.forEach(group -> ItemGroupEvents.modifyEntriesEvent(group)
                .register(entries -> items.forEach(entries::add)));
    }

    protected ArmorItem makeItem(ArmorMaterial material, ArmorItem.Type type, Item.Settings settings) {
        return new ArmorItem(material, type, settings);
    }

    public ArmorItem getHelmet() {
        return helmet;
    }

    public ArmorItem getChestplate() {
        return chestplate;
    }

    public ArmorItem getLeggings() {
        return leggings;
    }

    public ArmorItem getBoots() {
        return boots;
    }

    public List<Item> getArmorSet() {
        return armorSet;
    }

    public boolean isInArmorSet(ItemStack stack) {
        return this.getArmorSet().contains(stack.getItem());
    }
}
