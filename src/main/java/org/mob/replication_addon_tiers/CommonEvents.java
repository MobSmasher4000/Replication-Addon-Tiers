package org.mob.replication_addon_tiers;

import com.hrznstudio.titanium.nbthandler.NBTManager;
import org.mob.replication_addon_tiers.block.custom.*;

public class CommonEvents {
    public static void init(){
        NBTManager.getInstance().scanTileClassForAnnotations(AdvancedReplicatorBlockEntity.class);
        NBTManager.getInstance().scanTileClassForAnnotations(EliteReplicatorBlockEntity.class);
        NBTManager.getInstance().scanTileClassForAnnotations(AdvancedDisintegratorBlockEntity.class);
        NBTManager.getInstance().scanTileClassForAnnotations(EliteDisintegratorBlockEntity.class);
        NBTManager.getInstance().scanTileClassForAnnotations(AdvancedMatterPipeBlockEntity.class);
        NBTManager.getInstance().scanTileClassForAnnotations(EliteMatterPipeBlockEntity.class);
        NBTManager.getInstance().scanTileClassForAnnotations(UltimateMatterPipeBlockEntity.class);
        NBTManager.getInstance().scanTileClassForAnnotations(PatternTransferUnitBlockEntity.class);
    }
}
