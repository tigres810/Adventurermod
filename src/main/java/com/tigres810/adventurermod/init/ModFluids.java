package com.tigres810.adventurermod.init;

import java.util.ArrayList;
import java.util.List;

import com.tigres810.adventurermod.fluids.FluidLiquid;
import com.tigres810.adventurermod.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {

	public static final List<Fluid> FLUIDS = new ArrayList<Fluid>();
	
	public static final Fluid FLUX_FLUID = new FluidLiquid("flux_fluid", new ResourceLocation(Reference.MOD_ID + ":blocks/flux_fluid_still"), new ResourceLocation(Reference.MOD_ID + ":blocks/flux_fluid_flow"));
	
	public static void registerFluids() {
		registerFluid(FLUX_FLUID);
	}
	
	public static void registerFluid(Fluid fluid) {
		
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}
}
