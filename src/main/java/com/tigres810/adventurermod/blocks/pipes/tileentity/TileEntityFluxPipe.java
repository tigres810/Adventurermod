package com.tigres810.adventurermod.blocks.pipes.tileentity;

import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxGenerator;
import com.tigres810.adventurermod.blocks.pipes.BlockEntityFluxPipe;
import com.tigres810.adventurermod.energy.CustomEnergyStorage;
import com.tigres810.adventurermod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;


public class TileEntityFluxPipe extends TileEntity implements ITickable
{
	private CustomEnergyStorage storage = new CustomEnergyStorage(500);
	public int energy = storage.getEnergyStored();
	private int counttoenergy;
	
	@SuppressWarnings("unused")
	private Block isBlockConnection(Block block) {
		if(block == ModBlocks.FLUX_PIPE_BLOCK) return block;
		else return null;
	}
	
	@Override
	public void update() {
		this.world.getBlockState(pos);
        if (this.world != null) 
        {
            if (!this.world.isRemote)
            {
            	if(counttoenergy < 6) {
            		counttoenergy += 1;
            	} else {
            		counttoenergy = 0;
            	}
                	if(counttoenergy == 1) {
                		if(world.getBlockState(pos.offset(EnumFacing.UP)).getBlock() == ModBlocks.FLUX_PIPE_BLOCK) {
                			TileEntityFluxPipe te = (TileEntityFluxPipe)world.getTileEntity(pos.offset(EnumFacing.UP));
                			if(this.getFuelValueFromPipe() > 200) {
	                			this.consumeEnergy(Math.min(100, this.energy));
	                			te.energy += 100;
                			}
                		}
                	}else if(counttoenergy == 2) {
                		if(world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() == ModBlocks.FLUX_PIPE_BLOCK) {
                			TileEntityFluxPipe te = (TileEntityFluxPipe)world.getTileEntity(pos.offset(EnumFacing.DOWN));
                			if(this.getFuelValueFromPipe() > 200) {
	                			this.consumeEnergy(Math.min(100, this.energy));
	                			te.energy += 100;
                			}
                		}
            		}else if(counttoenergy == 3) {
            			if(world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() == ModBlocks.FLUX_GENERATOR_BLOCK) {
                			TileEntityFluxGenerator te = (TileEntityFluxGenerator)world.getTileEntity(pos.offset(EnumFacing.NORTH));
                			if(BlockEntityFluxPipe.isSideConnectable1(world, pos, EnumFacing.NORTH) == true) {
                				if(te.getFuelValueFromGenerator() > 100) {
	                				te.consumeEnergy(Math.min(100, te.energy));
	                				this.energy += 100;
                				}
                			}
                		}else if(world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() == ModBlocks.FLUX_PIPE_BLOCK) {
                			TileEntityFluxPipe te = (TileEntityFluxPipe)world.getTileEntity(pos.offset(EnumFacing.NORTH));
                			if(this.getFuelValueFromPipe() > 200) {
	                			this.consumeEnergy(Math.min(100, this.energy));
	                			te.energy += 100;
                			}
                		}
            		}else if(counttoenergy == 4) {
            			if(world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() == ModBlocks.FLUX_GENERATOR_BLOCK) {
                			TileEntityFluxGenerator te = (TileEntityFluxGenerator)world.getTileEntity(pos.offset(EnumFacing.SOUTH));
                			if(BlockEntityFluxPipe.isSideConnectable1(world, pos, EnumFacing.SOUTH) == true) {
                				if(te.getFuelValueFromGenerator() > 100) {
	                				te.consumeEnergy(Math.min(100, te.energy));
	                				this.energy += 100;
                				}
                			}
                		}else if(world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() == ModBlocks.FLUX_PIPE_BLOCK) {
                			TileEntityFluxPipe te = (TileEntityFluxPipe)world.getTileEntity(pos.offset(EnumFacing.SOUTH));
                			if(this.getFuelValueFromPipe() > 200) {
	                			this.consumeEnergy(Math.min(100, this.energy));
	                			te.energy += 100;
                			}
                		}
            		}else if(counttoenergy == 5) {
            			if(world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() == ModBlocks.FLUX_GENERATOR_BLOCK) {
            				TileEntityFluxGenerator te = (TileEntityFluxGenerator)world.getTileEntity(pos.offset(EnumFacing.EAST));
                			if(BlockEntityFluxPipe.isSideConnectable1(world, pos, EnumFacing.EAST) == true) {
                				if(te.getFuelValueFromGenerator() > 100) {
	                				te.consumeEnergy(Math.min(100, te.energy));
	                				this.energy += 100;
                				}
                			}
                		}else if(world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() == ModBlocks.FLUX_PIPE_BLOCK) {
                			TileEntityFluxPipe te = (TileEntityFluxPipe)world.getTileEntity(pos.offset(EnumFacing.EAST));
                			if(this.getFuelValueFromPipe() > 200) {
	                			this.consumeEnergy(Math.min(100, this.energy));
	                			te.energy += 100;
                			}
                		}
            		}else if(counttoenergy == 6) {
            			if(world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() == ModBlocks.FLUX_GENERATOR_BLOCK) {
                			TileEntityFluxGenerator te = (TileEntityFluxGenerator)world.getTileEntity(pos.offset(EnumFacing.WEST));
                			if(BlockEntityFluxPipe.isSideConnectable1(world, pos, EnumFacing.WEST) == true) {
                				if(te.getFuelValueFromGenerator() > 100) {
	                				te.consumeEnergy(Math.min(100, te.energy));
	                				this.energy += 100;
                				}
                			}
                		}else if(world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() == ModBlocks.FLUX_PIPE_BLOCK) {
                			TileEntityFluxPipe te = (TileEntityFluxPipe)world.getTileEntity(pos.offset(EnumFacing.WEST));
                			if(this.getFuelValueFromPipe() > 200) {
	                			this.consumeEnergy(Math.min(100, this.energy));
	                			te.energy += 100;
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
	
	@SuppressWarnings("unchecked")
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
		compound.getString("Name");
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