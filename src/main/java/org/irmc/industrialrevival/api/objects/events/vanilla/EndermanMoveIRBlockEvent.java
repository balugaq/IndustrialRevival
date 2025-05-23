package org.irmc.industrialrevival.api.objects.events.vanilla;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.irmc.industrialrevival.api.items.IndustrialRevivalItem;
import org.irmc.industrialrevival.api.objects.events.interfaces.RelatedIRItem;
import org.jetbrains.annotations.NotNull;

@Getter
public class EndermanMoveIRBlockEvent extends EntityEvent implements RelatedIRItem {
    private static final HandlerList handlers = new HandlerList();
    private final EntityChangeBlockEvent originalEvent;
    private final Entity enderman;
    private final IndustrialRevivalItem iritem;

    public EndermanMoveIRBlockEvent(EntityChangeBlockEvent originalEvent, IndustrialRevivalItem iritem) {
        super(originalEvent.getEntity());
        if (originalEvent.getEntity().getType() == EntityType.ENDERMAN) {
            this.originalEvent = originalEvent;
            this.enderman = originalEvent.getEntity();
            this.iritem = iritem;
        } else {
            throw new IllegalArgumentException("Entity is not an enderman");
        }
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
