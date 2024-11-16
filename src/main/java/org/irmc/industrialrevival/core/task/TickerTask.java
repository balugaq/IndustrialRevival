package org.irmc.industrialrevival.core.task;

import com.tcoded.folialib.wrapper.task.WrappedTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.irmc.industrialrevival.api.items.IndustrialRevivalItem;
import org.irmc.industrialrevival.api.items.handlers.BlockTicker;
import org.irmc.industrialrevival.api.objects.ChunkPosition;
import org.irmc.industrialrevival.api.objects.IRBlockData;
import org.irmc.industrialrevival.api.objects.events.ir.IRBlockTickEvent;
import org.irmc.industrialrevival.api.objects.events.ir.IRTickDoneEvent;
import org.irmc.industrialrevival.api.objects.events.ir.IRTickStartEvent;
import org.irmc.industrialrevival.core.data.object.BlockRecord;
import org.irmc.industrialrevival.implementation.IndustrialRevival;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TickerTask implements Consumer<WrappedTask> {
    private final Supplier<Map<Location, IRBlockData>> blockDataSupplier =
            IndustrialRevival.getInstance().getBlockDataService()::getBlockDataMap;
    public static final Map<Location, Integer> bugsCount = new ConcurrentHashMap<>();
    // TODO: When place or break a block, bugsCount should be reset to 0.
    @Getter
    private final int checkInterval;
    @Getter
    private long ticked = 0;

    public TickerTask(int checkInterval) {
        this.checkInterval = checkInterval;
    }
    @Override
    public void accept(WrappedTask wrappedTask) {
        Map<Location, IRBlockData> blockDataMap = blockDataSupplier.get();
        IRTickStartEvent e = new IRTickStartEvent(blockDataMap, checkInterval, ticked);
        Bukkit.getPluginManager().callEvent(e);

        if (blockDataMap == null) {
            return;
        }

        for (Map.Entry<Location, IRBlockData> entry : blockDataMap.entrySet()) {
            IRBlockData blockData = entry.getValue();

            IndustrialRevivalItem item = IndustrialRevivalItem.getById(blockData.getId());
            if (item == null) {
                removeTickingBlock(entry.getKey());
                continue;
            }

            BlockTicker ticker = item.getItemHandler(BlockTicker.class);
            if (ticker != null) {
                try {
                    IRBlockTickEvent event = new IRBlockTickEvent(entry.getKey().getBlock(), blockData.getMachineMenu(), item, blockData);
                    Bukkit.getPluginManager().callEvent(event);

                    IndustrialRevival.getInstance().getProfilerService().startProfiling(entry.getKey());
                    if (!event.isCancelled()) {
                        ticker.onTick(event);
                    }
                    IndustrialRevival.getInstance().getProfilerService().stopProfiling(entry.getKey());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    bugsCount.put(entry.getKey(), bugsCount.getOrDefault(entry.getKey(), 0) + 1);
                    if (bugsCount.get(entry.getKey()) >= 4) {
                        removeTickingBlock(entry.getKey());
                        reportBug(entry.getKey(), entry.getValue(), ex);
                    }
                }
            }
        }

        IRTickDoneEvent e2 = new IRTickDoneEvent();
        Bukkit.getPluginManager().callEvent(e2);

        ticked++;
        IndustrialRevival.getInstance().getProfilerService().clearProfilingData();
    }

    private void addTickingBlock(Location location, IRBlockData blockData) {
        Map<Location, IRBlockData> blockDataMap = blockDataSupplier.get();
        if (blockDataMap == null) {
            return;
        }

        blockDataMap.put(location, blockData);
    }

    private void removeTickingBlock(Location location) {
        Map<Location, IRBlockData> blockDataMap = blockDataSupplier.get();
        if (blockDataMap == null) {
            return;
        }

        blockDataMap.remove(location);
    }

    /**
     * Called when an existing chunk is loaded.
     * @param chunk The chunk that was loaded.
     */
    public void loadChunk(Chunk chunk) {
        // TODO: load chunk
        // get available ticking blocks from database
        ChunkPosition chunkPosition = new ChunkPosition(chunk);
        for (BlockRecord record : IndustrialRevival.getInstance().getDataManager().getAllBlockRecords()) {
            ChunkPosition recordChunkPosition = new ChunkPosition(record.getLocation().getChunk());
            if (recordChunkPosition.equals(chunkPosition)) {
                loadBlock(record.getLocation());
            }
        }
    }

    /**
     * Called when an existing block is loaded.
     * @param location The location of the block that was loaded.
     */
    public void loadBlock(Location location) {
        IRBlockData blockData = IndustrialRevival.getInstance().getBlockDataService().getBlockData(location);
        if (blockData == null) {
            return;
        }

        addTickingBlock(location, blockData);
    }

    private void reportBug(Location location, IRBlockData blockData, Exception e) {
        // todo
        String message = "An error caught while ticking a block at " + location + ":\n" + e.getMessage();
    }
}