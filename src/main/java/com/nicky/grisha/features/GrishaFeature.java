package com.nicky.grisha.features;

import com.nicky.grisha.Grisha;
import com.nicky.grisha.registry.GrishaBlocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.impl.biome.modification.BuiltInRegistryKeys;
import net.minecraft.block.Blocks;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.RuleTestType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
//import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
//import net.minecraft.util.registry.RegistryEntry;
//import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.NoiseThresholdBlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import java.util.Arrays;
import java.util.List;

public class GrishaFeature implements ModInitializer {
	
		public static ConfiguredFeature<?, ?> ORE_PEBBLE_BLOCK_OVERWORLD_CONFIGURED_FEATURE = new ConfiguredFeature<>(
				Feature.ORE, new OreFeatureConfig(
					List.of(OreFeatureConfig.createTarget(new BlockMatchRuleTest(Blocks.STONE), Blocks.STONE.getDefaultState())),
					20)); // Vein size

		public static PlacedFeature ORE_PEBBLE_BLOCK_OVERWORLD_PLACED_FEATURE = new PlacedFeature(
				RegistryEntry.of(ORE_PEBBLE_BLOCK_OVERWORLD_CONFIGURED_FEATURE),
				Arrays.asList(
						CountPlacementModifier.of(5), // number of veins per chunk
						SquarePlacementModifier.of(), // spreading horizontally
						HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64)) // height
				));

	  private static final Feature<WildJurdaFeatureConfig> WILD_JURDA = new WildJurdaFeature(WildJurdaFeatureConfig.CODEC);

	  public static final ConfiguredFeature<?, ?> CONFIGURED_WILD_JURDA = new ConfiguredFeature<>(WILD_JURDA, new WildJurdaFeatureConfig(ConstantIntProvider.create(2)));

	  public static PlacedFeature PLACED_WILD_JURDA = new PlacedFeature(
			  RegistryEntry.of(CONFIGURED_WILD_JURDA),
			  Arrays.asList(
			  NoiseThresholdCountPlacementModifier.of(-0.8, 15, 4),
			  RarityFilterPlacementModifier.of(100),
			  SquarePlacementModifier.of(),
			  PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
			  BiomePlacementModifier.of()
			  ));
	/*public static final ConfiguredFeature<?, ?> CONFIGURED_WILD_JURDA = Feature.FLOWER.configure(
			VegetationConfiguredFeatures.createRandomPatchFeatureConfig(
					new WeightedBlockStateProvider(DataPool.builder()
							.add(Blocks.POPPY.getDefaultState(), 2)
							.add(Blocks.DANDELION.getDefaultState(), 1)),
					64)));*/
	  
	  @Override
	  public void onInitialize() {
		  Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
				  new Identifier(Grisha.MOD_ID, "ore_pebble_block_overworld"), ORE_PEBBLE_BLOCK_OVERWORLD_CONFIGURED_FEATURE);
		  Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Grisha.MOD_ID, "ore_pebble_block_overworld"),
				  ORE_PEBBLE_BLOCK_OVERWORLD_PLACED_FEATURE);
		  BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
				  RegistryKey.of(Registry.PLACED_FEATURE_KEY,
						  new Identifier(Grisha.MOD_ID, "ore_pebble_block_overworld")));

	    Registry.register(Registry.FEATURE, new Identifier(Grisha.MOD_ID, "wild_jurda"), WILD_JURDA);
	       
	    RegistryKey<ConfiguredFeature<?, ?>> wildJurda = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
		            new Identifier(Grisha.MOD_ID, "configured_wild_jurda"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, wildJurda.getValue(), CONFIGURED_WILD_JURDA);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Grisha.MOD_ID, "placed_wild_jurda"),
				  PLACED_WILD_JURDA);
		       //
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS,BiomeKeys.FOREST,BiomeKeys.SAVANNA,BiomeKeys.SUNFLOWER_PLAINS,BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
				new Identifier(Grisha.MOD_ID, "placed_wild_jurda")));
	  }
	}
