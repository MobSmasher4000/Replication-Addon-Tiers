package org.mob.replication_addon_tiers;

import com.buuz135.replication.ReplicationAttachments;
import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.ReplicationRegistry;
import com.buuz135.replication.api.IMatterType;
import com.buuz135.replication.api.MatterType;
import com.buuz135.replication.api.matter_fluid.MatterStack;
import com.buuz135.replication.block.tile.MatterPipeBlockEntity;
import com.buuz135.replication.block.tile.ReplicationMachine;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.block_network.NetworkManager;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.mob.replication_addon_tiers.client.ClientEvents;
import org.mob.replication_addon_tiers.registry.ModRegistry;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ReplicationAddonTiers.MOD_ID)
public class ReplicationAddonTiers {

    public static final String MOD_ID = "replication_addon_tiers";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.replication_addon_tiers")).icon(() -> new ItemStack(ModRegistry.MATTER_TANK_TIER_1.get())).displayItems((parameters, output) -> {
        output.accept(ModRegistry.MATTER_TANK_TIER_1.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_2.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_3.get());
        output.accept(ModRegistry.MATTER_TANK_TIER_4.get());
        output.accept(ModRegistry.REPLICATOR_ADVANCED_BLOCK.get());

        for(IMatterType value : ReplicationRegistry.MATTER_TYPES_REGISTRY.stream().toList()) {
            if (!value.equals(MatterType.EMPTY)) {
                MatterStack matterStack = new MatterStack(value, (double) ReplicationConfig.MatterTank.CAPACITY * Config.tankTier1);
                CompoundTag tile = new CompoundTag();
                CompoundTag tank = matterStack.writeToNBT(new CompoundTag());
                tile.put("tank", tank);
                ItemStack item = new ItemStack(ModRegistry.MATTER_TANK_TIER_1.get());
                item.set(ReplicationAttachments.TILE, tile);
                output.accept(item);
            }
        }

        for(IMatterType value : ReplicationRegistry.MATTER_TYPES_REGISTRY.stream().toList()) {
            if (!value.equals(MatterType.EMPTY)) {
                MatterStack matterStack = new MatterStack(value, (double) ReplicationConfig.MatterTank.CAPACITY * Config.tankTier2);
                CompoundTag tile = new CompoundTag();
                CompoundTag tank = matterStack.writeToNBT(new CompoundTag());
                tile.put("tank", tank);
                ItemStack item = new ItemStack(ModRegistry.MATTER_TANK_TIER_2);
                item.set(ReplicationAttachments.TILE, tile);
                output.accept(item);
            }
        }

        for(IMatterType value : ReplicationRegistry.MATTER_TYPES_REGISTRY.stream().toList()) {
            if (!value.equals(MatterType.EMPTY)) {
                MatterStack matterStack = new MatterStack(value, (double) ReplicationConfig.MatterTank.CAPACITY * Config.tankTier3);
                CompoundTag tile = new CompoundTag();
                CompoundTag tank = matterStack.writeToNBT(new CompoundTag());
                tile.put("tank", tank);
                ItemStack item = new ItemStack(ModRegistry.MATTER_TANK_TIER_3);
                item.set(ReplicationAttachments.TILE, tile);
                output.accept(item);
            }
        }

        for(IMatterType value : ReplicationRegistry.MATTER_TYPES_REGISTRY.stream().toList()) {
            if (!value.equals(MatterType.EMPTY)) {
                MatterStack matterStack = new MatterStack(value, (double) ReplicationConfig.MatterTank.CAPACITY * Config.tankTier4);
                CompoundTag tile = new CompoundTag();
                CompoundTag tank = matterStack.writeToNBT(new CompoundTag());
                tile.put("tank", tank);
                ItemStack item = new ItemStack(ModRegistry.MATTER_TANK_TIER_4);
                item.set(ReplicationAttachments.TILE, tile);
                output.accept(item);
            }
        }


    }).build());


    public ReplicationAddonTiers(IEventBus modEventBus, ModContainer modContainer) {

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        ModRegistry.BLOCK_ENTITIES.register(modEventBus);


        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(ClientEvents::registerRenderers);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        EventManager.mod(RegisterCapabilitiesEvent.class).process((event) -> {
            event.registerBlock(Capabilities.EnergyStorage.BLOCK, (level, blockPos, blockState, blockEntity, direction) -> {
                Block patt0$temp = blockState.getBlock();
                if (patt0$temp instanceof INetworkDirectionalConnection connection) {
                    if (connection.canConnect(level, blockPos, blockState, direction) && blockEntity instanceof ReplicationMachine<?> machine) {
                        return machine.getEnergyStorage();
                    }
                }

                return null;
            }, new Block[]{ModRegistry.REPLICATOR_ADVANCED_BLOCK.get()});
        }).subscribe();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ClientEvents.init();
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


    }
}
