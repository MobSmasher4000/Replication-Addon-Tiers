package org.mob.replication_addon_tiers.block;

import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.ReplicationRegistry;
import com.buuz135.replication.block.shapes.ReplicatorShapes;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.util.FacingUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.mob.replication_addon_tiers.Config;
import org.mob.replication_addon_tiers.block.custom.AdvancedReplicatorBlockEntity;
import org.mob.replication_addon_tiers.registry.ModRegistry;

import java.util.List;

public class AdvancedReplicatorBlock extends RotatableBlock<AdvancedReplicatorBlockEntity> implements INetworkDirectionalConnection {
    public static BooleanProperty HAS_ENCLOSURE = BooleanProperty.create("has_enclosure");
    public static BooleanProperty HAS_MOTOR = BooleanProperty.create("has_motor");

    public AdvancedReplicatorBlock() {
        super("advanced_replicator", Properties.ofFullCopy(Blocks.IRON_BLOCK), AdvancedReplicatorBlockEntity.class);
        registerDefaultState(defaultBlockState().setValue(HAS_ENCLOSURE, false).setValue(HAS_MOTOR, false));
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return (pos, blockState) -> new AdvancedReplicatorBlockEntity(this, ModRegistry.ADVANCED_REPLICATOR_BE.get(), pos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        super.createBlockStateDefinition(p_206840_1_);
        p_206840_1_.add(HAS_ENCLOSURE, HAS_MOTOR);
    }

    @NotNull
    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @NotNull
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext selectionContext) {
        var rotation = state.getValue(FACING_HORIZONTAL);
        if (rotation == Direction.NORTH){
            return ReplicatorShapes.NORTH_FULL;
        }
        if (rotation == Direction.SOUTH){
            return ReplicatorShapes.SOUTH_FULL;
        }
        if (rotation == Direction.EAST){
            return ReplicatorShapes.EAST_FULL;
        }
        if (rotation == Direction.WEST){
            return ReplicatorShapes.WEST_FULL;
        }
        return super.getCollisionShape(state, world, pos, selectionContext);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        var rotation = state.getValue(FACING_HORIZONTAL);
        if (rotation == Direction.NORTH){
            return ReplicatorShapes.NORTH_FULL;
        }
        if (rotation == Direction.SOUTH){
            return ReplicatorShapes.SOUTH_FULL;
        }
        if (rotation == Direction.EAST){
            return ReplicatorShapes.EAST_FULL;
        }
        if (rotation == Direction.WEST){
            return ReplicatorShapes.WEST_FULL;
        }
        return super.getShape(state, p_60556_, p_60557_, p_60558_);
    }

    public Pair<VoxelShape, VoxelShape> getShapePlate(BlockState state) {
        var rotation = state.getValue(FACING_HORIZONTAL);
        if (rotation == Direction.NORTH){
            return Pair.of(ReplicatorShapes.NORTH, ReplicatorShapes.NORTH_PLATE);
        }
        if (rotation == Direction.SOUTH){
            return Pair.of(ReplicatorShapes.SOUTH, ReplicatorShapes.SOUTH_PLATE);
        }
        if (rotation == Direction.EAST){
            return Pair.of(ReplicatorShapes.EAST, ReplicatorShapes.EAST_PLATE);
        }
        if (rotation == Direction.WEST){
            return Pair.of(ReplicatorShapes.WEST, ReplicatorShapes.WEST_PLATE);
        }
        return Pair.of(ReplicatorShapes.NORTH, ReplicatorShapes.NORTH_PLATE);
    }

    @Override
    public boolean canConnect(Level level, BlockPos pos, BlockState state, Direction direction) {
        var sideness = FacingUtil.getFacingRelative(direction, state.getValue(FACING_HORIZONTAL));
        if (direction == Direction.UP) return false;
        return sideness == FacingUtil.Sideness.BOTTOM || sideness == FacingUtil.Sideness.BACK;
    }

    @Override
    public NonNullList<ItemStack> getDynamicDrops(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        var list = super.getDynamicDrops(state, worldIn, pos, newState, isMoving);
        if (state.getValue(HAS_MOTOR)) {
            list.add(new ItemStack(ReplicationRegistry.Items.REPLICATOR_MOTOR));
        }
        if (state.getValue(HAS_ENCLOSURE)) {
            list.add(new ItemStack(ReplicationRegistry.Items.REPLICATOR_ENCLOSURE));
        }
        return list;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        String tip1 = String.valueOf(Component.translatable("tooltip.replication_addon_tiers.advanced_replicator_1"));
        String tip2 = String.valueOf(Component.translatable("tooltip.replication_addon_tiers.advanced_replicator_2"));
        String tip3 = String.valueOf(Component.translatable("tooltip.replication_addon_tiers.advanced_replicator_3"));
        tooltipComponents.add(Component.literal(tip1 + (Config.advancedReplicator * 2) + tip2 + (ReplicationConfig.Replicator.MAX_PROGRESS * 2) + tip3));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
