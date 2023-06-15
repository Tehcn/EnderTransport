package dev.tehcn.endertransport.block.entity;

import dev.tehcn.endertransport.recipe.AlloyingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlloyFurnaceBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public static final int LAVA_COST_PER_TICK = 1;

    protected final PropertyDelegate propertyDelegate;

    private int progress = 0;
    private final int maxProgress = 72;

    // in mB (millibuckets)
    private int fuel = 0;
    private final int fuelCapacity = 5000;

    public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALLOY_FURNACE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AlloyFurnaceBlockEntity.this.progress;
                    case 1 -> AlloyFurnaceBlockEntity.this.maxProgress;
                    case 2 -> AlloyFurnaceBlockEntity.this.fuel;
                    case 3 -> AlloyFurnaceBlockEntity.this.fuelCapacity;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AlloyFurnaceBlockEntity.this.progress = value;
                    case 1 -> throw new RuntimeException("Error trying to mutate AlloyFurnaceBlockEntity propdel idx 1");
                    case 2 -> AlloyFurnaceBlockEntity.this.fuel = value;
                    case 3 -> throw new RuntimeException("Error trying to mutate AlloyFurnaceBlockEntity propdel idx 3");
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.endertransport.alloy_furnace");
    }

    // TODO: make screen handler
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("alloy_furnace.progress", progress);
        nbt.putInt("alloy_furnace.fuel", fuel);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("alloy_furnace.progress");
        fuel = nbt.getInt("alloy_furnace.fuel");
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, AlloyFurnaceBlockEntity entity) {
        if(world.isClient) return;

        if(hasRecipe(entity) && hasFuel(entity)) {
            entity.progress++;
            markDirty(world, blockPos, state);
            if(entity.progress >= entity.maxProgress) {
                craftItem(entity);
            } else {
                entity.resetProgress();
                markDirty(world, blockPos, state);
            }
        }

    }

    private static boolean hasRecipe(AlloyFurnaceBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for(int i=0;i<entity.size();i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<AlloyingRecipe> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(AlloyingRecipe.Type.INSTANCE, inventory, entity.getWorld());

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput().getItem());
    }

    private static boolean hasFuel(AlloyFurnaceBlockEntity entity) {
        return entity.fuel > LAVA_COST_PER_TICK;
    }

    public void resetProgress() { this.progress = 0; }

    private static void craftItem(AlloyFurnaceBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for(int i=0;i<entity.size();i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<AlloyingRecipe> recipe = entity.getWorld().getRecipeManager()
                .getFirstMatch(AlloyingRecipe.Type.INSTANCE, inventory, entity.getWorld());

        if(hasRecipe(entity)) {
            entity.removeStack(1, 1);
            entity.setStack(2, new ItemStack(recipe.get().getOutput().getItem(),
                    entity.getStack(2).getCount() + 1));
        }

        entity.resetProgress();
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }
}
