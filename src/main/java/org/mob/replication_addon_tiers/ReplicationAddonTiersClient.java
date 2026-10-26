package org.mob.replication_addon_tiers;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.mob.replication_addon_tiers.client.ClientEvents;

@Mod(value = ReplicationAddonTiers.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ReplicationAddonTiers.MOD_ID, value = Dist.CLIENT)
public class ReplicationAddonTiersClient {
    public ReplicationAddonTiersClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        ClientEvents.init();
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
    }
}
