package com.tigres810.adventurermod.blocks.machines.storages.tileentity;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.blocks.machines.BlockEntityFluxGenerator;
import com.tigres810.adventurermod.blocks.machines.storages.BlockEntityFluxStorage;
import com.tigres810.adventurermod.energy.CustomEnergyStorage;
import com.tigres810.adventurermod.energy.IEnergyContainer;
import com.tigres810.adventurermod.energy.network.Messages;
import com.tigres810.adventurermod.energy.network.PacketSyncPower;
import com.tigres810.adventurermod.init.ModFluids;
import com.tigres810.adventurermod.util.handler.RegistryHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFluxStorage extends TileEntity implements ITickable, IEnergyContainer {
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(10000, 0, 100);
	public int energy = this.storage.getEnergyStored();
	private int clientenergy = -1;

	@Override
	public void update() {
		if(!world.isRemote) {
			if(this.world.isBlockPowered(this.pos)) {
				if(energy < storage.getMaxEnergyStored()) {
					TileEntityFluxStorage te = (TileEntityFluxStorage) this.world.getTileEntity(this.pos);
					this.energy += Math.min(10, this.storage.getMaxEnergyStored()-this.energy);
					
					if(te.getEnergy() != te.getClientEnergy()) {
						EntityPlayerMP player = BlockEntityFluxStorage.player;
						te.setClientEnergy(te.getEnergy());
						Messages.INSTANCE.sendTo(new PacketSyncPower(te.getEnergy()), player);
					}
				}
			}
		}
		else {
			//System.out.println(this.clientenergy);
		}
	}
	
	public void consumeEnergy(int amount) {
    	this.energy -= amount;
    }
    
    public int getFuelValueFromStorage() 
	{
		return this.energy;
	}
    
    public int getEnergy() {
    	return storage.getEnergyStored();
    }
    
    public int getClientEnergy() {
    	return clientenergy;
    }
    
    public void setClientEnergy(int clientEnergy) {
    	this.clientenergy = clientEnergy;
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
		compound.setInteger("Energy", this.storage.getEnergyStored());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
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

	@Override
	public void syncEnergy(int energy) {
		TileEntityFluxStorage te = (TileEntityFluxStorage) this.world.getTileEntity(this.pos);
		te.setClientEnergy(energy);
	}

}