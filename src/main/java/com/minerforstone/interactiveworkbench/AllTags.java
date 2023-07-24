package com.minerforstone.interactiveworkbench;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AllTags {
    public static final TagKey<Item> CRAFTING_TOOLS = TagKey.of(Registry.ITEM_KEY, new Identifier("interactiveworkbench", "crafting_tools"));
}
