package org.irmc.industrialrevival.api.objects.events.vanilla;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerLeftClickEvent extends PlayerInteractEvent {
    private final HandlerList handlers = new HandlerList();

    public PlayerLeftClickEvent(PlayerInteractEvent originalEvent) {
        super(
                originalEvent.getPlayer(),
                originalEvent.getAction(),
                originalEvent.getItem(),
                originalEvent.getClickedBlock(),
                originalEvent.getBlockFace(),
                originalEvent.getHand());
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
