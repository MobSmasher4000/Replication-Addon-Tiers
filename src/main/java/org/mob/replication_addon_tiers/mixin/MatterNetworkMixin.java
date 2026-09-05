package org.mob.replication_addon_tiers.mixin;

import com.buuz135.replication.network.MatterNetwork;
import com.hrznstudio.titanium.block_network.element.NetworkElement;
import net.minecraft.world.level.Level;
import org.mob.replication_addon_tiers.block.custom.AdvancedReplicatorBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = MatterNetwork.class, remap = false)
public class MatterNetworkMixin {

    @Shadow private List<NetworkElement> queueNetworkElements;
    @Shadow private List<NetworkElement> replicators;

    //inject right before queueNetworkElements.clear() is called
    @Inject(method = "update", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", shift = At.Shift.BEFORE))
    private void addAdvancedReplicatorsToNetwork(Level level, CallbackInfo ci) {
        for (NetworkElement element : this.queueNetworkElements) {
            if (!element.getLevel().isLoaded(element.getPos())) continue;

            var tile = element.getLevel().getBlockEntity(element.getPos());

            if (tile instanceof AdvancedReplicatorBlockEntity) {
                this.replicators.add(element);
            }
        }
    }
}