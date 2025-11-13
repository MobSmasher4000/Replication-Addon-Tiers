package org.mob.replication_addon_tiers.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.mob.replication_addon_tiers.ReplicationAddonTiers;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ReplicationAddonTiers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
