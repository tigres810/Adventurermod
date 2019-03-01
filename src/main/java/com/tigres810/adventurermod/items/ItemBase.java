package com.tigres810.adventurermod.items;

import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;
import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.client.gui.GuiInformationTablet;
import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.init.ModFluids;
import com.tigres810.adventurermod.init.ModItems;
import com.tigres810.adventurermod.tabs.AdventurerModItemsTab;
import com.tigres810.adventurermod.util.IHasModel;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ItemBase extends Item implements IHasModel {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	public static final PropertyBool CONNECTED = PropertyBool.create("connected");
	
	private BlockPos blockpos;
	
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
