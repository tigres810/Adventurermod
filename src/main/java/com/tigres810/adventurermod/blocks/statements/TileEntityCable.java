package com.tigres810.adventurermod.blocks.statements;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityCable extends TileEntity {
	
	public EnumFacing[] connections = new EnumFacing[6];
	public TileEntityCable() {
		
	}
	
	public void updateConnections() {
		
	}
}
