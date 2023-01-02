package com.nicky.grisha;

import com.nicky.grisha.blocks.MateralkiCraftingTable;
import com.nicky.grisha.entity.MateralkiCraftingTableEntity;
import com.nicky.grisha.entity.StorageBlockEntity;
import com.nicky.grisha.gui.GrishaCraftingController;
import com.nicky.grisha.gui.GrishaSmeltingController;
import com.nicky.grisha.items.JurdaParemItem;
import com.nicky.grisha.items.bag.ui.BagScreenHandler;
import com.nicky.grisha.mixin_util.PlayerAbilitiesExt;
import com.nicky.grisha.recipe.GrishaMaterialkiCraftingRecipe;
import com.nicky.grisha.recipe.GrishaMaterialkiCraftingRecipeSerializer;
import com.nicky.grisha.registry.GrishaBlocks;
import com.nicky.grisha.registry.GrishaEnchantments;
import com.nicky.grisha.registry.GrishaItems;
import com.nicky.grisha.registry.GrishaThrownEntity;
import com.nicky.grisha.status_effects.JurdaParem;
import com.nicky.grisha.status_effects.JurdaParemAddictionPhase0;
import com.nicky.grisha.status_effects.JurdaParemAddictionPhase1;
import com.nicky.grisha.status_effects.JurdaParemAddictionPhase2;
import com.nicky.grisha.status_effects.JurdaParemAddictionPhase3;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

import java.util.List;


public class Grisha implements ModInitializer{

	public static final String MOD_ID = "grisha";
	
	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(
			new Identifier(MOD_ID, "general"))
			.icon(() -> new ItemStack(GrishaItems.PEBBLE))
			.build();
	
	public static final Identifier ACTIVATE_MATERIALKI_PACKET_ID = new Identifier(MOD_ID, "materialki");
	public static final Identifier ACTIVATE_ETHEREALKI_PACKET_ID = new Identifier(MOD_ID, "etherealki");
	public static final Identifier ACTIVATE_CORPORALKI_PACKET_ID = new Identifier(MOD_ID, "corporalki");
	public static final Identifier ACTIVATE_CORPORALKI_PAREM_PACKET_ID = new Identifier(MOD_ID, "corporalki_parem");
	public static final Identifier SHOW_PAREM_ADDICTION_PACKET_ID = new Identifier(MOD_ID, "show_parem_addiction");
	public static final Identifier AMPLIFIER_SICKNESS_PACKET_ID = new Identifier(MOD_ID, "sickness");
	public static final Identifier INCREASE_JURDA_PAREM_STAT_PACKET_ID = new Identifier(MOD_ID, "increase_jurda_parem_stat");
	public static final Identifier START_PAREM_ADDICTION_PACKET_ID = new Identifier(MOD_ID, "start_parem_addiction");
    
    // a public identifier for multiple parts of our bigger chest
	public static final Identifier MATERIALKI_CRAFTING_ID = new Identifier(MOD_ID,"materialki_crafting");
	public static final Identifier MATERIALKI_SMELTING_ID = new Identifier(MOD_ID,"materialki_smelting");
	public static ScreenHandlerType<GrishaCraftingController> MATERIALKI_SCREEN_HANDLER;
	public static ScreenHandlerType<GrishaSmeltingController> MATERIALKI_SMELTING_SCREEN_HANDLER;
	public static final RecipeType<GrishaMaterialkiCraftingRecipe> MATERIALKI_CRAFTING_RECIPE_TYPE;
	public static final RecipeSerializer<GrishaMaterialkiCraftingRecipe> MATERIALKI_CRAFTING_RECIPE_SERIALIZER;
	
	public static final ScreenHandlerType<BagScreenHandler> BAG_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID,"cloth_bag_screen"), BagScreenHandler::new);
	
	public static final StatusEffect JURDA_PAREM_EFFECT = new JurdaParem();
	public static final StatusEffect JURDA_PAREM_ADDICTION_PHASE0 = new JurdaParemAddictionPhase0();
	public static final StatusEffect JURDA_PAREM_ADDICTION_PHASE1 = new JurdaParemAddictionPhase1();
	public static final StatusEffect JURDA_PAREM_ADDICTION_PHASE2 = new JurdaParemAddictionPhase2();
	public static final StatusEffect JURDA_PAREM_ADDICTION_PHASE3 = new JurdaParemAddictionPhase3();
	
	public static final Identifier TAKE_JURDA_PAREM = new Identifier(MOD_ID, "take_jurda_parem");

	public static final Text TITLE = Text.translatable("container.crafting");

	//public static final Block MATERIALKI_CRAFTING_TABLE;
	//@SuppressWarnings("rawtypes")
	//public static final BlockEntityType MATERIALKI_CRAFTING_TABLE_ENTITY;

	public static final RecipeType<? extends AbstractCookingRecipe> MATERIALKI_SMELTING_RECIPE_TYPE = null;
	
	
	static {
		MATERIALKI_CRAFTING_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, new Identifier(MOD_ID, "materialki_crafting"), new RecipeType<GrishaMaterialkiCraftingRecipe>() {
            @Override
            public String toString() {return "materialki_crafting";}
        });
		MATERIALKI_CRAFTING_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(MOD_ID, "materialki_crafting"), new GrishaMaterialkiCraftingRecipeSerializer());
	
		MATERIALKI_SCREEN_HANDLER= ScreenHandlerRegistry.registerSimple(Grisha.MATERIALKI_CRAFTING_ID, GrishaCraftingController::new);
		//MATERIALKI_SMELTING_SCREEN_HANDLER= ScreenHandlerRegistry.registerSimple(Grisha.MATERIALKI_SMELTING_ID, GrishaSmeltingController::new);
	
		//Block
		//MATERIALKI_CRAFTING_TABLE = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "materialki_crafting_table"), new MateralkiCraftingTable(FabricBlockSettings.of(Material.METAL)));
        //BlockItem
        //Registry.register(Registry.ITEM, new Identifier(MOD_ID, "materialki_crafting_table"), new BlockItem(MATERIALKI_CRAFTING_TABLE, new Item.Settings().group(ItemGroup.DECORATIONS)));
        //BlockEntity
        //MATERIALKI_CRAFTING_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "materialki_crafting_table"), FabricBlockEntityTypeBuilder.create(MateralkiCraftingTableEntity::new, GrishaBlocks.STORAGE_BLOCK).build(null));
    
	}
	
	@Override
	public void onInitialize() {
		
		// Register Calls
		GrishaItems.registerItems();
		GrishaBlocks.registerBlocks();
		GrishaEnchantments.registerEnchantments();
		GrishaThrownEntity.registerThrownEntities();

		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
			content.add(GrishaItems.PEBBLE);
			content.add(GrishaItems.CORE_CLOTH);
			content.add(GrishaItems.JURDA_SEEDS);
			content.add(GrishaItems.REFINED_JURDA);
			content.add(GrishaItems.JURDA_PRIME);
			content.add(GrishaItems.JURDA_PAREM);
			content.add(GrishaItems.PEBBLE_BLOCK);
		});
		
		//ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier(Grisha.MOD_ID),(i,syncId,player,buf)-> new GrishaCraftingController(syncId,player.getInventory(),BlockContext.create(player.world,buf.readBlockPos())));
		//MATERIALKI_SCREEN_HANDLER= ScreenHandlerRegistry.registerSimple(Grisha.MATERIALKI_CRAFTING_ID, (syncId,inventory)-> new GrishaCraftingController(syncId,inventory));

		GrishaBlocks.STORAGE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "grisha:storage",FabricBlockEntityTypeBuilder.create(StorageBlockEntity::new, GrishaBlocks.STORAGE_BLOCK).build(null));
		Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "jurda_parem_effect"), JURDA_PAREM_EFFECT);
		Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "jurda_parem_addiction_phase0"), JURDA_PAREM_ADDICTION_PHASE0);
		Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "jurda_parem_addiction_phase1"), JURDA_PAREM_ADDICTION_PHASE1);
		Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "jurda_parem_addiction_phase2"), JURDA_PAREM_ADDICTION_PHASE2);
		Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "jurda_parem_addiction_phase3"), JURDA_PAREM_ADDICTION_PHASE3);
		
		Registry.register(Registries.CUSTOM_STAT, "take_jurda_parem", TAKE_JURDA_PAREM);
		Stats.CUSTOM.getOrCreateStat(TAKE_JURDA_PAREM, StatFormatter.DEFAULT);
		
		ServerPlayNetworking.registerGlobalReceiver(ACTIVATE_MATERIALKI_PACKET_ID, (client, player, handler, buf, responseSender) -> {
			client.execute(() -> {
				System.out.println("Materialki Cast! - (ServerSide)");
				if(!GrishaParemCrafting.craft(player).getResult().isAccepted())
					GrishaSmallScience.ACTIVATE_MATERIALKI(player);
				//player.sendMessage(new LiteralText("Key 1 was pressed!"), false);
				
			});
		});
		
		ServerPlayNetworking.registerGlobalReceiver(ACTIVATE_ETHEREALKI_PACKET_ID, (client, player, handler, buf, responseSender) -> {
			client.execute(() -> {
				System.out.println("Etheralki Cast! - (ServerSide)");
				GrishaSmallScience.ACTIVATE_ETHEREALKI(player);
			});
		});
		
		ServerPlayNetworking.registerGlobalReceiver(ACTIVATE_CORPORALKI_PACKET_ID, (client, player, handler, buf, responseSender) -> {
			client.execute(() -> {
				System.out.println("Corporalki Cast! - (ServerSide)");
				GrishaSmallScience.ACTIVATE_CORPORALKI(player);
			});
		});
		
		ServerPlayNetworking.registerGlobalReceiver(AMPLIFIER_SICKNESS_PACKET_ID, (client, player, handler, buf, responseSender) -> {
			client.execute(() -> {
				GrishaSmallScience.amplifierSickness(player);
			});
		});
		
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult)->{
			if(ItemStack.areEqual(player.getStackInHand(Hand.MAIN_HAND), (new ItemStack(Items.AIR,1)))) {	
				if(entity instanceof LivingEntity)
				if(GrishaSmallScience.isWearingCorporalki(player))
				{
					GrishaSmallScience.corporalkiActive(player,(LivingEntity)entity);
				}
			}
			return ActionResult.PASS;
		});
		
		ServerPlayNetworking.registerGlobalReceiver(ACTIVATE_CORPORALKI_PAREM_PACKET_ID, (client, player, handler, buf, responseSender) -> {
			if(ItemStack.areEqual(player.getStackInHand(Hand.MAIN_HAND), (new ItemStack(Items.AIR,1)))) {
				Entity y = player.world.getEntityById(buf.readInt());
				if(y.isLiving()) {
						((LivingEntity) y).addStatusEffect((new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 10)));
				}
					
			}
		});
		
		ServerPlayNetworking.registerGlobalReceiver(SHOW_PAREM_ADDICTION_PACKET_ID, (client, player, handler, buf, responseSender) -> {
					player.addStatusEffect(new StatusEffectInstance(Grisha.JURDA_PAREM_ADDICTION_PHASE0,JurdaParemAddictionPhase1.LENGTH,0,false,false,true));
		});
		
		ServerPlayNetworking.registerGlobalReceiver(INCREASE_JURDA_PAREM_STAT_PACKET_ID, (client, player, handler, buf, responseSender) -> {
			player.incrementStat(Grisha.TAKE_JURDA_PAREM);
			((PlayerAbilitiesExt)player.getAbilities()).setConsumedParem(true);
		});
		
		ServerPlayNetworking.registerGlobalReceiver(START_PAREM_ADDICTION_PACKET_ID, (client, player, handler, buf, responseSender) -> {
			JurdaParemItem.addiction(player);
		});
		
		ServerTickEvents.END_SERVER_TICK.register((server)->{
			
		});

		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killed) -> {
					if(entity.isPlayer())
						GrishaSmallScience.enchantAmplifier((PlayerEntity) entity);
		});
		
	}

}
