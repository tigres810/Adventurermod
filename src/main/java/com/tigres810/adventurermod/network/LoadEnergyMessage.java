package com.tigres810.adventurermod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class LoadEnergyMessage implements IMessage {
	  // A default constructor is always required
	  public LoadEnergyMessage(){}

	  int x;
	  int y;
	  int z;
	  public LoadEnergyMessage(BlockPos pos) {
	    this.x = pos.getX();
	    this.y = pos.getY();
	    this.z = pos.getZ();
	  }

	  @Override public void toBytes(ByteBuf buf) {
	    // Writes the int into the buf
	    buf.writeInt(x);
	    buf.writeInt(y);
	    buf.writeInt(z);
	  }

	  @Override public void fromBytes(ByteBuf buf) {
	    // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
	    x = buf.readInt();
	    y = buf.readInt();
	    z = buf.readInt();
	  }
	}
