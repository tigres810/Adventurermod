package com.tigres810.adventurermod.network;

import com.tigres810.adventurermod.util.Reference;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkNetHandler {

	public final static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
	
	public static void init() {
		INSTANCE.registerMessage(UpdateEnergyMessageHandler.class, MessageEnergy.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(LoadEnergyMessageHandler.class, LoadEnergyMessage.class, 0, Side.SERVER);
	}
}
