package org.mob.replication_addon_tiers.block;

import com.buuz135.replication.ReplicationRegistry;
import com.buuz135.replication.block.shapes.ReplicatorShapes;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.hrznstudio.titanium.util.FacingUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.mob.replication_addon_tiers.block.custom.ReplicatorAdvancedBlockEntity;
import org.mob.replication_addon_tiers.registry.ModRegistry;

public class ReplicatorAdvancedBlock extends RotatableBlock<ReplicatorAdvancedBlockEntity> implements INetworkDirectionalConnection {
    public static BooleanProperty HAS_ENCLOSURE = BooleanProperty.create("has_enclosure");
    public static BooleanProperty HAS_MOTOR = BooleanProperty.create("has_motor");

    public ReplicatorAdvancedBlock() {
        super("replicator_advanced", Properties.ofFullCopy(Blocks.IRON_BLOCK), ReplicatorAdvancedBlockEntity.class);
        this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(HAS_ENCLOSURE, false)).setValue(HAS_MOTOR, false));
    }

    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return (pos, blockState) -> new ReplicatorAdvancedBlockEntity(this, ModRegistry.REPLICATOR_ADVANCED_BLOCK_BE.get(), pos, blockState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        super.createBlockStateDefinition(blockStateBuilder);
        blockStateBuilder.add(new Property[]{HAS_ENCLOSURE, HAS_MOTOR});
    }

    public RotatableBlock.@NotNull RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext selectionContext) {
        Direction rotation = (Direction)state.getValue(FACING_HORIZONTAL);
        if (rotation == Direction.NORTH) {
            return ReplicatorShapes.NORTH_FULL;
        } else if (rotation == Direction.SOUTH) {
            return ReplicatorShapes.SOUTH_FULL;
        } else if (rotation == Direction.EAST) {
            return ReplicatorShapes.EAST_FULL;
        } else {
            return rotation == Direction.WEST ? ReplicatorShapes.WEST_FULL : super.getCollisionShape(state, world, pos, selectionContext);
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        Direction rotation = (Direction)state.getValue(FACING_HORIZONTAL);
        if (rotation == Direction.NORTH) {
            return ReplicatorShapes.NORTH_FULL;
        } else if (rotation == Direction.SOUTH) {
            return ReplicatorShapes.SOUTH_FULL;
        } else if (rotation == Direction.EAST) {
            return ReplicatorShapes.EAST_FULL;
        } else {
            return rotation == Direction.WEST ? ReplicatorShapes.WEST_FULL : super.getShape(state, p_60556_, p_60557_, p_60558_);
        }
    }

    public Pair<VoxelShape, VoxelShape> getShapePlate(BlockState state) {
        Direction rotation = (Direction)state.getValue(FACING_HORIZONTAL);
        if (rotation == Direction.NORTH) {
            return Pair.of(ReplicatorShapes.NORTH, ReplicatorShapes.NORTH_PLATE);
        } else if (rotation == Direction.SOUTH) {
            return Pair.of(ReplicatorShapes.SOUTH, ReplicatorShapes.SOUTH_PLATE);
        } else if (rotation == Direction.EAST) {
            return Pair.of(ReplicatorShapes.EAST, ReplicatorShapes.EAST_PLATE);
        } else {
            return rotation == Direction.WEST ? Pair.of(ReplicatorShapes.WEST, ReplicatorShapes.WEST_PLATE) : Pair.of(ReplicatorShapes.NORTH, ReplicatorShapes.NORTH_PLATE);
        }
    }

    public boolean canConnect(Level level, BlockPos pos, BlockState state, Direction direction) {
        FacingUtil.Sideness sideness = FacingUtil.getFacingRelative(direction, (Direction)state.getValue(FACING_HORIZONTAL));
        if (direction == Direction.UP) {
            return false;
        } else {
            return sideness == FacingUtil.Sideness.BOTTOM || sideness == FacingUtil.Sideness.BACK;
        }
    }

    public NonNullList<ItemStack> getDynamicDrops(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        NonNullList<ItemStack> list = super.getDynamicDrops(state, worldIn, pos, newState, isMoving);
        if ((Boolean)state.getValue(HAS_MOTOR)) {
            list.add(new ItemStack(com.buuz135.replication.ReplicationRegistry.Items.REPLICATOR_MOTOR));
        }

        if ((Boolean)state.getValue(HAS_ENCLOSURE)) {
            list.add(new ItemStack(com.buuz135.replication.ReplicationRegistry.Items.REPLICATOR_ENCLOSURE));
        }

        return list;
    }
}
