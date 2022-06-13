package com.nicky.grisha.registry;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class GrishaArmor implements ArmorMaterial {

	private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
	private static final int[] PROTECTION_VALUES = new int[] {1, 4, 5, 1};
	private int color = 0;
	
	public GrishaArmor(int color) {
		this.color = color;
	}
	
	@Override
	public int getDurability(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getEntitySlotId()] * 13;
	}

	@Override
	public int getProtectionAmount(EquipmentSlot slot) {
		return PROTECTION_VALUES[slot.getEntitySlotId()];
	}

	@Override
	public int getEnchantability() {
		return 11;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(GrishaItems.CORE_CLOTH);
	}

	@Override
	public String getName() {
		switch (color) {
		case 1:
			return "kefta_purple";
		case 2:
			return "kefta_red";
		case 3:
			return "kefta_blue";
		default:
			return "kefta";
		}
	}

	@Override
	public float getToughness() {
		// TODO Auto-generated method stub
		return 0.0F;
	}

	@Override
	public float getKnockbackResistance() {
		// TODO Auto-generated method stub
		return 0.0F;
	}

}
