package com.tigres810.adventurermod.blocks.machines.storages.tileentity;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.energy.CustomEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFluxStorage extends TileEntity implements ITickable {
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(10000, 0, 100);
	public int energy = storage.getEnergyStored();

	@Override
	public void update() {
		if(!world.isRemote) {
			if(this.world.isBlockPowered(this.pos)) {
				this.energy += Math.min(10, this.storage.getMaxEnergyStored()-this.energy);
			}
		}
		else {
			System.out.println(this.energy);
		}
	}
	
	public void consumeEnergy(int amount) {
    	this.energy -= amount;
    }
    
    public int getFuelValueFromStorage() 
	{
		return this.energy;
	}
    
    @Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
		//if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T)this.handler;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return true;
		//if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		//compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("GuiEnergy", this.energy);
		compound.setInteger("Energy", this.storage.getEnergyStored());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		//this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		int t = compound.getInteger("Energy");
		this.storage = new CustomEnergyStorage(1000, 0, 100, t);
	}
	
	/*
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation("tile.flux_generator_block.name");
	}
	*/
	
	public int getEnergyStored()
	{
		return this.energy;
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

}