package org.mob.replication_addon_tiers.block;

import java.util.List;

import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.util.FacingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.mob.replication_addon_tiers.block.custom.PatternTransferUnitBlockEntity;
import org.mob.replication_addon_tiers.registry.ModRegistry;

public class PatternTransferUnitBlock extends RotatableBlock<PatternTransferUnitBlockEntity> implements INetworkDirectionalConnection {

    public PatternTransferUnitBlock() {
        super("pattern_transfer_unit", Properties.ofFullCopy(Blocks.IRON_BLOCK), PatternTransferUnitBlockEntity.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return (pos, blockState) -> new PatternTransferUnitBlockEntity(this, ModRegistry.PATTERN_TRANSFER_UNIT_BE.get(), pos, blockState);
    }

    @NotNull
    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    // Energy (and matter network pipes) connect from the bottom and the back, like the Identification Chamber.
    @Override
    public boolean canConnect(Level level, BlockPos pos, BlockState state, Direction direction) {
        var sideness = FacingUtil.getFacingRelative(direction, state.getValue(FACING_HORIZONTAL));
        return sideness == FacingUtil.Sideness.BOTTOM || sideness == FacingUtil.Sideness.BACK;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.replication_addon_tiers.pattern_transfer_unit_modes"));

        tooltip.add(Component.translatable("gui.replication_addon_tiers.pattern_transfer_unit.mode.copy").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("gui.replication_addon_tiers.pattern_transfer_unit.mode.copy.tooltip")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.translatable("gui.replication_addon_tiers.pattern_transfer_unit.mode.move").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("gui.replication_addon_tiers.pattern_transfer_unit.mode.move.tooltip")
                .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
