package com.minerforstone.interactiveworkbench;

import com.minerforstone.interactiveworkbench.blocks.WorkbenchBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InteractiveWorkbench implements ModInitializer {
    public static final Item SAW = Registry.register(Registry.ITEM, new Identifier("interactiveworkbench", "saw"), new Item(new FabricItemSettings().maxCount(1)));
    public static final Item HAMMER = Registry.register(Registry.ITEM, new Identifier("interactiveworkbench", "hammer"), new Item(new FabricItemSettings().maxCount(1)));
    public static final Item PLIERS = Registry.register(Registry.ITEM, new Identifier("interactiveworkbench", "pliers"), new Item(new FabricItemSettings().maxCount(1)));
    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("interactiveworkbench", "workbench"), WorkbenchBlock.WORKBENCH_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("interactiveworkbench", "workbench"), new BlockItem(WorkbenchBlock.WORKBENCH_BLOCK, new FabricItemSettings()));
    }
}
