package com.tigres810.adventurermod.blocks.machines;

import java.util.Random;

import com.tigres810.adventurermod.blocks.BlockBase;
import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxCrafter;
import com.tigres810.adventurermod.init.ModBlocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFluxCrafterLeftSide extends BlockBase {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	
	public BlockFluxCrafterLeftSide(String name, Material material) {
		super(name, material);
			
			//Block Meta
			setSoundType(SoundType.METAL);
			setHardness(10.0f);
			setResistance(6.000f);
			
			setCreativeTab(null);
				
			this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return Item.getItemFromBlock(ModBlocks.FLUX_CRAFTER_BLOCK);
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		
		return new ItemStack(ModBlocks.FLUX_CRAFTER_BLOCK);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(state.getValue(FACING).equals(EnumFacing.NORTH)) {
	        	((BlockEntityFluxCrafter) ModBlocks.FLUX_CRAFTER_BLOCK).openGui(worldIn, pos.offset(EnumFacing.EAST.getOpposite()), state, playerIn, hand, facing, hitX, hitY, hitZ);
	        } else if(state.getValue(FACING).equals(EnumFacing.SOUTH)) {
	        	((BlockEntityFluxCrafter) ModBlocks.FLUX_CRAFTER_BLOCK).openGui(worldIn, pos.offset(EnumFacing.EAST), state, playerIn, hand, facing, hitX, hitY, hitZ);
	        } else if(state.getValue(FACING).equals(EnumFacing.EAST)) {
	        	((BlockEntityFluxCrafter) ModBlocks.FLUX_CRAFTER_BLOCK).openGui(worldIn, pos.offset(EnumFacing.SOUTH.getOpposite()), state, playerIn, hand, facing, hitX, hitY, hitZ);
	        } else if (state.getValue(FACING).equals(EnumFacing.WEST)) {
	        	((BlockEntityFluxCrafter) ModBlocks.FLUX_CRAFTER_BLOCK).openGui(worldIn, pos.offset(EnumFacing.SOUTH), state, playerIn, hand, facing, hitX, hitY, hitZ);
	        }
		}
		
		return true;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if(!worldIn.isRemote) {
			IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());
            IBlockState east = worldIn.getBlockState(pos.east());
            EnumFacing face = (EnumFacing)state.getValue(FACING);

            if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
            else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.NORTH;
            else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) face = EnumFacing.EAST;
            else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.WEST;
            worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityFluxCrafter();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(state.getValue(FACING).equals(EnumFacing.NORTH)) {
        	worldIn.destroyBlock(pos.offset(EnumFacing.EAST.getOpposite()), false);
        } else if(state.getValue(FACING).equals(EnumFacing.SOUTH)) {
        	worldIn.destroyBlock(pos.offset(EnumFacing.EAST), false);
        } else if(state.getValue(FACING).equals(EnumFacing.EAST)) {
        	worldIn.destroyBlock(pos.offset(EnumFacing.SOUTH.getOpposite()), false);
        } else if (state.getValue(FACING).equals(EnumFacing.WEST)) {
        	worldIn.destroyBlock(pos.offset(EnumFacing.SOUTH), false);
        }
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		EnumFacing facing = EnumFacing.getFront(meta);
		if(facing.getAxis() == EnumFacing.Axis.Y) facing = EnumFacing.NORTH;
		return this.getDefaultState().withProperty(FACING, facing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	public void setBlock( World worldIn, BlockPos pos, EnumFacing face) {
		IBlockState state = this.getDefaultState();
        worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
	}

}
