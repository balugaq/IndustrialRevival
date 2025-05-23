package org.irmc.industrialrevival.api.objects.events.ir;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.irmc.industrialrevival.api.objects.IRBlockData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TickStartEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Map<Location, IRBlockData> blockDataMap;
    private final int checkInterval;
    private final long ticked;

    public TickStartEvent(Map<Location, IRBlockData> blockDataMap, int checkInterval, long ticked) {
        super(true);
        this.blockDataMap = new HashMap<>(blockDataMap);
        this.checkInterval = checkInterval;
        this.ticked = ticked;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
