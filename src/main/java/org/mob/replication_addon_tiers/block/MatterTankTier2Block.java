package org.mob.replication_addon_tiers.block;

import com.buuz135.replication.ReplicationAttachments;
import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.block.shapes.MatterTankShapes;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.datagenerator.loot.block.BasicBlockLootTables;
import com.hrznstudio.titanium.nbthandler.NBTManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mob.replication_addon_tiers.Config;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier2BlockEntity;
import org.mob.replication_addon_tiers.registry.ModRegistry;

import javax.annotation.Nonnull;
import java.util.List;

public class MatterTankTier2Block extends RotatableBlock<MatterTankTier2BlockEntity> implements INetworkDirectionalConnection {

    public MatterTankTier2Block() {
        super("matter_tank_tier_2", Properties.ofFullCopy(Blocks.IRON_BLOCK), MatterTankTier2BlockEntity.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return (pos, blockState) -> new MatterTankTier2BlockEntity(this, ModRegistry.MATTER_TANK_TIER_2_BE.get(), pos, blockState);
    }

    @NotNull
    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @NotNull
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext selectionContext) {
        return MatterTankShapes.SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return MatterTankShapes.SHAPE;
    }
    
    @Override
    public boolean canConnect(Level level, BlockPos pos, BlockState state, Direction direction) {
        return direction == Direction.UP || direction == Direction.DOWN;
    }

    @Override
    public LootTable.Builder getLootTable(@Nonnull BasicBlockLootTables blockLootTables) {
        return blockLootTables.droppingNothing();
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_60537_, LootParams.Builder builder) {
        NonNullList<ItemStack> stacks = NonNullList.create();
        ItemStack stack = new ItemStack(this);
        BlockEntity tankTile = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (tankTile instanceof MatterTankTier2BlockEntity tile) {
            if (!tile.getTanks().get(0).getMatter().isEmpty()) {
                stack.set(ReplicationAttachments.TILE, NBTManager.getInstance().writeTileEntity(tile, new CompoundTag()));
            }
        }
        stacks.add(stack);
        return stacks;
    }

    @Override
    public NonNullList<ItemStack> getDynamicDrops(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        return NonNullList.create();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack stack) {
        super.setPlacedBy(level, pos, blockState, livingEntity, stack);
        BlockEntity entity = level.getBlockEntity(pos);
        if (stack.has(ReplicationAttachments.TILE)) {
            if (entity instanceof MatterTankTier2BlockEntity tile) {
                entity.loadCustomOnly(stack.get(ReplicationAttachments.TILE), entity.getLevel().registryAccess());
                tile.markForUpdate();
            }
        }
    }

}
