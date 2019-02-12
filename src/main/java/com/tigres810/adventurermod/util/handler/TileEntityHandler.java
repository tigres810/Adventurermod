package com.tigres810.adventurermod.util.handler;

import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxGenerator;
import com.tigres810.adventurermod.blocks.pipes.tileentity.TileEntityFluxPipe;
import com.tigres810.adventurermod.blocks.pipes.tileentity.TileEntityFluxPipeSideConnector;
import com.tigres810.adventurermod.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityFluxGenerator.class, new ResourceLocation(Reference.MOD_ID + ":flux_generator_block"));
		GameRegistry.registerTileEntity(TileEntityFluxPipe.class, new ResourceLocation(Reference.MOD_ID + ":flux_pipe_block"));
		GameRegistry.registerTileEntity(TileEntityFluxPipeSideConnector.class, new ResourceLocation(Reference.MOD_ID + ":flux_pipe_block_side_connector"));
	}
}