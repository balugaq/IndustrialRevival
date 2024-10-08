package org.irmc.industrialrevival.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.irmc.industrialrevival.api.items.IndustrialRevivalItemStack;
import org.irmc.pigeonlib.items.ItemUtils;
import org.irmc.pigeonlib.pdc.PersistentDataAPI;

/**
 * This class serves guide itemstack instead of {@link IndustrialRevivalItemStack}
 */
@UtilityClass
public class CleanedItemGetter {
    public static final NamespacedKey CLEANED_ITEM_ID_KEY = KeyUtil.customKey("cleaned_item_id");

    public static ItemStack getCleanedItem(ItemStack item) {
        return ItemUtils.getCleanedItem(item, (meta) -> {
            if (item instanceof IndustrialRevivalItemStack iris) {
                PersistentDataAPI.setString(meta, CLEANED_ITEM_ID_KEY, iris.getId());
            }
        });
    }
}
