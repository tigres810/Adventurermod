package com.tigres810.adventurermod.network;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.network.client.TileEntitySyncPacket;
import com.tigres810.adventurermod.network.client.TileEntitySyncPacketHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

	public static void init()
    {

        Main.network = NetworkRegistry.INSTANCE.newSimpleChannel("am");
        Main.network.registerMessage(TileEntitySyncPacketHandler.class, TileEntitySyncPacket.class, 0, Side.CLIENT);
    }
}
