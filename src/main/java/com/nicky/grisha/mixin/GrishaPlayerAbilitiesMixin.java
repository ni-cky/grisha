package com.nicky.grisha.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.nicky.grisha.mixin_util.PlayerAbilitiesExt;

import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.nbt.NbtCompound;

@Mixin(PlayerAbilities.class)
public class GrishaPlayerAbilitiesMixin implements PlayerAbilitiesExt{
	
	public boolean consumedParem = false;
	
	public boolean getConsumedParem() {
		return consumedParem;
	}
	
	public void setConsumedParem(boolean cons) {
		consumedParem = cons;
	}
	
	@Inject(method = "writeNbt", at = @At("RETURN"))
	public void writeNbt(NbtCompound nbtCompound,CallbackInfo ci) {
		nbtCompound.putBoolean("consumed_parem", this.consumedParem);
	}
	
	@Inject(method = "readNbt", at = @At("RETURN"))
	public void readNbt(NbtCompound nbt, CallbackInfo ci) {
	        this.consumedParem = nbt.getBoolean("consumed_parem");
	}
}
