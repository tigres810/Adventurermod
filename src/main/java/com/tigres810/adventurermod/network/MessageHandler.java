package com.tigres810.adventurermod.network;

import com.tigres810.adventurermod.blocks.machines.storages.tileentity.TileEntityFluxStorage;
import com.tigres810.adventurermod.energy.IEnergyProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandler implements IMessageHandler<MessageEnergy, IMessage> {
	  // Do note that the default constructor is required, but implicitly defined in this case

	  @Override public IMessage onMessage(MessageEnergy message, MessageContext ctx) {
	    // The world
	    World world = Minecraft.getMinecraft().world;
	    // The Tile entity
	    IEnergyProvider te = (IEnergyProvider) world.getTileEntity(new BlockPos(message.x, message.y, message.z));
	    // Set the energy in the tile entity
	    te.setEnergy(message.toSend);
	    // No response packet
	    return null;
	  }
	}
