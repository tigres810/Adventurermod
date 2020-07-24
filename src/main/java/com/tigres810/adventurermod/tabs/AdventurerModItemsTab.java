package com.tigres810.adventurermod.tabs;

import com.tigres810.adventurermod.init.ModFluids;
import com.tigres810.adventurermod.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class AdventurerModItemsTab extends CreativeTabs {

	public AdventurerModItemsTab(String label ) { super("AdventurerModItemsTab");
		this.setBackgroundImageName("adventurermod.png"); }

	public ItemStack getTabIconItem() { return new ItemStack(ModItems.INFORMATION_TABLET); }
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> items) {
		super.displayAllRelevantItems(items);
		items.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.FLUX_FLUID, 1)));
	}
}
