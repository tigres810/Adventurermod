package com.tigres810.adventurermod.blocks.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.Interfaces.IPipeConnect;
import com.tigres810.adventurermod.blocks.BlockBase;
import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxCrafter;
import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEntityFluxCrafter extends BlockBase implements IPipeConnect {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	public static final PropertyBool CONNECTED = PropertyBool.create("connected");
	
	public BlockEntityFluxCrafter(String name, Material material) {
		super(name, material);

		//Block Meta
		setSoundType(SoundType.METAL);
		setHardness(10.0f);
		setResistance(6.000f);
					
		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
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
			playerIn.openGui(Main.instance, Reference.GUI_FLUX_CRAFTER_BLOCK, worldIn, pos.getX(), pos.getY(), pos.getZ());
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
            boolean bool = false;
            if(face.equals(EnumFacing.NORTH)) {
            	if(worldIn.isAirBlock(pos.offset(EnumFacing.EAST))) {
            		bool = true;
            	}
            } else if(face.equals(EnumFacing.SOUTH)) {
            	if(worldIn.isAirBlock(pos.offset(EnumFacing.EAST.getOpposite()))) {
            		bool = true;
            	}
            } else if(face.equals(EnumFacing.EAST)) {
            	if(worldIn.isAirBlock(pos.offset(EnumFacing.SOUTH))) {
            		bool = true;
            	}
            } else if (face.equals(EnumFacing.WEST)) {
            	if(worldIn.isAirBlock(pos.offset(EnumFacing.SOUTH.getOpposite()))) {
            		bool = true;
            	}
            }
            
            if(bool == true) {
            	worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
            	if(face.equals(EnumFacing.NORTH)) {
            		BlockFluxCrafterLeftSide.setBlock(worldIn, pos.offset(EnumFacing.EAST), face);
            	} else if(face.equals(EnumFacing.SOUTH)) {
            		BlockFluxCrafterLeftSide.setBlock(worldIn, pos.offset(EnumFacing.EAST.getOpposite()), face);
            	} else if(face.equals(EnumFacing.EAST)) {
            		BlockFluxCrafterLeftSide.setBlock(worldIn, pos.offset(EnumFacing.SOUTH), face);
            	} else if (face.equals(EnumFacing.WEST)) {
            		BlockFluxCrafterLeftSide.setBlock(worldIn, pos.offset(EnumFacing.SOUTH.getOpposite()), face);
            	}
            } else {
            	worldIn.destroyBlock(pos, true);
            }
		}
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active) worldIn.setBlockState(pos, ModBlocks.FLUX_CRAFTER_BLOCK.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, true).withProperty(CONNECTED, true), 3);
		else worldIn.setBlockState(pos, ModBlocks.FLUX_CRAFTER_BLOCK.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, false).withProperty(CONNECTED, true), 3);
		
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
		return new TileEntityFluxCrafter();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityFluxCrafter tileentity = (TileEntityFluxCrafter)worldIn.getTileEntity(pos);
		worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.handler.getStackInSlot(0)));
		if(state.getValue(FACING).equals(EnumFacing.NORTH) && worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockFluxCrafterLeftSide) {
        	worldIn.destroyBlock(pos.offset(EnumFacing.EAST), false);
        } else if(state.getValue(FACING).equals(EnumFacing.SOUTH) && worldIn.getBlockState(pos.offset(EnumFacing.EAST.getOpposite())).getBlock() instanceof BlockFluxCrafterLeftSide) {
        	worldIn.destroyBlock(pos.offset(EnumFacing.EAST.getOpposite()), false);
        } else if(state.getValue(FACING).equals(EnumFacing.EAST) && worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockFluxCrafterLeftSide) {
        	worldIn.destroyBlock(pos.offset(EnumFacing.SOUTH), false);
        } else if (state.getValue(FACING).equals(EnumFacing.WEST) && worldIn.getBlockState(pos.offset(EnumFacing.SOUTH.getOpposite())).getBlock() instanceof BlockFluxCrafterLeftSide) {
        	worldIn.destroyBlock(pos.offset(EnumFacing.SOUTH.getOpposite()), false);
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
		return new BlockStateContainer(this, new IProperty[] {CONNECTED,BURNING,FACING});
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
    public List<EnumFacing> getConnectableSides(IBlockState state) {
        List<EnumFacing> faces = new ArrayList<EnumFacing>();
        faces.add(state.getValue(FACING));
        return faces;
    }
	
	public static void openGui(World worldIn, BlockPos pos ,EntityPlayer playerIn) {
		playerIn.openGui(Main.instance, Reference.GUI_FLUX_CRAFTER_BLOCK, worldIn, pos.getX(), pos.getY(), pos.getZ());
	}


}
