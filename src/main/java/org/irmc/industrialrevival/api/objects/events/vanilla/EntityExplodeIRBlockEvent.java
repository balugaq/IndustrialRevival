package org.irmc.industrialrevival.api.objects.events.vanilla;

import lombok.Getter;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.irmc.industrialrevival.api.items.IndustrialRevivalItem;
import org.irmc.industrialrevival.api.objects.events.interfaces.RelatedIRItem;

@Getter
public class EntityExplodeIRBlockEvent extends EntityExplodeEvent implements RelatedIRItem {
    private final EntityExplodeEvent originalEvent;
    private final IndustrialRevivalItem iritem;

    public EntityExplodeIRBlockEvent(EntityExplodeEvent originalEvent, IndustrialRevivalItem iritem) {
        super(originalEvent.getEntity(), originalEvent.getLocation(), originalEvent.blockList(), originalEvent.getYield());
        this.originalEvent = originalEvent;
        this.iritem = iritem;
    }
}
