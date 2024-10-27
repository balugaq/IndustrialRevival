package org.irmc.industrialrevival.core.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.irmc.industrialrevival.api.items.IndustrialRevivalItem;
import org.irmc.industrialrevival.api.items.attributes.ItemDroppable;
import org.irmc.industrialrevival.api.items.handlers.BlockBreakHandler;
import org.irmc.industrialrevival.api.items.handlers.BlockExplodeHandler;
import org.irmc.industrialrevival.api.items.handlers.BlockFromToHandler;
import org.irmc.industrialrevival.api.items.handlers.BlockPistonExtendHandler;
import org.irmc.industrialrevival.api.items.handlers.BlockPistonRetractHandler;
import org.irmc.industrialrevival.api.items.handlers.BlockPlaceHandler;
import org.irmc.industrialrevival.api.items.handlers.EndermanMoveBlockHandler;
import org.irmc.industrialrevival.api.items.handlers.EntityChangeBlockHandler;
import org.irmc.industrialrevival.api.items.handlers.EntityExplodeHandler;
import org.irmc.industrialrevival.api.items.handlers.ItemHandler;
import org.irmc.industrialrevival.api.objects.IRBlockData;
import org.irmc.industrialrevival.api.objects.events.vanilla.BlockExplodeIRBlockEvent;
import org.irmc.industrialrevival.api.objects.events.vanilla.EndermanMoveIRBlockEvent;
import org.irmc.industrialrevival.api.objects.events.vanilla.EntityChangeIRBlockEvent;
import org.irmc.industrialrevival.api.objects.events.vanilla.EntityExplodeIRBlockEvent;
import org.irmc.industrialrevival.api.objects.events.vanilla.EntityPickupIRItemEvent;
import org.irmc.industrialrevival.api.objects.events.vanilla.IRBlockBreakEvent;
import org.irmc.industrialrevival.api.objects.events.vanilla.IRBlockFromToEvent;
import org.irmc.industrialrevival.api.objects.events.vanilla.IRBlockPlaceEvent;
import org.irmc.industrialrevival.api.objects.events.vanilla.PistonExtendIRBlockEvent;
import org.irmc.industrialrevival.api.objects.events.vanilla.PistonRetractIRBlockEvent;
import org.irmc.industrialrevival.implementation.IndustrialRevival;
import org.jetbrains.annotations.Nullable;

/**
 * This class is used to call {@link ItemHandler}
 *
 * @see EventCreator
 * @see DefaultHandler
 * @author balugaq
 */
public class HandlerCaller implements Listener {
    // todo: add more event handlers
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockExplodeIRBlock(BlockExplodeIRBlockEvent e) {
        IndustrialRevivalItem iritem = e.getIritem();
        if (!checkValid(iritem)) {
            return;
        }

        BlockExplodeHandler handler = iritem.getItemHandler(BlockExplodeHandler.class);
        if (handler != null) {
            handler.onBlockExplode(e);
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEndermanMoveIRBlockEvent(EndermanMoveIRBlockEvent e) {
        IndustrialRevivalItem iritem = e.getIritem();
        if (!checkValid(iritem)) {
            return;
        }

        EndermanMoveBlockHandler handler = iritem.getItemHandler(EndermanMoveBlockHandler.class);
        if (handler != null) {
            handler.onEndermanMoveBlock(e);
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityChangeBlock(EntityChangeIRBlockEvent e) {
        IndustrialRevivalItem iritem = e.getIritem();
        if (!checkValid(iritem)) {
            return;
        }

        EntityChangeBlockHandler handler = iritem.getItemHandler(EntityChangeBlockHandler.class);
        if (handler != null) {
            handler.onEntityChangeBlock(e);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplodeIRBlock(EntityExplodeIRBlockEvent e) {
        IndustrialRevivalItem iritem = e.getIritem();
        if (!checkValid(iritem)) {
            return;
        }

        EntityExplodeHandler handler = iritem.getItemHandler(EntityExplodeHandler.class);
        if (handler != null) {
            handler.onEntityExplode(e);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityPickupIRItem(EntityPickupIRItemEvent e) {
        IndustrialRevivalItem iritem = e.getIritem();
        if (!checkValid(iritem)) {
            return;
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onIRBlockBreak(IRBlockBreakEvent e) {
        IndustrialRevivalItem iritem = e.getIritem();
        if (!checkValid(iritem)) {
            return;
        }

        BlockBreakHandler handler = iritem.getItemHandler(BlockBreakHandler.class);
        if (handler != null) {
            handler.onBlockBreak(e);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onIRBlockFromTo(IRBlockFromToEvent e) {
        IndustrialRevivalItem iritem = e.getIritem();
        if (!checkValid(iritem)) {
            return;
        }

        BlockFromToHandler handler = iritem.getItemHandler(BlockFromToHandler.class);
        if (handler != null) {
            handler.onBlockFromTo(e);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onIRBlockPlace(IRBlockPlaceEvent event) {
        IndustrialRevivalItem iritem = event.getIritem();
        if (!checkValid(iritem, event.getBlock().getWorld())) {
            return;
        }

        BlockPlaceHandler handler = iritem.getItemHandler(BlockPlaceHandler.class);

        if (handler != null) {
            handler.onBlockPlace(event);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPistonExtendIRBlock(PistonExtendIRBlockEvent event) {
        BlockPistonExtendHandler handler = event.getIritem().getItemHandler(BlockPistonExtendHandler.class);
        if (handler != null) {
            boolean pass = handler.onPistonExtend(event);
            if (!pass) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPistonRetractIRBlock(PistonRetractIRBlockEvent event) {
        BlockPistonRetractHandler handler = event.getIritem().getItemHandler(BlockPistonRetractHandler.class);
        if (handler != null) {
            boolean pass = handler.onPistonRetract(event);
            if (!pass) {
                event.setCancelled(true);
            }
        }
    }

    private boolean checkValid(@Nullable IndustrialRevivalItem iritem) {
        return iritem != null && !iritem.isDisabled();
    }

    private boolean checkValid(@Nullable IndustrialRevivalItem iritem, World world) {
        return iritem != null && !iritem.isDisabledInWorld(world);
    }

    private boolean checkValid(@Nullable IndustrialRevivalItem iritem, Block block) {
        return iritem != null && !iritem.isDisabledInWorld(block.getWorld());
    }

    private boolean checkValid(@Nullable IndustrialRevivalItem iritem, Location location) {
        return iritem != null && !iritem.isDisabledInWorld(location.getWorld());
    }
}
