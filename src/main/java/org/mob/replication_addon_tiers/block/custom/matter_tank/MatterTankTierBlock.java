package org.mob.replication_addon_tiers.block.custom.matter_tank;

import com.buuz135.replication.ReplicationAttachments;
import com.buuz135.replication.block.shapes.MatterTankShapes;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.datagenerator.loot.block.BasicBlockLootTables;
import com.hrznstudio.titanium.nbthandler.NBTManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mob.replication_addon_tiers.block.entity.matter_tank.MatterTankTierBlockEntity;

import javax.annotation.Nonnull;
import java.util.List;

public class MatterTankTierBlock extends RotatableBlock<MatterTankTierBlockEntity> implements INetworkDirectionalConnection {

    private final TankTier tier;

    public MatterTankTierBlock(TankTier tier) {
        super(tier.getRegistryId(), Properties.ofFullCopy(Blocks.IRON_BLOCK), MatterTankTierBlockEntity.class);
        this.tier = tier;
    }

    public TankTier getTier() {
        return tier;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return (pos, blockState) -> new MatterTankTierBlockEntity(this, pos, blockState);
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
        if (tankTile instanceof MatterTankTierBlockEntity tile) {
            if (!tile.getTanks().get(0).getMatter().isEmpty()) {
                CompoundTag savedData = tile.saveCustomOnly(tile.getLevel().registryAccess());
                stack.set(ReplicationAttachments.TILE, savedData);
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
    public void setPlacedBy(Level level, BlockPos pos, BlockState p_49849_, @Nullable LivingEntity p_49850_, ItemStack stack) {
        super.setPlacedBy(level, pos, p_49849_, p_49850_, stack);
        BlockEntity entity = level.getBlockEntity(pos);
        if (stack.has(ReplicationAttachments.TILE)) {
            if (entity instanceof MatterTankTierBlockEntity tile) {
                entity.loadCustomOnly(TagValueInput.create(ProblemReporter.DISCARDING, entity.getLevel().registryAccess(), stack.get(ReplicationAttachments.TILE)));
                tile.markForUpdate();
            }
        }
    }
}