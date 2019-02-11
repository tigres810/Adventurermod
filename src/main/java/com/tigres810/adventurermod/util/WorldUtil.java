package com.tigres810.adventurermod.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public final class WorldUtil {

	public static void doEnergyInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing sideTo, int maxTransfer) {
        if (maxTransfer > 0) {
            EnumFacing opp = sideTo == null ? null : sideTo.getOpposite();
                IEnergyStorage handlerFrom = tileFrom.getCapability(CapabilityEnergy.ENERGY, sideTo);
                IEnergyStorage handlerTo = tileTo.getCapability(CapabilityEnergy.ENERGY, opp);
                if (handlerFrom != null && handlerTo != null) {
                    int drain = handlerFrom.extractEnergy(maxTransfer, true);
                    if (drain > 0) {
                        int filled = handlerTo.receiveEnergy(drain, false);
                        handlerFrom.extractEnergy(filled, false);
                        return;
                    }
                }
        }
    }
}
