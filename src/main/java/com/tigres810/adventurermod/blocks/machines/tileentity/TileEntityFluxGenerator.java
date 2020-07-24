package com.tigres810.adventurermod.blocks.machines.tileentity;

import com.tigres810.adventurermod.energy.CustomEnergyStorage;
import com.tigres810.adventurermod.init.ModFluids;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;


public class TileEntityFluxGenerator extends TileEntity implements ITickable
{
	public ItemStackHandler handler = new ItemStackHandler(1);
	private CustomEnergyStorage storage = new CustomEnergyStorage(1000, 0, 100);
	public int energy = storage.getEnergyStored();
	public int cookTime;
	
    @Override
    public void update()
    {
    	if(!this.world.isRemote) {
	    	if(!this.handler.getStackInSlot(0).isEmpty() && isItemFuel(this.handler.getStackInSlot(0)))
			{
	    		if(this.energy < this.storage.getMaxEnergyStored()) {
				this.cookTime++;
				if(this.cookTime == 25)
				{
					//System.out.println(handler.getStackInSlot(0).getItem().getUnlocalizedName());
					this.energy += getFuelValue(this.handler.getStackInSlot(0));
					if(this.handler.getStackInSlot(0).getItem().getUnlocalizedName().equals(Items.WATER_BUCKET.getUnlocalizedName()) || this.handler.getStackInSlot(0).getItem().getUnlocalizedName().equals(Items.MILK_BUCKET.getUnlocalizedName()) || this.handler.getStackInSlot(0).getItem().getUnlocalizedName().equals(FluidUtil.getFilledBucket(new FluidStack(ModFluids.FLUX_FLUID, 1)).getItem().getUnlocalizedName())) {
						this.handler.setStackInSlot(0, new ItemStack(Items.BUCKET));
					} else {
						this.handler.getStackInSlot(0).shrink(1);
					}
					this.cookTime = 0;
					this.markDirty();
				}
				} else {
					this.energy = 1000;
					this.cookTime = 0;
				}
			}
    	}
    }
    
    public void consumeEnergy(int amount) {
    	this.energy -= amount;
    }
    
    public int getFuelValueFromGenerator() 
	{
		return this.energy;
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

	@SuppressWarnings("unchecked")
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
		compound.getString("Name");
		this.storage = new CustomEnergyStorage(1000, 0, 100, t);
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation("tile.flux_generator_block.name");
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

}