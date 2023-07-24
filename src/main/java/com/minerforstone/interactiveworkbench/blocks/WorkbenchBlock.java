package com.minerforstone.interactiveworkbench.blocks;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.minerforstone.interactiveworkbench.AllTags;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class WorkbenchBlock extends Block {
    public WorkbenchBlock(Settings settings) {
        super(settings);
        boolean startWithTools = false;
        setDefaultState(getDefaultState().with(SAW, startWithTools));
        setDefaultState(getDefaultState().with(HAMMER, startWithTools));
        setDefaultState(getDefaultState().with(PLIERS, startWithTools));

        itemStateMap.put(Registry.ITEM.get(new Identifier("interactiveworkbench", "saw")), SAW);
        itemStateMap.put(Registry.ITEM.get(new Identifier("interactiveworkbench", "hammer")), HAMMER);
        itemStateMap.put(Registry.ITEM.get(new Identifier("interactiveworkbench", "pliers")), PLIERS);

        directionStateMap.put(Direction.NORTH, HAMMER);
        directionStateMap.put(Direction.EAST, SAW);
        directionStateMap.put(Direction.SOUTH, PLIERS);
        directionStateMap.put(Direction.WEST, SAW);
    }

    private final BiMap<Item, BooleanProperty> itemStateMap = HashBiMap.create();
    private final Map<Direction, BooleanProperty> directionStateMap = new HashMap<>();

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getMainHandStack().isIn(AllTags.CRAFTING_TOOLS)) { // Is this a crafting tool?
            if (!state.get(itemStateMap.get(player.getMainHandStack().getItem()))) { // Run if the tool is not present already on table

                world.setBlockState(pos, state.with(itemStateMap.get(player.getMainHandStack().getItem()), true));

                player.playSound(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, 1, 1);
                player.getMainHandStack().decrement(1);

                return ActionResult.SUCCESS;
            }
            return ActionResult.FAIL;

        } else if (hit.getSide() == Direction.UP || hit.getSide() == Direction.DOWN) { // Open crafting GUI
            return ActionResult.SUCCESS;

        } else if (player.getMainHandStack().isEmpty() && state.get(directionStateMap.get(hit.getSide()))) {
            player.giveItemStack(itemStateMap.inverse().get(directionStateMap.get(hit.getSide())).getDefaultStack());
            world.setBlockState(pos, state.with(directionStateMap.get(hit.getSide()), false));
            player.playSound(SoundEvents.ENTITY_ITEM_FRAME_BREAK, 1, 1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }
    public static final BooleanProperty SAW = BooleanProperty.of("saw");
    public static final BooleanProperty HAMMER = BooleanProperty.of("hammer");
    public static final BooleanProperty PLIERS = BooleanProperty.of("pliers");
    public static final WorkbenchBlock WORKBENCH_BLOCK = new WorkbenchBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD));

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SAW);
        builder.add(HAMMER);
        builder.add(PLIERS);
    }
}
