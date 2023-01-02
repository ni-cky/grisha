package com.nicky.grisha.registry;

import com.nicky.grisha.Grisha;
import com.nicky.grisha.blocks.JurdaBlock;
import com.nicky.grisha.blocks.StorageBlock;
import com.nicky.grisha.entity.StorageBlockEntity;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
//import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class GrishaBlocks {

	public static final Block PEBBLE_BLOCK = new Block(FabricBlockSettings
			.of(Material.METAL)
			.requiresTool()
			.strength(0.8f, 3f)
			.sounds(BlockSoundGroup.STONE));
	
	public static final Block STORAGE_BLOCK = new StorageBlock(FabricBlockSettings
			.of(Material.METAL));
	
	public static BlockEntityType<StorageBlockEntity> STORAGE_BLOCK_ENTITY;
	
	public static final CropBlock JURDA_BLOCK = new JurdaBlock(AbstractBlock.Settings
			.of(Material.PLANT)
			.nonOpaque()
			.noCollision()
			.ticksRandomly()
			.breakInstantly()
			.sounds(BlockSoundGroup.CROP));

	public static final PlantBlock WILD_JURDA_BLOCK = new FlowerBlock(
			StatusEffects.HUNGER, 7,
			AbstractBlock.Settings
					.of(Material.PLANT)
					.noCollision()
					.nonOpaque()
					.breakInstantly()
					.sounds(BlockSoundGroup.CROP));

	
	public static void registerBlocks() {		
		Registry.register(Registry.BLOCK, new Identifier(Grisha.MOD_ID,"jurda_block"), JURDA_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(Grisha.MOD_ID,"wild_jurda_block"), WILD_JURDA_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(Grisha.MOD_ID,"pebble_block"), PEBBLE_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(Grisha.MOD_ID,"storage_block"), STORAGE_BLOCK);
	}
	
	public static void registerBlocksClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), JURDA_BLOCK);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), WILD_JURDA_BLOCK);
	}
	
	
}
