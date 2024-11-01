package org.irmc.industrialrevival.api.items.handlers;

import org.irmc.industrialrevival.api.objects.events.vanilla.PrepareAnvilIRItemEvent;
import org.irmc.industrialrevival.api.objects.events.vanilla.PrepareTradeSelectIRItemEvent;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface PrepareTradeSelectHandler extends ItemHandler {
    void onPrepareTradeSelect(@NotNull PrepareTradeSelectIRItemEvent event);
}
