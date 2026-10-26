package org.mob.replication_addon_tiers.block.custom.matter_tank;

import com.buuz135.replication.ReplicationConfig;
import org.mob.replication_addon_tiers.ModConfig;

public enum TankTier {
    TIER_1("matter_tank_tier_1", "Matter Tank Tier 1", ModConfig.MatterTank.MATTER_TANK_TIER_1 * ReplicationConfig.MatterTank.CAPACITY, false),
    TIER_2("matter_tank_tier_2", "Matter Tank Tier 2", ModConfig.MatterTank.MATTER_TANK_TIER_2 * ReplicationConfig.MatterTank.CAPACITY, false),
    TIER_3("matter_tank_tier_3", "Matter Tank Tier 3", ModConfig.MatterTank.MATTER_TANK_TIER_3 * ReplicationConfig.MatterTank.CAPACITY, false),
    TIER_4("matter_tank_tier_4", "Matter Tank Tier 4", ModConfig.MatterTank.MATTER_TANK_TIER_4 * ReplicationConfig.MatterTank.CAPACITY, false),
    TIER_5("matter_tank_tier_5", "Matter Tank Tier 5", ModConfig.MatterTank.MATTER_TANK_TIER_5 * ReplicationConfig.MatterTank.CAPACITY, false),
    TIER_6("matter_tank_tier_6", "Matter Tank Tier 6", ModConfig.MatterTank.MATTER_TANK_TIER_6 * ReplicationConfig.MatterTank.CAPACITY, false),
    TIER_7("matter_tank_tier_7", "Matter Tank Tier 7", ModConfig.MatterTank.MATTER_TANK_TIER_7 * ReplicationConfig.MatterTank.CAPACITY, false),
    TIER_8("matter_tank_tier_8", "Matter Tank Tier 8", ModConfig.MatterTank.MATTER_TANK_TIER_8 * ReplicationConfig.MatterTank.CAPACITY, false);

    private final String registryId;
    private final String displayName;
    private final int capacity;
    private final boolean isCreative;

    TankTier(String registryId, String displayName, int capacity, boolean isCreative) {
        this.registryId = registryId;
        this.displayName = displayName;
        this.capacity = capacity;
        this.isCreative = isCreative;
    }

    public String getRegistryId() { return registryId; }
    public String getDisplayName() { return displayName; }
    public int getCapacity() { return capacity; }
    public boolean isCreative() { return isCreative; }
}