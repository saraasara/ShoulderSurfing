package com.github.exopandora.shouldersurfing.api;

import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import com.github.exopandora.shouldersurfing.api.callback.IAdaptiveItemCallback;

import net.minecraft.world.item.ItemStack;

public interface IShoulderSurfingRegistrar
{
	IShoulderSurfingRegistrar registerAdaptiveItemCallback(IAdaptiveItemCallback adaptiveItemCallback);
	
	default IShoulderSurfingRegistrar registerAdaptiveItemCallback(Predicate<ItemStack> predicate)
	{
		return this.registerAdaptiveItemCallback((minecraft, entity) -> StreamSupport.stream(entity.getHandSlots().spliterator(), false).anyMatch(predicate));
	}
}
