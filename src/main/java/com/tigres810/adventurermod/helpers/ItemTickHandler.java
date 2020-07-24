package com.tigres810.adventurermod.helpers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.ArrayList;
import java.util.List;

import com.tigres810.adventurermod.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemTickHandler
{
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SubscribeEvent
    public static void update(WorldTickEvent event) {
        if(event.phase == Phase.END) {
            List<Entity> entities = new ArrayList(event.world.loadedEntityList);
            entities.forEach((entity) -> {
                if(entity instanceof EntityItem)
                    MinecraftForge.EVENT_BUS.post(new EntityItemTickEvent((EntityItem) entity));
            });
        }
    }
    
    public static class EntityItemTickEvent extends EntityEvent {

        EntityItem entityItem;
        
        public EntityItemTickEvent(EntityItem entity) {
            super(entity);
            entityItem = entity;
        }
        
        public EntityItem getEntityItem() {
            return entityItem;
        }
        
    }
}