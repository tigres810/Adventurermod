package com.tigres810.adventurermod.network.client;



import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class TileEntitySyncPacketHandler implements IMessageHandler<TileEntitySyncPacket, IMessage> 
{



    @Override
    public IMessage onMessage(TileEntitySyncPacket message, MessageContext ctx) 
    {
        IThreadListener mainThread = Minecraft.getMinecraft();

        mainThread.addScheduledTask(new Runnable() {

            @Override
            public void run() {

                NBTTagCompound data = message.data;
                BlockPos pos = new BlockPos(data.getInteger("x"), data.getInteger("y"), data.getInteger("z"));
                NBTTagCompound tileData = data.getCompoundTag("data");

                TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(pos);

                if(tile != null)
                {
                    tile.readFromNBT(tileData);
                }

            }});

        return null;
    }

}
