package org.mob.replication_addon_tiers;

import com.buuz135.replication.block.tile.ReplicationMachine;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.block_network.NetworkManager;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.mob.replication_addon_tiers.block.custom.AdvancedMatterPipeBlockEntity;
import org.mob.replication_addon_tiers.block.custom.EliteMatterPipeBlockEntity;
import org.mob.replication_addon_tiers.block.custom.UltimateMatterPipeBlockEntity;
import org.mob.replication_addon_tiers.client.ClientEvents;
import org.mob.replication_addon_tiers.registry.ModRegistry;
import org.slf4j.Logger;

import static net.neoforged.fml.loading.FMLEnvironment.dist;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ReplicationAddonTiers.MOD_ID)
public class ReplicationAddonTiers {

    public static final String MOD_ID = "replication_addon_tiers";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.replication_addon_tiers")).icon(() -> new ItemStack(ModRegistry.MATTER_TANK_TIER_1.get())).displayItems((parameters, output) -> {
        output.accept(ModRegistry.MEMORY_CHIP_TIER_1.get());
        output.accept(ModRegistry.MEMORY_CHIP_TIER_2.get());
        output.accept(ModRegistry.MEMORY_CHIP_TIER_3.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_1.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_2.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_3.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_4.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_5.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_6.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_7.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_8.get());
        output.accept(ModRegistry.ADVANCED_REPLICATOR.get());
        output.accept(ModRegistry.ELITE_REPLICATOR.get());
        output.accept(ModRegistry.ADVANCED_DISINTEGRATOR.get());
        output.accept(ModRegistry.ELITE_DISINTEGRATOR.get());
        output.accept(ModRegistry.ADVANCED_MATTER_NETWORK_PIPE.get());
        output.accept(ModRegistry.ELITE_MATTER_NETWORK_PIPE.get());
        output.accept(ModRegistry.ULTIMATE_MATTER_NETWORK_PIPE.get());


    }).build());


    public ReplicationAddonTiers(IEventBus modEventBus, ModContainer modContainer) {

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        ModRegistry.BLOCK_ENTITIES.register(modEventBus);

        modEventBus.addListener(this::registerCapabilities);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        CommonEvents.init();

        EventManager.mod(RegisterCapabilitiesEvent.class).process((event) -> {
            event.registerBlock(Capabilities.EnergyStorage.BLOCK, (level, blockPos, blockState, blockEntity, direction) -> {
                if (level instanceof ServerLevel && blockEntity instanceof AdvancedMatterPipeBlockEntity pipe && NetworkManager.get(level) != null
                        && NetworkManager.get(level).getElement(blockPos) != null && pipe.getNetwork() != null && pipe.getNetwork().getEnergyStorage() != null) {
                    return pipe.getNetwork().getEnergyStorage();
                }
                return null;
            }, ModRegistry.ADVANCED_MATTER_NETWORK_PIPE.get());

            event.registerBlock(Capabilities.EnergyStorage.BLOCK, (level, blockPos, blockState, blockEntity, direction) -> {
                if (level instanceof ServerLevel && blockEntity instanceof EliteMatterPipeBlockEntity pipe && NetworkManager.get(level) != null
                        && NetworkManager.get(level).getElement(blockPos) != null && pipe.getNetwork() != null && pipe.getNetwork().getEnergyStorage() != null) {
                    return pipe.getNetwork().getEnergyStorage();
                }
                return null;
            }, ModRegistry.ELITE_MATTER_NETWORK_PIPE.get());

            event.registerBlock(Capabilities.EnergyStorage.BLOCK, (level, blockPos, blockState, blockEntity, direction) -> {
                if (level instanceof ServerLevel && blockEntity instanceof UltimateMatterPipeBlockEntity pipe && NetworkManager.get(level) != null
                        && NetworkManager.get(level).getElement(blockPos) != null && pipe.getNetwork() != null && pipe.getNetwork().getEnergyStorage() != null) {
                    return pipe.getNetwork().getEnergyStorage();
                }
                return null;
            }, ModRegistry.ULTIMATE_MATTER_NETWORK_PIPE.get());

            event.registerBlock(Capabilities.EnergyStorage.BLOCK, (level, blockPos, blockState, blockEntity, direction) -> {
                Block patt0$temp = blockState.getBlock();
                if (patt0$temp instanceof INetworkDirectionalConnection connection) {
                    if (connection.canConnect(level, blockPos, blockState, direction) && blockEntity instanceof ReplicationMachine<?> machine) {
                        return machine.getEnergyStorage();
                    }
                }

                return null;
            }, ModRegistry.ADVANCED_REPLICATOR.get(),ModRegistry.ELITE_REPLICATOR.get(),ModRegistry.ADVANCED_DISINTEGRATOR.get(),ModRegistry.ELITE_DISINTEGRATOR.get());
        }).subscribe();


        CommonEvents.init();
        if (dist == Dist.CLIENT) {
            ClientEvents.init();
        }
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_1_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_1_BE.get(),
                (object, context) -> object.getFluidHandler(context)
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_2_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_2_BE.get(),
                (object, context) -> object.getFluidHandler(context)
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_3_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_3_BE.get(),
                (object, context) -> object.getFluidHandler(context)
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_4_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_4_BE.get(),
                (object, context) -> object.getFluidHandler(context)
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_5_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_5_BE.get(),
                (object, context) -> object.getFluidHandler(context)
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_6_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_6_BE.get(),
                (object, context) -> object.getFluidHandler(context)
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_7_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_7_BE.get(),
                (object, context) -> object.getFluidHandler(context)
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_8_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, ModRegistry.MATTER_TANK_TIER_8_BE.get(),
                (object, context) -> object.getFluidHandler(context)
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.ADVANCED_DISINTEGRATOR_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.ELITE_DISINTEGRATOR_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.ADVANCED_REPLICATOR_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, ModRegistry.ELITE_REPLICATOR_BE.get(),
                (object, context) -> object.getItemHandler(context)
        );

    }
}
