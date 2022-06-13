package com.nicky.grisha.registry;

import com.nicky.grisha.AmplifierEnchantment;
import com.nicky.grisha.Grisha;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GrishaEnchantments{

	
	public static Enchantment AMPLIFIER = new AmplifierEnchantment();
	
	public static void registerEnchantments() {
		Registry.register(Registry.ENCHANTMENT,new Identifier(Grisha.MOD_ID, "amplifier"), AMPLIFIER);
	}

}
