package com.tigres810.adventurermod.helpers;

import com.tigres810.adventurermod.blocks.machines.BlockEntityFluxConveyor;
import com.tigres810.adventurermod.helpers.ItemTickHandler.EntityItemTickEvent;
import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.util.Reference;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class NewHelper {

public static final String NO_PICKUP_TAG = "advmod_no_collect";
public static final String NO_DESPAWN_TAG = "advmod_no_despawn";
	
@SubscribeEvent
public static void someMethod(EntityItemTickEvent event) {
    World world = event.getEntity().getEntityWorld();
    EntityItem item = event.getEntityItem();
    BlockPos pos = item.getPosition();
    NBTTagCompound nbt = new NBTTagCompound();
    item.writeEntityToNBT(nbt);
    if(world.getBlockState(pos.down()).getBlock() == ModBlocks.FLUX_CONVEYOR_BLOCK) {
        if(nbt.getInteger("PickupDelay") != Short.MAX_VALUE) {
            item.setInfinitePickupDelay();
            item.addTag(NO_PICKUP_TAG);
        }
        if(nbt.getInteger("Age")  != -6000) {
            item.addTag(NO_DESPAWN_TAG);
            item.setNoDespawn();
        }
    }
    else {
        if(item.getTags().contains(NO_PICKUP_TAG)) {
            item.removeTag(NO_PICKUP_TAG);
            item.setDefaultPickupDelay();
        }
        if(item.getTags().contains(NO_DESPAWN_TAG)) {
            item.removeTag(NO_DESPAWN_TAG);
            item.setAgeToCreativeDespawnTime();
        }
    }
}


}
