package org.irmc.industrialrevival.implementation.items;

import org.bukkit.NamespacedKey;
import org.irmc.industrialrevival.api.items.collection.ItemDictionary;
import org.irmc.industrialrevival.api.items.collection.UnchangeableItemDictionary;
import org.irmc.industrialrevival.implementation.IndustrialRevival;
import org.irmc.industrialrevival.implementation.items.IRItems;
import org.irmc.industrialrevival.utils.KeyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IRDictionaries {
    public static final ItemDictionary IR_ORE;
    public static final ItemDictionary IR_ORE_PRODUCTION;

    static {
        IR_ORE = new UnchangeableItemDictionary(
                KeyUtil.customKey("dict_ore"),
                List.of(
                        IRItems.ALUMINIUM_ORE,
                        IRItems.CHROMIUM_ORE,
                        IRItems.LEAD_ORE,
                        IRItems.COBALT_ORE,
                        IRItems.MAGNESIUM_ORE,
                        IRItems.MAGNET_ORE,
                        IRItems.URANIUM_ORE,
                        IRItems.TIN_ORE,
                        IRItems.MERCURY_ORE,
                        IRItems.SILVER_ORE,
                        IRItems.NICKEL_ORE,
                        IRItems.TUNGSTEN_ORE,
                        IRItems.ZINC_ORE));

        IR_ORE_PRODUCTION = new UnchangeableItemDictionary(KeyUtil.customKey("dict_ore_production"), List.of());
    }

    public static ItemDictionary getDictionary(NamespacedKey key) {
        return IndustrialRevival.getInstance().getRegistry().getDictionaries().get(key);
    }
}
