package com.nicky.grisha;

import java.util.Random;

import com.nicky.grisha.registry.GrishaEnchantments;
import com.nicky.grisha.registry.GrishaItems;
import com.nicky.grisha.registry.GrishaThrownEntity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GrishaSmallScience {

	protected static final Random RANDOM = new Random();
	protected static double amplifierProbability = 0.02;
	private static final Item[] materialki = {GrishaItems.KEFTA_PURPLE_BOOTS, GrishaItems.KEFTA_PURPLE_LEGGINGS, GrishaItems.KEFTA_PURPLE_CHESTPLATE, GrishaItems.KEFTA_PURPLE_HOOD};
	private static final Item[] etherealki = {GrishaItems.KEFTA_BLUE_BOOTS, GrishaItems.KEFTA_BLUE_LEGGINGS, GrishaItems.KEFTA_BLUE_CHESTPLATE, GrishaItems.KEFTA_BLUE_HOOD};
	private static final Item[] corporalki = {GrishaItems.KEFTA_RED_BOOTS, GrishaItems.KEFTA_RED_LEGGINGS, GrishaItems.KEFTA_RED_CHESTPLATE, GrishaItems.KEFTA_RED_HOOD};
	
	private static final int amplifierMod = 2;

	public static boolean isWearingMaterialki(PlayerEntity player) {
		Iterable<ItemStack>  iterator = player.getArmorItems();
    	int count = 0;
    	
    	//Test for Materialki
    	for (ItemStack i : iterator){
    		if(i.isItemEqualIgnoreDamage(new ItemStack(materialki[0]))) {
    			count++;
    		}
    		if(i.isItemEqualIgnoreDamage(new ItemStack(materialki[1]))) {
    			count++;
    		}
    		if(i.isItemEqualIgnoreDamage(new ItemStack(materialki[2]))) {
    			count++;
    		}
    		if(i.isItemEqualIgnoreDamage(new ItemStack(materialki[3]))) {
    			count++;
    		}
    	}
    	return count == 4;
	}
	
	public static boolean isWearingEtherealki(PlayerEntity player) {
		Iterable<ItemStack>  iterator = player.getArmorItems();
    	int count = 0;
    	
    	//Test for Materialki
    	for (ItemStack i : iterator){
    		if(i.isItemEqualIgnoreDamage(new ItemStack(etherealki[0]))) {
    			count++;
    		}
    		if(i.isItemEqualIgnoreDamage(new ItemStack(etherealki[1]))) {
    			count++;
    		}
    		if(i.isItemEqualIgnoreDamage(new ItemStack(etherealki[2]))) {
    			count++;
    		}
    		if(i.isItemEqualIgnoreDamage(new ItemStack(etherealki[3]))) {
    			count++;
    		}
    	}
    	return count == 4;
	}
	
	public static boolean isWearingCorporalki(PlayerEntity player) {
		Iterable<ItemStack>  iterator = player.getArmorItems();
    	int count = 0;
    	
    	//Test for Materialki
    	for (ItemStack i : iterator){
    		if(i.isItemEqualIgnoreDamage(new ItemStack(corporalki[0]))) {
    			count++;
    		}
    		if(i.isItemEqualIgnoreDamage(new ItemStack(corporalki[1]))) {
    			count++;
    		}
    		if(i.isItemEqualIgnoreDamage(new ItemStack(corporalki[2]))) {
    			count++;
    		}
    		if(i.isItemEqualIgnoreDamage(new ItemStack(corporalki[3]))) {
    			count++;
    		}
    	}
    	return count == 4;
	}
		
	public static void ACTIVATE_MATERIALKI(PlayerEntity player) {
		if(player.world.isClient()) {
			//Client
			if(ItemStack.areEqual(player.getStackInHand(Hand.MAIN_HAND), (new ItemStack(Items.AIR,1)))) {
				
			}else {
				
			}
		}else {
			//Server
			if(ItemStack.areEqual(player.getStackInHand(Hand.MAIN_HAND), (new ItemStack(Items.AIR,1)))) {
				player.openHandledScreen(GrishaSmallScienceUtil.createScreenHandlerFactory(player.world,player.getBlockPos()));
					removeEnergy(player,1);
			}else {
				if(throwsItem(player.getEntityWorld(), player, Hand.MAIN_HAND).getResult().isAccepted())
						removeEnergy(player,1);
			}
		}
	}
	
	public static void ACTIVATE_ETHEREALKI(PlayerEntity player) {
		System.out.println(player.world.isClient());
		if(player.world.isClient()) {
			//Client
			if(ItemStack.areEqual(player.getStackInHand(Hand.MAIN_HAND), (new ItemStack(Items.AIR,1)))) {
				etherealkiNeutral(player).isAccepted();
					
			}else {
				
			}
		}else {
			//Server
			if(ItemStack.areEqual(player.getStackInHand(Hand.MAIN_HAND), (new ItemStack(Items.AIR,1)))) {
				player.world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PHANTOM_FLAP, SoundCategory.NEUTRAL, 0.4F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
				removeEnergy(player,1);
			}else if(ItemStack.areItemsEqualIgnoreDamage(player.getStackInHand(Hand.MAIN_HAND), (new ItemStack(Items.FLINT_AND_STEEL,1)))){
				etherealkiActive(player);
				player.world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, SoundCategory.NEUTRAL, 0.5F, 0.6F / (RANDOM.nextFloat() * 0.4F + 0.8F));
				removeEnergy(player,4);
			}
		}
	}
	
	public static void ACTIVATE_CORPORALKI(PlayerEntity player) {
		if(player.world.isClient()) {
			//Client
			if(ItemStack.areEqual(player.getStackInHand(Hand.MAIN_HAND), (new ItemStack(Items.AIR,1)))) {
				
			}else {
				
			}
		}else {
			//Server
			if(ItemStack.areEqual(player.getStackInHand(Hand.MAIN_HAND), (new ItemStack(Items.AIR,1)))) {			
				corporalkiNeutral(player);
			}else {
				//corpoalkiActive() is being triggered in @Grisha.java via attack event
			}
		}
	}
	
	//Materialki Active
	public static TypedActionResult<ItemStack> throwsItem(World world, PlayerEntity user, Hand hand) {
		ItemCooldownManager x = user.getItemCooldownManager();
		System.out.println(x.isCoolingDown(user.getStackInHand(Hand.MAIN_HAND).getItem()));
		ItemStack itemStack = user.getStackInHand(hand);
		if(!x.isCoolingDown(user.getStackInHand(Hand.MAIN_HAND).getItem())) {
			if (!GrishaSmallScienceUtil.isOnParem(user))
				x.set(user.getStackInHand(Hand.MAIN_HAND).getItem(), 5);	
			world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
			if (!world.isClient) {
				int damage = 4;
				float speed = 1.5f;
				float divergence = 1.0F;
				if(GrishaSmallScienceUtil.holdsAmplifier(user))
					speed*= amplifierMod;
				if(GrishaSmallScienceUtil.isOnParem(user)) {
					speed *= 2;
					damage *= 2;
					divergence = 0;
				}
				GrishaThrownEntity grishaEntity = new GrishaThrownEntity(world, user, user.getStackInHand(Hand.MAIN_HAND),damage);
				grishaEntity.setItem(itemStack);
				grishaEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, speed, divergence);
				world.spawnEntity(grishaEntity);
				grishaEntity.createSpawnPacket();
			}
			if (!user.getAbilities().creativeMode) {
				itemStack.decrement(1);
			}
			return TypedActionResult.success(itemStack, world.isClient());
		}else {
			return TypedActionResult.fail(itemStack);
		}
	}
	
	public static ActionResult etherealkiNeutral(PlayerEntity player) {
		if(player.isOnGround()) {
			float acceleration = 1.5f;
			if(GrishaSmallScienceUtil.holdsAmplifier(player))
				acceleration = 3f;
			float f = 0.42F * acceleration;
			Vec3d vec3d = player.getVelocity();
			player.setVelocity(vec3d.x, (double)f, vec3d.z);
			if (player.isSprinting()) {
				float g = player.getYaw() * 0.017453292F;
				player.setVelocity(player.getVelocity().add((double)(-MathHelper.sin(g) * 0.2F), 0.0D, (double)(MathHelper.cos(g) * 0.2F)));
			}

			player.velocityDirty = true;
			if(GrishaSmallScienceUtil.isOnParem(player))
				player.setNoGravity(true);
				player.setOnGround(false);
			return ActionResult.SUCCESS;
		}else {
			Vec3d x = player.getRotationVector();
			x = x.multiply(2);
			if(player.isSneaking())
				x = x.negate();
			player.addVelocity(x.x, x.y, x.z);
			player.velocityDirty = true;
			return ActionResult.SUCCESS;
		}
	}
	
	public static void etherealkiActive(PlayerEntity player) {
		ItemCooldownManager x = player.getItemCooldownManager();
		if(!x.isCoolingDown(player.getStackInHand(Hand.MAIN_HAND).getItem())) {
			x.set(player.getStackInHand(Hand.MAIN_HAND).getItem(), 3);
			Vec3d cameraRot = player.getRotationVector();
			int damage = 1;
			if(GrishaSmallScienceUtil.holdsAmplifier(player))
				damage*= amplifierMod;
			if(GrishaSmallScienceUtil.isOnParem(player)) {
				damage *= 2;
			}
			FireballEntity fireballEntity = new FireballEntity(player.world, player, 0, 0, 0, damage);
	        fireballEntity.setPosition(player.getX() + cameraRot.x * 4.0D, player.getBodyY(0.5D), player.getZ() + cameraRot.z * 4.0D);
	        player.world.spawnEntity(fireballEntity);
	        player.getStackInHand(Hand.MAIN_HAND).damage(damage, player, (entity)->{
	        	entity.sendToolBreakStatus(Hand.MAIN_HAND);
	        });
		}
	}
	
	public static void corporalkiActive(LivingEntity attacker, LivingEntity entity) {
		System.out.println("ACTIVE!");
		int i = GrishaSmallScienceUtil.holdsAmplifier(attacker) ? 2 : 1;

		StatusEffect effect = StatusEffects.INSTANT_DAMAGE;
		effect.applyInstantEffect(attacker, attacker, entity, i, 1);

		System.out.println("World - corporalkiActive:"+entity.world.isClient());
		if(entity.world.isClient())
		for(int i1 = 0; i1 < 8; ++i1) {
			int i2 = 3840;
			double d = (double)(i2 >> 16 & 255) / 255.0D;
			double e = (double)(i2 >> 8 & 255) / 255.0D;
			double f = (double)(i2 >> 0 & 255) / 255.0D;
			entity.world.addParticle(ParticleTypes.EFFECT,entity.getParticleX(0.5D), entity.getRandomBodyY(), entity.getParticleZ(0.5D), d, e, f);
		}
	}
	
	public static ActionResult corporalkiNeutral(PlayerEntity player) {
		if(!player.world.isClient()) {
			TypedActionResult<Entity> x = GrishaSmallScienceUtil.grishaRaycast(player,4.5f,4.5);
			LivingEntity y = player;
			if(x.getResult().isAccepted()) {
				if(x.getValue().isLiving()) {
					y = (LivingEntity) x.getValue();
				}
			}
			int i = GrishaSmallScienceUtil.isOnParem(player) ? 10 : 1;
			i = GrishaSmallScienceUtil.holdsAmplifier(player) ? 2 : i;
			y.addStatusEffect((new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, i)));
			System.out.println("World - corporalkiNeutral:"+y.world.isClient());
			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeInt(y.getId());
			ServerPlayNetworking.send((ServerPlayerEntity) player, GrishaClient.ACTIVATE_CORPORALKI_PARTICLES_PACKET_ID, buf);
			return ActionResult.success(true);
		}
		return ActionResult.FAIL;
	}
	
	public static void corporalkiParticles(PlayerEntity player, LivingEntity y) {
		System.out.println("Particles!");
		for(int i1 = 0; i1 < 8; ++i1) {
			int i2 = 3840;
			double d = (double)(i2 >> 16 & 255) / 255.0D;
			double e = (double)(i2 >> 8 & 255) / 255.0D;
			double f = (double)(i2 >> 0 & 255) / 255.0D;
			System.out.println("Position:"+y.getParticleX(0.5D)+","+y.getRandomBodyY()+","+y.getParticleZ(0.5D));
			player.world.addParticle(ParticleTypes.HEART,y.getParticleX(0.5D), y.getRandomBodyY(), y.getParticleZ(0.5D), d, e, f);
		}
	}
	
	public static double getAmplifierModifier(PlayerEntity player) {
		return GrishaSmallScienceUtil.holdsAmplifier(player)? amplifierMod :0;
	}
	
	public static void removeEnergy(PlayerEntity player, float amount) {
		if (!player.getAbilities().creativeMode) {
			HungerManager x = player.getHungerManager();
			float f = 0;
			float h = -amount;
			if(x.getFoodLevel() > 0) {
				if(x.getSaturationLevel() > 0f) {
					f = -h;
					h = h/2;
				}
				
				if(x.getFoodLevel()+(h-0.5f) > 0)
					if(x.getSaturationLevel()+f > 0)
						x.add((int)(h-0.5f), f);
					else {
						x.add((int)(h-0.5f), 0);
						x.setSaturationLevel(0);
					}
				else
					if(x.getSaturationLevel()+f > 0) {
						x.add(0, f);
						x.setFoodLevel(0);
					}else {
						x.setFoodLevel(0);
						x.setSaturationLevel(0);
					}
						
				System.out.println(x.getFoodLevel() + ", " + x.getSaturationLevel() + "["+f);
			}else {
				player.damage(DamageSource.STARVE, amount);
			}
		}
	}
	
	public static void enchantAmplifier(PlayerEntity player) {
		ItemStack x = player.getStackInHand(Hand.OFF_HAND);
		if(x.getCount()==1) {
			if(!x.hasEnchantments()) {
				if(RANDOM.nextDouble() < amplifierProbability)
					x.addEnchantment(GrishaEnchantments.AMPLIFIER, 1);
			}else {
				System.out.println("f");
			}
		}
	}
	
	public static void amplifierSickness(PlayerEntity player) {
		player.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 80, 10)));
		player.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 10)));
	}
}
