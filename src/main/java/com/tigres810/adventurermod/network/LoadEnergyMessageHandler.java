package com.tigres810.adventurermod.network;

import com.tigres810.adventurermod.energy.IEnergyProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LoadEnergyMessageHandler implements IMessageHandler<LoadEnergyMessage, IMessage> {
	  // Do note that the default constructor is required, but implicitly defined in this case

	  @Override public IMessage onMessage(LoadEnergyMessage message, MessageContext ctx) {
		  // Player
		  EntityPlayer sender = ctx.getServerHandler().player;
		  // The world
		  World playerworld = sender.world;
		  // Tile Entity
		  IEnergyProvider te = (IEnergyProvider) playerworld.getTileEntity(new BlockPos(message.x, message.y, message.z));
		  // Set the energy in the tile entity
		  if(te == null) return null;
		  
		  return new MessageEnergy(te.getEnergy(), new BlockPos(message.x, message.y, message.z));
	  }
	}
