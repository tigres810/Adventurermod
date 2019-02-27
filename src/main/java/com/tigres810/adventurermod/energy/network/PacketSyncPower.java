package com.tigres810.adventurermod.energy.network;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.energy.IEnergyContainer;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncPower implements IMessage {

	private int energy;
	
	@Override
	public void fromBytes(ByteBuf buf) { energy = buf.readInt(); }

	@Override
	public void toBytes(ByteBuf buf) { buf.writeInt(energy); }
	
	public PacketSyncPower(int energy) { this.energy = energy; }
	
	public static class Handler implements IMessageHandler<PacketSyncPower, IMessage> {

		@Override
		public IMessage onMessage(PacketSyncPower message, MessageContext ctx) {
			Main.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PacketSyncPower message, MessageContext ctx) {
			EntityPlayer player = Main.proxy.getClientPlayer();
			if(player instanceof EntityPlayerMP) {
				((IEnergyContainer) player).syncEnergy(message.energy);
			}
		}
		
	}

}
