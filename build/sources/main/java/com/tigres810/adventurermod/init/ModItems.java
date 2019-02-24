package com.tigres810.adventurermod.init;

import java.util.ArrayList;
import java.util.List;

import com.tigres810.adventurermod.items.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Items
	public static final Item INFORMATION_TABLET = new ItemBase("information_tablet");
	
	//Ore Items
	public static final Item FLUX_ORE = new ItemBase("flux_ore");
}
