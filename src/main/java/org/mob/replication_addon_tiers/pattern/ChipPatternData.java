package org.mob.replication_addon_tiers.pattern;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.buuz135.replication.ReplicationAttachments;
import com.buuz135.replication.api.pattern.IMatterPatternHolder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * Reads and writes the patterns of a memory chip directly from its "replication:chip_patterns" component.
 * Each pattern is stored as {Stack: <item>, Completion: <float>} under the keys "0", "1", ...
 */
public final class ChipPatternData {

    private ChipPatternData() {
    }

    // Non-empty patterns of the chip, in slot order.
    public static List<CompoundTag> read(ItemStack chip) {
        List<CompoundTag> result = new ArrayList<>();
        CompoundTag patterns = chip.get(ReplicationAttachments.CHIP_PATTERNS);
        if (patterns == null) return result;
        patterns.getAllKeys().stream()
                .sorted(Comparator.comparingInt(ChipPatternData::slotIndex))
                .forEach(key -> {
                    CompoundTag pattern = patterns.getCompound(key);
                    if (!key(pattern).isEmpty()) result.add(pattern.copy());
                });
        return result;
    }

    public static void write(ItemStack chip, List<CompoundTag> patterns) {
        CompoundTag tag = new CompoundTag();
        for (int i = 0; i < patterns.size(); i++) {
            tag.put(String.valueOf(i), patterns.get(i));
        }
        chip.set(ReplicationAttachments.CHIP_PATTERNS, tag);
    }

    // Identity of a pattern: its item and components, without the count. Empty for an empty pattern.
    public static CompoundTag key(CompoundTag pattern) {
        CompoundTag item = pattern.getCompound("Stack").copy();
        item.remove("count");
        return item;
    }

    public static float completion(CompoundTag pattern) {
        return pattern.getFloat("Completion");
    }

    @SuppressWarnings("unchecked")
    public static int slots(ItemStack chip) {
        if (chip.getItem() instanceof IMatterPatternHolder<?> holder) {
            return ((IMatterPatternHolder<ItemStack>) holder).getPatternSlots(chip);
        }
        return 0;
    }

    private static int slotIndex(String key) {
        try {
            return Integer.parseInt(key);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}
