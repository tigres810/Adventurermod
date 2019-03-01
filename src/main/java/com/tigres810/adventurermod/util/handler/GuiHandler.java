package com.tigres810.adventurermod.util.handler;

import com.tigres810.adventurermod.blocks.gui.GUIFluxCrafter;
import com.tigres810.adventurermod.blocks.gui.GUIFluxGenerator;
import com.tigres810.adventurermod.blocks.machines.container.ContainerFluxCrafter;
import com.tigres810.adventurermod.blocks.machines.container.ContainerFluxGenerator;
import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxCrafter;
import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxGenerator;
import com.tigres810.adventurermod.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Reference.GUI_FLUX_GENERATOR_BLOCK) return new ContainerFluxGenerator(player.inventory, (TileEntityFluxGenerator)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Reference.GUI_FLUX_CRAFTER_BLOCK) return new ContainerFluxCrafter(player.inventory, (TileEntityFluxCrafter)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Reference.GUI_FLUX_GENERATOR_BLOCK) return new GUIFluxGenerator(player.inventory, (TileEntityFluxGenerator)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Reference.GUI_FLUX_CRAFTER_BLOCK) return new GUIFluxCrafter(player.inventory, (TileEntityFluxCrafter)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}
}
