package com.tigres810.adventurermod.util.handler;

import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.init.ModFluids;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public class RenderHandler {

	public static void registerCustomMeshesAndStates() {
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.FLUX_FLUID_BLOCK), new ItemMeshDefinition() {
			
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation("am:flux_fluid", "fluid");
			}
		});
		
		ModelLoader.setCustomStateMapper(ModBlocks.FLUX_FLUID_BLOCK, new StateMapperBase() {
			
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation("am:flux_fluid", "fluid");
			}
		});
	}
}
