package org.irmc.industrialrevival.api.objects.events.vanilla;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.irmc.industrialrevival.api.items.IndustrialRevivalItem;
import org.irmc.industrialrevival.api.objects.events.interfaces.RelatedIRItem;

@Getter
public class IRItemKillEntityEvent extends EntityDeathEvent implements RelatedIRItem {
    private final IndustrialRevivalItem iritem;
    private final EntityDeathEvent originalEvent;
    private final Player killer;
    public IRItemKillEntityEvent(EntityDeathEvent originalEvent, IndustrialRevivalItem iritem) {
        super(originalEvent.getEntity(), originalEvent.getDrops(), originalEvent.getDroppedExp());
        this.iritem = iritem;
        this.originalEvent = originalEvent;
        this.killer = originalEvent.getEntity().getKiller();
    }
}
