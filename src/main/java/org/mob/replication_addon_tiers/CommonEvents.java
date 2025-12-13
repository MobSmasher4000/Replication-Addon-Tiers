package org.mob.replication_addon_tiers;

import com.hrznstudio.titanium.nbthandler.NBTManager;
import org.mob.replication_addon_tiers.block.custom.AdvancedReplicatorBlockEntity;

public class CommonEvents {
    public static void init(){
        NBTManager.getInstance().scanTileClassForAnnotations(AdvancedReplicatorBlockEntity.class);
    }
}
