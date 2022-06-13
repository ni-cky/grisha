package com.nicky.grisha.features;

import com.nicky.grisha.Grisha;
import com.nicky.grisha.registry.GrishaBlocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.NoiseThresholdBlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import java.util.List;

@SuppressWarnings("deprecation")
public class GrishaFeature implements ModInitializer {
	
		public static ConfiguredFeature<?, ?> ORE_PEBBLE_BLOCK_OVERWORLD_CONFIGURED_FEATURE = Feature.ORE
		    .configure(new OreFeatureConfig(
					OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
		      		GrishaBlocks.PEBBLE_BLOCK.getDefaultState(),
					20)); // Vein size

		public static PlacedFeature ORE_PEBBLE_BLOCK_OVERWORLD_PLACED_FEATURE = ORE_PEBBLE_BLOCK_OVERWORLD_CONFIGURED_FEATURE.withPlacement(
			CountPlacementModifier.of(5), // number of veins per chunk
			SquarePlacementModifier.of(), // spreading horizontally
			HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))); // height
	
	  private static final Feature<SpiralFeatureConfig> SPIRAL = new SpiralFeature(SpiralFeatureConfig.CODEC);
	 
	  public static final ConfiguredFeature<?, ?> STONE_SPIRAL = SPIRAL.configure(new SpiralFeatureConfig(ConstantIntProvider.create(15), BlockStateProvider.of(Blocks.STONE.getDefaultState())));

	  private static final Feature<WildJurdaFeatureConfig> WILD_JURDA = new WildJurdaFeature(WildJurdaFeatureConfig.CODEC);

	  public static final ConfiguredFeature<?, ?> CONFIGURED_WILD_JURDA = WILD_JURDA.configure(new WildJurdaFeatureConfig(ConstantIntProvider.create(2)));

	  public static PlacedFeature PLACED_WILD_JURDA = CONFIGURED_WILD_JURDA.withPlacement(
			  NoiseThresholdCountPlacementModifier.of(-0.8, 15, 4),
			  RarityFilterPlacementModifier.of(100),
			  SquarePlacementModifier.of(),
			  PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
			  BiomePlacementModifier.of()
	  );
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
