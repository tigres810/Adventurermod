package com.tigres810.adventurermod.blocks.pipes.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxGenerator;
import com.tigres810.adventurermod.energy.CustomEnergyStorage;
import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.init.ModFluids;
import com.tigres810.adventurermod.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;


public class TileEntityFluxPipe extends TileEntity implements ITickable
{
	private PropertyDirection FACING = BlockHorizontal.FACING;
	private BlockPos neighbourPos = null;
	
	private IBlockState neighbourState = null;

    private Block neighbourBlock = null;
	private CustomEnergyStorage storage = new CustomEnergyStorage(500);
	public int energy = storage.getEnergyStored();
	private boolean output = false;
	private String customName;
	
	@Override
	public void update() 
	{
		//Handle check block nearby
		if(world.getBlockState(pos).getValue(FACING) != null) {
			//Check block neighbourPos
			EnumFacing face = (EnumFacing)world.getBlockState(pos).getValue(FACING);
			if( face.equals( EnumFacing.NORTH ) ) {
				neighbourPos = pos.offset(EnumFacing.NORTH);
				
				neighbourState = world.getBlockState(neighbourPos);

			    neighbourBlock = neighbourState.getBlock();
			    
			    if ( neighbourBlock == ModBlocks.FLUX_GENERATOR_BLOCK ) {

			    	if( neighbourState.getValue(FACING).equals( EnumFacing.EAST ) || neighbourState.getValue(FACING).equals( EnumFacing.WEST ) ) {
			    		TileEntityFluxGenerator tile = (TileEntityFluxGenerator) world.getTileEntity(neighbourPos);
			    		if(tile != null) {
			    			if(tile.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.NORTH) && energy < storage.getMaxEnergyStored()) {
			    				if(world.getTileEntity(pos).hasCapability(CapabilityEnergy.ENERGY, EnumFacing.NORTH)) {
				    				int FUEL = tile.getFuelValueFromGenerator();
				    				if(FUEL > 0) {
				    					energy += 1;
				    					tile.consumeEnergy(1);
				    				}
			    				}
			    			}
			    		}
			    	}
			    }
			} else if( face.equals( EnumFacing.SOUTH ) ) {
				neighbourPos = pos.offset(EnumFacing.SOUTH);
				
				neighbourState = world.getBlockState(neighbourPos);

			    neighbourBlock = neighbourState.getBlock();
			    
			    if ( neighbourBlock == ModBlocks.FLUX_GENERATOR_BLOCK ) {

			    	if( neighbourState.getValue(FACING).equals( EnumFacing.EAST ) || neighbourState.getValue(FACING).equals( EnumFacing.WEST ) ) {
			    		TileEntityFluxGenerator tile = (TileEntityFluxGenerator) world.getTileEntity(neighbourPos);
			    		if(tile != null) {
			    			if(tile.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.SOUTH) && energy < storage.getMaxEnergyStored()) {
			    				if(world.getTileEntity(pos).hasCapability(CapabilityEnergy.ENERGY, EnumFacing.SOUTH)) {
				    				int FUEL = tile.getFuelValueFromGenerator();
				    				if(FUEL > 0) {
				    					energy += 1;
				    					tile.consumeEnergy(1);
				    				}
			    				}
			    			}
			    		}
			    	}
			    }
			} else if( face.equals( EnumFacing.EAST ) ) {
				neighbourPos = pos.offset(EnumFacing.EAST);
				
				neighbourState = world.getBlockState(neighbourPos);

			    neighbourBlock = neighbourState.getBlock();
			    
			    if ( neighbourBlock == ModBlocks.FLUX_GENERATOR_BLOCK ) {

			    	if( neighbourState.getValue(FACING).equals( EnumFacing.NORTH ) || neighbourState.getValue(FACING).equals( EnumFacing.SOUTH ) ) {
			    		TileEntityFluxGenerator tile = (TileEntityFluxGenerator) world.getTileEntity(neighbourPos);
			    		if(tile != null) {
			    			if(tile.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.EAST) && energy < storage.getMaxEnergyStored()) {
			    				if(world.getTileEntity(pos).hasCapability(CapabilityEnergy.ENERGY, EnumFacing.EAST)) {
				    				int FUEL = tile.getFuelValueFromGenerator();
				    				if(FUEL > 0) {
				    					energy += 1;
				    					tile.consumeEnergy(1);
				    				}
			    				}
			    			}
			    		}
			    	}
			    }
			} else if( face.equals( EnumFacing.WEST ) ) {
				neighbourPos = pos.offset(EnumFacing.WEST);
				
				neighbourState = world.getBlockState(neighbourPos);

			    neighbourBlock = neighbourState.getBlock();
			    
			    if ( neighbourBlock == ModBlocks.FLUX_GENERATOR_BLOCK ) {

			    	if( neighbourState.getValue(FACING).equals( EnumFacing.NORTH ) || neighbourState.getValue(FACING).equals( EnumFacing.SOUTH ) ) {
			    		TileEntityFluxGenerator tile = (TileEntityFluxGenerator) world.getTileEntity(neighbourPos);
			    		if(tile != null) {
			    			if(tile.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.WEST) && energy < storage.getMaxEnergyStored()) {
			    				if(world.getTileEntity(pos).hasCapability(CapabilityEnergy.ENERGY, EnumFacing.WEST)) {
				    				int FUEL = tile.getFuelValueFromGenerator();
				    				if(FUEL > 0) {
				    					energy += 1;
				    					tile.consumeEnergy(1);
				    				}
			    				}
			    			}
			    		}
			    	}
			    }
			}
		}
	}
	
	public void consumeEnergy(int amount) {
    	energy -= amount;
    }
    
    public int getFuelValueFromPipe() 
	{
		return energy;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setInteger("GuiEnergy", this.energy);
		compound.setString("Name", this.getDisplayName().toString());
		this.storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		this.energy = compound.getInteger("GuiEnergy");
		this.customName = compound.getString("Name");
		this.storage.readFromNBT(compound);
	}
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentTranslation("tile.flux_pipe_block.name");
	}

	public int getEnergyStored() 
	{
		return energy;
	}
	
	public int getMaxEnergyStored() 
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public int getField(int id)
	{
		switch(id)
		{
		case 0:
			return this.energy;
		default:
			return 0;
		}
	}
	
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0:
			this.energy = value;
		}
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64D;
	}
}