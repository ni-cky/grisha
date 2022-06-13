package com.nicky.grisha.damage_source;

import net.minecraft.entity.damage.DamageSource;

public class JurdaParemAddictionDamageSource extends DamageSource{

	public JurdaParemAddictionDamageSource() {
		super("jurdaParemAddiction");
		setBypassesArmor();
		setUnblockable();
	}

}
