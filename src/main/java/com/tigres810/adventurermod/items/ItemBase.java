package com.tigres810.adventurermod.items;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.client.gui.GuiInformationTablet;
import com.tigres810.adventurermod.init.ModItems;
import com.tigres810.adventurermod.util.IHasModel;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ItemBase extends Item implements IHasModel {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	public static final PropertyBool CONNECTED = PropertyBool.create("connected");
	
	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.adventurermoditemstab);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

		if(!worldIn.isRemote) {
			if(playerIn.getHeldItem(handIn).getUnlocalizedName().equals(ModItems.INFORMATION_TABLET.getUnlocalizedName())) {
				//System.out.print("Done well!"); // Print stuff if it works
			}
		} else {
			if(playerIn.getHeldItem(handIn).getUnlocalizedName().equals(ModItems.INFORMATION_TABLET.getUnlocalizedName())) {
				FMLCommonHandler.instance().showGuiScreen(new GuiInformationTablet());
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
}
