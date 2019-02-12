package com.tigres810.adventurermod.items;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.client.gui.GuiInformationTablet;
import com.tigres810.adventurermod.init.ModItems;
import com.tigres810.adventurermod.tabs.AdventurerModItemsTab;
import com.tigres810.adventurermod.util.IHasModel;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBase extends Item implements IHasModel {

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
			if(playerIn.getHeldItem(handIn).getDisplayName().equals("AdventurerMod Information Tablet") || playerIn.getHeldItem(handIn).getDisplayName().equals("AdventurerMod Tableta de Informacion") ) {
				System.out.print("Done well!"); // Print stuff if it works
			//playerIn.openGui(Main.instance, GuiIds.class, playerIn.world, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ));
				//Minecraft.getMinecraft().displayGuiScreen(new GuiInformationTablet());
			}
		} else {
			if(playerIn.getHeldItem(handIn).getDisplayName().equals("AdventurerMod Information Tablet") || playerIn.getHeldItem(handIn).getDisplayName().equals("AdventurerMod Tableta de Informacion") ) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiInformationTablet());
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}