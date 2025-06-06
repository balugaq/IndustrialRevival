package org.irmc.industrialrevival.api.elements.compounds;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.NamespacedKey;
import org.irmc.industrialrevival.api.elements.ElementType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

/**
 * An element part to describe a chemical compound.
 *
 * @author balugaq
 * @see Chemical
 */
@Data
@AllArgsConstructor
@ParametersAreNonnullByDefault
public class Element implements Compound {
    static {
        COMPOUND_READERS.add(new CompoundReader.ElementReader());
    }

    private final @NotNull ElementType element;
    private final @NotNull NamespacedKey key;

    public Element(ElementType elementType) {
        this(elementType, new NamespacedKey(Element.ELEMENT_NAMESPACE, elementType.name().toLowerCase()));
    }

    public double getMolarMass() {
        return element.getRelativeAtomicMass();
    }

    public Map<ElementType, Double> toAtomic() {
        return Map.of(element, 1D);
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }

}
