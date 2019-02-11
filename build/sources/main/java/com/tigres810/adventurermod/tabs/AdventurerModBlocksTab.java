package com.tigres810.adventurermod.tabs;

import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class AdventurerModBlocksTab extends CreativeTabs {

	public AdventurerModBlocksTab(String label ) { super("AdventurerModBlocksTab");
	this.setBackgroundImageName("adventurermod.png"); }

	public ItemStack getTabIconItem() { return new ItemStack(ModBlocks.FLUX_GENERATOR_BLOCK); }
}
