package com.tigres810.adventurermod.blocks.machines.tileentity;

import com.tigres810.adventurermod.blocks.machines.storages.tileentity.TileEntityFluxStorage;
import com.tigres810.adventurermod.energy.CustomEnergyStorage;
import com.tigres810.adventurermod.energy.IEnergyProvider;
import com.tigres810.adventurermod.init.ModFluids;
import com.tigres810.adventurermod.network.MessageEnergy;
import com.tigres810.adventurermod.network.NetworkNetHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFluxCrafter extends TileEntity implements ITickable, IEnergyProvider {

	public ItemStackHandler handler = new ItemStackHandler(12);
	private CustomEnergyStorage storage = new CustomEnergyStorage(5000, 0, 100);
	public int energy = storage.getEnergyStored();
	private String customName;
	public int cookTime = 0;
	private int previousenergy = energy;
	private int ticks = 0;
	
	public static ItemStack currentfuel;
	public static ItemStack currentcraft;
	
	@Override
	public void update() {
		TileEntityFluxCrafter te = (TileEntityFluxCrafter) this.world.getTileEntity(this.pos);
		if(!world.isRemote) {
			if(this.world.isBlockPowered(this.pos)) {
				if(energy < storage.getMaxEnergyStored()) {
					this.energy += Math.min(100, this.storage.getMaxEnergyStored()-this.energy);
				}
			} else {
			// Furnace handler
				if(!this.handler.getStackInSlot(0).isEmpty()) {
					if(energy > 0) {
						this.cookTime++;
						if(this.cookTime == 25) {
							energy -= 500;
							if(this.handler.getStackInSlot(1).isEmpty()) {
								currentfuel = this.handler.getStackInSlot(0);
								this.handler.setStackInSlot(1, new ItemStack(this.handler.getStackInSlot(0).getItem()));
							} else {
								currentfuel = this.handler.getStackInSlot(1);
								this.handler.insertItem(1, new ItemStack(this.handler.getStackInSlot(0).getItem()), false);
							}
							this.handler.getStackInSlot(0).shrink(1);
							this.cookTime = 0;
							if(energy < 0) energy = 0;
						}
					}
				} else {
					this.cookTime = 0;
				}
			}
			//Crafting handler
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
			//System.out.println(this.energy);
		}
	}
	
	public static boolean isItemOutput(ItemStack stack) {
		if(stack == currentfuel) return true;
		else return false;
	}
	
	public static boolean isItemCraftOutput(ItemStack stack) {
		if(stack == currentcraft) return true;
		else return false;
	}
	
	private boolean isItemFuel(ItemStack stack) 
	{
		return getFuelValue(stack) > 0;
	}
	
	private int getFuelValue(ItemStack stack) 
	{
		if(stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.MILK_BUCKET) return 100;
		else if(stack.getItem() == Items.WHEAT_SEEDS || stack.getItem() == Items.MELON_SEEDS || stack.getItem() == Items.PUMPKIN_SEEDS) return 50;
		else if(stack.getItem() == FluidUtil.getFilledBucket(new FluidStack(ModFluids.FLUX_FLUID, 1)).getItem()) return 200;
		else return 0;
	}
	
	public int getCookTimeValue() {
		return this.energy < this.storage.getMaxEnergyStored() ? this.cookTime : 0;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T)this.handler;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return true;
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("CookTime", this.cookTime);
		compound.setInteger("GuiEnergy", this.energy);
		compound.setString("Name", this.getDisplayName().toString());
		compound.setInteger("Energy", this.storage.getEnergyStored());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.cookTime = compound.getInteger("CookTime");
		this.energy = compound.getInteger("GuiEnergy");
		int t = compound.getInteger("Energy");
		this.customName = compound.getString("Name");
		this.storage = new CustomEnergyStorage(5000, 0, 100, t);
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation("tile.flux_crafter_block.name");
	}
	
	public int getEnergyStored()
	{
		return this.energy;
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
		case 1:
			return this.cookTime;
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
			break;
		case 1:
			this.cookTime = value;
			break;
		}
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
