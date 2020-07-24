package com.tigres810.adventurermod.init;

import java.util.ArrayList;
import java.util.List;

import com.tigres810.adventurermod.blocks.machines.BlockEntityFluxConveyor;
import com.tigres810.adventurermod.blocks.machines.BlockEntityFluxCrafter;
import com.tigres810.adventurermod.blocks.machines.BlockEntityFluxGenerator;
import com.tigres810.adventurermod.blocks.machines.BlockFluxCrafterLeftSide;
import com.tigres810.adventurermod.blocks.machines.storages.BlockEntityFluxStorage;
import com.tigres810.adventurermod.blocks.ores.BlockFluxOre;
import com.tigres810.adventurermod.blocks.pipes.BlockEntityFluxPipe;
import com.tigres810.adventurermod.fluids.FluidBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	//Machines
	public static final Block FLUX_GENERATOR_BLOCK = new BlockEntityFluxGenerator("flux_generator_block", Material.IRON);
	public static final Block FLUX_CRAFTER_BLOCK = new BlockEntityFluxCrafter("flux_crafter_block", Material.IRON);
	public static final Block FLUX_CRAFTER_SIDE_BLOCK = new BlockFluxCrafterLeftSide("flux_crafter_side_block", Material.IRON);
	
	//Conveyors
	public static final Block FLUX_CONVEYOR_BLOCK = new BlockEntityFluxConveyor("flux_conveyor_block", Material.IRON);
	
	//Pipes
	public static final Block FLUX_PIPE_BLOCK = new BlockEntityFluxPipe("flux_pipe_block", Material.WOOD);
	
	//Ores
	public static final Block FLUX_ORE_BLOCK = new BlockFluxOre("flux_ore_block", Material.ROCK);
	
	//Fluids
	public static final Block FLUX_FLUID_BLOCK = new FluidBase("flux_fluid_block", ModFluids.FLUX_FLUID, Material.WATER);
	
	//Storages
	public static final Block FLUX_STORAGE_BLOCK = new BlockEntityFluxStorage("flux_storage_block", Material.IRON);
}
