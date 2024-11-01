package org.irmc.industrialrevival.api.objects.events.vanilla;

import lombok.Getter;
import org.bukkit.event.block.BlockBreakEvent;
import org.irmc.industrialrevival.api.items.IndustrialRevivalItem;
import org.irmc.industrialrevival.api.objects.events.interfaces.RelatedIRItem;

@Getter
public class IRBlockBreakEvent extends BlockBreakEvent implements RelatedIRItem {
    private final BlockBreakEvent originalEvent;
    private final IndustrialRevivalItem iritem;
    public IRBlockBreakEvent(BlockBreakEvent originalEvent, IndustrialRevivalItem iritem) {
        super(originalEvent.getBlock(), originalEvent.getPlayer());
        this.originalEvent = originalEvent;
        this.iritem = iritem;
    }
}
