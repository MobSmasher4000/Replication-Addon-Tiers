package org.mob.replication_addon_tiers.compat;

import com.buuz135.replication.Replication;
import com.buuz135.replication.ReplicationAttachments;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.mob.replication_addon_tiers.registry.ModRegistry;

import java.util.List;

@JeiPlugin
public class JeiCompat implements IModPlugin {
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        IModPlugin.super.registerItemSubtypes(registration);
        registration.registerSubtypeInterpreter(ModRegistry.MATTER_TANK_TIER_1.asItem(), new ISubtypeInterpreter<ItemStack>() {
            public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
                return List.of((CompoundTag)ingredient.getOrDefault(ReplicationAttachments.TILE, new CompoundTag()));
            }

            public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
                return ((CompoundTag)ingredient.getOrDefault(ReplicationAttachments.TILE, new CompoundTag())).toString();
            }
        });

        registration.registerSubtypeInterpreter(ModRegistry.MATTER_TANK_TIER_2.asItem(), new ISubtypeInterpreter<ItemStack>() {
            public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
                return List.of((CompoundTag)ingredient.getOrDefault(ReplicationAttachments.TILE, new CompoundTag()));
            }

            public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
                return ((CompoundTag)ingredient.getOrDefault(ReplicationAttachments.TILE, new CompoundTag())).toString();
            }
        });

        registration.registerSubtypeInterpreter(ModRegistry.MATTER_TANK_TIER_3.asItem(), new ISubtypeInterpreter<ItemStack>() {
            public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
                return List.of((CompoundTag)ingredient.getOrDefault(ReplicationAttachments.TILE, new CompoundTag()));
            }

            public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
                return ((CompoundTag)ingredient.getOrDefault(ReplicationAttachments.TILE, new CompoundTag())).toString();
            }
        });

        registration.registerSubtypeInterpreter(ModRegistry.MATTER_TANK_TIER_4.asItem(), new ISubtypeInterpreter<ItemStack>() {
            public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
                return List.of((CompoundTag)ingredient.getOrDefault(ReplicationAttachments.TILE, new CompoundTag()));
            }

            public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
                return ((CompoundTag)ingredient.getOrDefault(ReplicationAttachments.TILE, new CompoundTag())).toString();
            }
        });


    }

    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Replication.MOD_ID, "replication");
    }

    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
    }
}
