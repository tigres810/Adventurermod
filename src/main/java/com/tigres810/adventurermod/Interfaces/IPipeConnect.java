package com.tigres810.adventurermod.Interfaces;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public interface IPipeConnect {
	
	public List<EnumFacing> getConnectableSides(IBlockState state);
}
