package com.tigres810.adventurermod.blocks.machines.container;

import com.tigres810.adventurermod.blocks.machines.container.slots.CustomSlotCrafting;
import com.tigres810.adventurermod.blocks.machines.container.slots.CustomSlotFurnace;
import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxCrafter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFluxCrafter extends Container {
	private final TileEntityFluxCrafter tileentity;
	private int energy, cooktime;
	
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	
	public ContainerFluxCrafter(InventoryPlayer player, TileEntityFluxCrafter tileentity) {
		this.tileentity = tileentity;
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 16, 22));
		this.addSlotToContainer(new CustomSlotFurnace(handler, 1, 68, 22));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 105, 26));
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 123, 26));
		this.addSlotToContainer(new SlotItemHandler(handler, 4, 141, 26));
		this.addSlotToContainer(new SlotItemHandler(handler, 5, 105, 44));
		this.addSlotToContainer(new SlotItemHandler(handler, 6, 123, 44));
		this.addSlotToContainer(new SlotItemHandler(handler, 7, 141, 44));
		this.addSlotToContainer(new SlotItemHandler(handler, 8, 105, 62));
		this.addSlotToContainer(new SlotItemHandler(handler, 9, 123, 62));
		this.addSlotToContainer(new SlotItemHandler(handler, 10, 141, 62));
		this.addSlotToContainer(new CustomSlotCrafting(handler, 11, 123, 6));
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void updateProgressBar(int id, int data) {
		this.tileentity.setField(id, data);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			if(this.energy != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
			if(this.cooktime != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));
		}
		
		this.energy = this.tileentity.getField(0);
		this.cooktime = this.tileentity.getField(1);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index >= 0 && index < 27) {
				if(!this.mergeItemStack(stack1, 27, 36, false)) return ItemStack.EMPTY;
			}
			else if(index >= 27 && index < 36) {
				if(!this.mergeItemStack(stack1, 0, 27, false)) return ItemStack.EMPTY;
			}
			else if(!this.mergeItemStack(stack1, 0, 36, false)) {
				return ItemStack.EMPTY;
			}
			
			if(stack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();
			
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		
		return stack;
	}
}