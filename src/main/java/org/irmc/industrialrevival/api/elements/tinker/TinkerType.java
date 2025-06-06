package org.irmc.industrialrevival.api.elements.tinker;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to define a material / product type in Smeltery.
 * Each type has a unique key, a level, meltable and output-able properties.
 *
 * @author balugaq
 * @since 1.0
 */
@Getter
public abstract class TinkerType {
    public static final Map<NamespacedKey, TinkerType> TYPES = new HashMap<>();
    private final @NotNull NamespacedKey key;
    private final int level;
    /* Define if the material can be melted in the smeltery */
    private final boolean meltable;
    /* Define if the material can be outputted from the smeltery */
    private final boolean outputAble;

    public TinkerType(@NotNull NamespacedKey key, int level, boolean meltable, boolean outputAble) {
        this.key = key;
        this.level = level;
        this.meltable = meltable;
        this.outputAble = outputAble;
        TYPES.put(key, this);
    }

    @Nullable
    public static TinkerType forName(NamespacedKey key) {
        return TYPES.get(key);
    }

    /**
     * Get the name of the type. (e.g. "pickaxe")
     *
     * @return the name of the type.
     */
    public @NotNull Component name() {
        // todo: add localization support
        return Component.text(key.getKey());
    }
}
