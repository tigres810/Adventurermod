package com.tigres810.adventurermod.blocks.machines.storages.tileentity;

import com.tigres810.adventurermod.energy.CustomEnergyStorage;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityEnergy extends TileEntity implements ITickable {

	private CustomEnergyStorage storage = new CustomEnergyStorage(5000);
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY) return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public void tick() {
		this.storage.receiveEnergy(100, false);
		this.storage.extractEnergy(20, false);
	}
}
