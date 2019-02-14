package com.tigres810.adventurermod.init;

import java.util.ArrayList;
import java.util.List;

import com.tigres810.adventurermod.blocks.BlockBase;
import com.tigres810.adventurermod.blocks.machines.BlockEntityFluxGenerator;
import com.tigres810.adventurermod.blocks.pipes.BlockEntityFluxPipe;
import com.tigres810.adventurermod.blocks.pipes.BlockEntityFluxPipeSideConnector;
import com.tigres810.adventurermod.fluids.FluidBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	//Machines
	public static final Block FLUX_GENERATOR_BLOCK = new BlockEntityFluxGenerator("flux_generator_block", Material.ANVIL);
	
	//Pipes
	public static final Block FLUX_PIPE_BLOCK = new BlockEntityFluxPipe("flux_pipe_block", Material.WOOD);
	public static final Block FLUX_PIPE_BLOCK_SIDE_CONNECTOR = new BlockEntityFluxPipeSideConnector("flux_pipe_block_side_connector", Material.WOOD);
	
	//Fluids
	public static final Block FLUX_FLUID_BLOCK = new FluidBase("flux_fluid_block", ModFluids.FLUX_FLUID, Material.WATER);
}
