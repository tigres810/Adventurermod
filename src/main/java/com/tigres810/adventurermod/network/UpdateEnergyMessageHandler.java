package com.tigres810.adventurermod.network;

import com.tigres810.adventurermod.energy.IEnergyProvider;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateEnergyMessageHandler implements IMessageHandler<MessageEnergy, IMessage> {
	  // Do note that the default constructor is required, but implicitly defined in this case

	  @Override public IMessage onMessage(MessageEnergy message, MessageContext ctx) {
	    // The world
		WorldClient worldClient = FMLClientHandler.instance().getWorldClient();
	    // The Tile entity
	    IEnergyProvider te = (IEnergyProvider) worldClient.getTileEntity(new BlockPos(message.x, message.y, message.z));
	    if(te == null) return null;
	    // Set the energy in the tile entity
	    te.setEnergy(message.toSend);
	    // No response packet
	    return null;
	  }
	}
