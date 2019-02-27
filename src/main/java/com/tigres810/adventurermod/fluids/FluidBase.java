package com.tigres810.adventurermod.fluids;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.init.ModFluids;
import com.tigres810.adventurermod.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidBase extends BlockFluidClassic {

	public FluidBase(String name, Fluid fluid, Material material) {
		
		super(fluid, material);
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(worldIn.getBlockState(fromPos).getBlock() instanceof FluidBase) {
            FluidBase block = (FluidBase)worldIn.getBlockState(fromPos).getBlock();
            Fluid fluid = block.getFluid();
            if(fluid == ModFluids.FLUX_FLUID) {
                if(block.isSourceBlock(worldIn, fromPos)) {
                	worldIn.setBlockState(fromPos, worldIn.getBlockState(fromPos).withProperty(LEVEL, 16));
                }
            }
        }
        super.onNeighborChange(worldIn, pos, fromPos);
    }
}
