package org.mob.replication_addon_tiers.item.custom;

import com.buuz135.replication.item.MemoryChipItem;
import net.minecraft.world.item.ItemStack;

public class MemoryChipItemTier extends MemoryChipItem {
    private int SLOTS;
    public MemoryChipItemTier(int slots) {
        super();
        this.SLOTS = slots;
    }

    @Override
    public int getPatternSlots(ItemStack element) {
        return this.SLOTS;
    }
}