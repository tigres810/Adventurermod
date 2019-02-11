package com.tigres810.adventurermod.blocks.pipes;

import java.util.List;
import java.util.Random;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.blocks.BlockBase;
import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxGenerator;
import com.tigres810.adventurermod.blocks.pipes.tileentity.TileEntityFluxPipe;
import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.util.Reference;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEntityFluxPipe extends BlockBase {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool GENERATING = PropertyBool.create("generating");
	public static final AxisAlignedBB FLUX_PIPE_BLOCK = new AxisAlignedBB(0, 0.0625 * 5, 0.0625 * 5, 0.0625 * 16, 0.0625 * 11, 0.0625 * 11);
	public static final AxisAlignedBB FLUX_PIPE_BLOCK1 = new AxisAlignedBB(0.0625 * 5, 0.0625 * 5, 0, 0.0625 * 11, 0.0625 * 11, 0.0625 * 16);
	
	public BlockEntityFluxPipe(String name, Material material) {
		super(name, material);
		
		//Block Meta
		setSoundType(SoundType.GLASS);
		setHardness(5.0f);
		setResistance(1.000f);
		setLightOpacity(1);
			
		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(GENERATING, false));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return Item.getItemFromBlock(ModBlocks.FLUX_GENERATOR_BLOCK);
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		
		return new ItemStack(ModBlocks.FLUX_GENERATOR_BLOCK);
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
	
	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active) worldIn.setBlockState(pos, ModBlocks.FLUX_GENERATOR_BLOCK.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(GENERATING, true), 3);
		else worldIn.setBlockState(pos, ModBlocks.FLUX_GENERATOR_BLOCK.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(GENERATING, false), 3);
		
		if(tileentity != null) 
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityFluxPipe();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityFluxPipe tileentity = (TileEntityFluxPipe)worldIn.getTileEntity(pos);
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()), 2);
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
		return new BlockStateContainer(this, new IProperty[] {GENERATING,FACING});
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
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing facing = EnumFacing.getFront(state.getValue(FACING).getIndex());
		if (this.getRegistryName().equals(ModBlocks.FLUX_PIPE_BLOCK.getRegistryName())) {
			if(facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) { return FLUX_PIPE_BLOCK1; } else { return FLUX_PIPE_BLOCK; }
		} else {
			return super.getBoundingBox(state, source, pos);
		}
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		EnumFacing facing = EnumFacing.getFront(state.getValue(FACING).getIndex());
		if(facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH) ) {
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, FLUX_PIPE_BLOCK1);
		} else {
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, FLUX_PIPE_BLOCK);
		}
	}
}
