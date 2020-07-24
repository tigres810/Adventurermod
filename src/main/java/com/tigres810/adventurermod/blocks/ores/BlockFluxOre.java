package com.tigres810.adventurermod.blocks.ores;

import java.util.Random;

import com.tigres810.adventurermod.blocks.BlockBase;
import com.tigres810.adventurermod.init.ModItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockFluxOre extends BlockBase {

	public BlockFluxOre(String name, Material material) {
		super(name, material);

		setSoundType(SoundType.STONE);
		setHardness(8.0F);
		setResistance(4.000f);
		setHarvestLevel("pickaxe", 1);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		// TODO Auto-generated method stub
		return ModItems.FLUX_ORE;
	}
	
	@Override
	public int quantityDropped(Random random) {
		int max = 6;
		int min = 3;
		return random.nextInt(max) + min;
	}

}
