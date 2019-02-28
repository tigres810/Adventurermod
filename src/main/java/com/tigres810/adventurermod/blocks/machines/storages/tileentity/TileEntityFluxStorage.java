package com.tigres810.adventurermod.blocks.machines.storages.tileentity;

import java.util.UUID;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.blocks.machines.BlockEntityFluxGenerator;
import com.tigres810.adventurermod.blocks.machines.storages.BlockEntityFluxStorage;
import com.tigres810.adventurermod.energy.CustomEnergyStorage;
import com.tigres810.adventurermod.energy.IEnergyProvider;
import com.tigres810.adventurermod.init.ModFluids;
import com.tigres810.adventurermod.network.MessageEnergy;
import com.tigres810.adventurermod.network.NetworkNetHandler;
import com.tigres810.adventurermod.util.handler.RegistryHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFluxStorage extends TileEntity implements ITickable, IEnergyProvider {
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(10000, 0, 100);
	public int energy = this.storage.getEnergyStored();
	private int previousenergy = energy;
	private int ticks = 0;

	@Override
	public void update() {
		TileEntityFluxStorage te = (TileEntityFluxStorage) this.world.getTileEntity(this.pos);
		if(!world.isRemote) {
			if(this.world.isBlockPowered(this.pos)) {
				if(energy < storage.getMaxEnergyStored()) {
					this.energy += Math.min(100, this.storage.getMaxEnergyStored()-this.energy);
				}
			}
			// Server side
			if(this.ticks < 5) {
				this.ticks++;
			} else {
				this.ticks = 0;
				if(this.previousenergy != energy) {
					this.previousenergy = this.energy;
					NetworkNetHandler.INSTANCE.sendToAllTracking(new MessageEnergy(this.energy, this.pos), new TargetPoint(this.world.provider.getDimension(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 5));
				}
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
	public int getEnergy() {
		return this.energy;
	}

	@Override
	public void setEnergy(int amount) {
		this.energy = amount;
	}

}