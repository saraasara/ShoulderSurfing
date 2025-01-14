package com.github.exopandora.shouldersurfing.forge.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.exopandora.shouldersurfing.config.Perspective;

import net.minecraft.client.CameraType;
import net.minecraftforge.client.gui.overlay.ForgeGui;

@Mixin(ForgeGui.class)
public class MixinForgeGui
{
	@Redirect
	(
		method = "renderSpyglassOverlay",
		at = @At
		(
			value = "INVOKE",
			target = "net/minecraft/client/CameraType.isFirstPerson()Z"
		)
	)
	private boolean isFirstPerson(CameraType cameraType)
	{
		return cameraType.isFirstPerson() || Perspective.SHOULDER_SURFING.equals(Perspective.current());
	}
}
