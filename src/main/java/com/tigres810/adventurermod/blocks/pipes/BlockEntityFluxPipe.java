package com.tigres810.adventurermod.blocks.pipes;

import java.util.List;
import java.util.Random;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.blocks.BlockBase;
import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxGenerator;
import com.tigres810.adventurermod.blocks.pipes.tileentity.TileEntityFluxPipe;
import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEntityFluxPipe extends BlockBase {

	//public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final AxisAlignedBB FLUX_PIPE_BLOCK = new AxisAlignedBB(0, 0.0625 * 5, 0.0625 * 5, 0.0625 * 16, 0.0625 * 11, 0.0625 * 11);
	public static final AxisAlignedBB FLUX_PIPE_BLOCK1 = new AxisAlignedBB(0.0625 * 5, 0.0625 * 5, 0, 0.0625 * 11, 0.0625 * 11, 0.0625 * 16);
	
	public BlockEntityFluxPipe(String name, Material material) {
		super(name, material);
		
		//Block Meta
		setSoundType(SoundType.METAL);
		setHardness(5.0f);
		setResistance(1.000f);
		setLightOpacity(1);
		
		this.setDefaultState(this.getDefaultState().withProperty(UP, false).withProperty(DOWN, false).withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(EAST, false).withProperty(WEST, false));
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
			/*
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
            */
		}
	}
	
	/*
	public static void setState(boolean up, boolean down, boolean north, boolean south, boolean east, boolean west ,World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(render == 0) worldIn.setBlockState(pos, ModBlocks.FLUX_GENERATOR_BLOCK.getDefaultState().withProperty(UP, true), 3);
		else worldIn.setBlockState(pos, ModBlocks.FLUX_GENERATOR_BLOCK.getDefaultState().withProperty(UP, false).withProperty(DOWN, false).withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(EAST, false).withProperty(WEST, false), 3);
		
		if(tileentity != null) 
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	*/
	
	private boolean isSideConnectable (IBlockAccess world, BlockPos pos, EnumFacing side) {
        
        final IBlockState state = world.getBlockState(pos.offset(side));
        return (state == null) ? false : state.getBlock() instanceof BlockEntityFluxPipe;
    }
	
	@Override
    public IBlockState getActualState (IBlockState state, IBlockAccess world, BlockPos position) {
         
        return state.withProperty(DOWN, this.isSideConnectable(world, position, EnumFacing.DOWN)).withProperty(EAST, this.isSideConnectable(world, position, EnumFacing.EAST)).withProperty(NORTH, this.isSideConnectable(world, position, EnumFacing.NORTH)).withProperty(SOUTH, this.isSideConnectable(world, position, EnumFacing.SOUTH)).withProperty(UP, this.isSideConnectable(world, position, EnumFacing.UP)).withProperty(WEST, this.isSideConnectable(world, position, EnumFacing.WEST));
    }
	
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.attackEntityFrom(DamageSource.CACTUS, 3.0F);
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {UP, DOWN, NORTH, SOUTH, EAST, WEST});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	
	public IBlockState getStateFromMeta(int meta) 
	{
		return this.getDefaultState();
	}
	
	/*
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
	*/
}
