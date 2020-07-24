package com.tigres810.adventurermod.blocks.machines.storages.tileentity;

import java.util.List;

import com.tigres810.adventurermod.blocks.machines.BlockEntityFluxConveyor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityFluxConveyor extends TileEntity implements ITickable
{

	@Override
	public void update() {
		List<EntityItem> stuff = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 2, 1)));
		IBlockState blockstate = world.getBlockState(pos);
		EnumFacing facing = blockstate.getValue(BlockEntityFluxConveyor.FACING);
		
		for (EntityItem item : stuff) {
			moveTo(item, pos.offset(facing.getOpposite()));
		}
	}
	
	private static void moveTo(Entity entity, BlockPos target) {
        float ix = (float) entity.posX;
        float iy = (float) entity.posY;
        float iz = (float) entity.posZ;
        float tx = (float) (target.getX()+0.5);
        float ty = target.getY();
        float tz = (float) (target.getZ()+0.5);
        if(ix!=tx) {
            if(ix>tx) entity.motionX=-0.05D;
            else if(ix<tx) entity.motionX=0.05D;
        }
        if(iy!=ty) {
            if(iy>ty) entity.motionY=-0.05D;
            else if(iy<ty) entity.motionY=0.05D;
        }
        if(iz!=tz) {
            if(iz>tz) entity.motionZ=-0.05D;
            else if(iz<tz) entity.motionZ=0.05D;
        }
    }
    
    

}