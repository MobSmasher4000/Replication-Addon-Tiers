package org.mob.replication_addon_tiers;

import com.buuz135.replication.block.tile.ReplicationMachine;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.block_network.NetworkManager;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.tab.TitaniumTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.mob.replication_addon_tiers.block.entity.matter_network_pipe.AdvancedMatterPipeBlockEntity;
import org.mob.replication_addon_tiers.block.entity.matter_network_pipe.EliteMatterPipeBlockEntity;
import org.mob.replication_addon_tiers.block.entity.matter_network_pipe.UltimateMatterPipeBlockEntity;
import org.mob.replication_addon_tiers.datagen.ReplicationAddonTiersBlockTagsProvider;
import org.mob.replication_addon_tiers.datagen.ReplicationAddonTiersLootTableDataProvider;
import org.mob.replication_addon_tiers.datagen.ReplicationAddonTiersRecipesProvider;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.List;

@Mod(ReplicationAddonTiers.MOD_ID)
public class ReplicationAddonTiers extends ModuleController {
    public static final String MOD_ID = "replication_addon_tiers";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static TitaniumTab TAB = new TitaniumTab(Identifier.fromNamespaceAndPath(MOD_ID, "main"));

    public ReplicationAddonTiers(Dist dist,IEventBus modEventBus, ModContainer modContainer) {
        super(modContainer);
        CommonEvents.init();
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        EventManager.mod(RegisterCapabilitiesEvent.class).process(event -> {
            event.registerBlock(Capabilities.Energy.BLOCK, (level, blockPos, blockState, blockEntity, direction) -> {
                if (level instanceof ServerLevel && blockEntity instanceof AdvancedMatterPipeBlockEntity pipe && NetworkManager.get(level) != null
                        && NetworkManager.get(level).getElement(blockPos) != null && pipe.getNetwork() != null && pipe.getNetwork().getEnergyStorage() != null) {
                    return pipe.getNetwork().getEnergyStorage();
                }
                return null;
            }, ReplicationAddonTiersRegistry.Blocks.ADVANCED_MATTER_NETWORK_PIPE.getBlock());

            event.registerBlock(Capabilities.Energy.BLOCK, (level, blockPos, blockState, blockEntity, direction) -> {
                if (level instanceof ServerLevel && blockEntity instanceof EliteMatterPipeBlockEntity pipe && NetworkManager.get(level) != null
                        && NetworkManager.get(level).getElement(blockPos) != null && pipe.getNetwork() != null && pipe.getNetwork().getEnergyStorage() != null) {
                    return pipe.getNetwork().getEnergyStorage();
                }
                return null;
            }, ReplicationAddonTiersRegistry.Blocks.ELITE_MATTER_NETWORK_PIPE.getBlock());

            event.registerBlock(Capabilities.Energy.BLOCK, (level, blockPos, blockState, blockEntity, direction) -> {
                if (level instanceof ServerLevel && blockEntity instanceof UltimateMatterPipeBlockEntity pipe && NetworkManager.get(level) != null
                        && NetworkManager.get(level).getElement(blockPos) != null && pipe.getNetwork() != null && pipe.getNetwork().getEnergyStorage() != null) {
                    return pipe.getNetwork().getEnergyStorage();
                }
                return null;
            }, ReplicationAddonTiersRegistry.Blocks.ULTIMATE_MATTER_NETWORK_PIPE.getBlock());

            event.registerBlock(Capabilities.Energy.BLOCK, (level, blockPos, blockState, blockEntity, direction) -> {
                if (blockState.getBlock() instanceof INetworkDirectionalConnection connection && connection.canConnect(level, blockPos, blockState, direction) && blockEntity instanceof ReplicationMachine<?> machine) {
                    return machine.getEnergyStorage();
                }
                return null;
            }, ReplicationAddonTiersRegistry.Blocks.ADVANCED_REPLICATOR.getBlock(), ReplicationAddonTiersRegistry.Blocks.ELITE_REPLICATOR.getBlock(),
                    ReplicationAddonTiersRegistry.Blocks.ADVANCED_DISINTEGRATOR.getBlock(), ReplicationAddonTiersRegistry.Blocks.ELITE_DISINTEGRATOR.getBlock(),
                    ReplicationAddonTiersRegistry.Blocks.ADVANCED_MATTER_NETWORK_PIPE.getBlock(), ReplicationAddonTiersRegistry.Blocks.ELITE_MATTER_NETWORK_PIPE.getBlock(), ReplicationAddonTiersRegistry.Blocks.ULTIMATE_MATTER_NETWORK_PIPE.getBlock());
        }).subscribe();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Override
    protected void initModules() {
        this.addCreativeTab("main",()-> new ItemStack(ReplicationAddonTiersRegistry.Items.MEMORY_CHIP_TIER_1.get()), "replication_addon_tiers", TAB);
        ReplicationAddonTiersRegistry.init(this.getRegistries());
    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        super.addDataProvider(event);
        List<Block> blocks = BuiltInRegistries.BLOCK.stream().filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(MOD_ID)).toList();
        event.addProvider(new ReplicationAddonTiersLootTableDataProvider(event.getGenerator(), () -> blocks, event.getLookupProvider()));
        event.addProvider(new ReplicationAddonTiersBlockTagsProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), MOD_ID, blocks));
        event.addProvider(new ReplicationAddonTiersRecipesProvider.Runner(event.getGenerator(), event.getLookupProvider()));
    }
}
