package com.nicky.grisha;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class AmplifierEnchantment extends Enchantment {
	
    public AmplifierEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.VANISHABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    
    @Override
    public int getMinPower(int level) {
        return 1;
    }
    
    @Override
    public int getMaxLevel() {
        return 1;
    }
    
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
     }
    
    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
     }
}
