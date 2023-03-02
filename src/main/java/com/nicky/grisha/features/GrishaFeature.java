package com.nicky.grisha.features;

import com.nicky.grisha.Grisha;
import com.nicky.grisha.registry.GrishaBlocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.*;
import net.fabricmc.fabric.impl.biome.modification.BuiltInRegistryKeys;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.RuleTestType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
//import net.minecraft.util.registry.BuiltinRegistries;
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
import java.util.function.BiConsumer;

public class GrishaFeature implements ModInitializer {

	public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_PEBBLE_BLOCK_CF = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Grisha.MOD_ID, "ore_pebble_block_overworld"));

	public static final RegistryKey<PlacedFeature> ORE_PEBBLE_BLOCK_PF = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Grisha.MOD_ID, "ore_pebble_block_overworld"));

	public static final RegistryKey<ConfiguredFeature<?, ?>> WILD_JURDA_CF = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Grisha.MOD_ID, "wild_jurda"));

	public static final RegistryKey<PlacedFeature> WILD_JURDA_PF = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Grisha.MOD_ID, "wild_jurda"));

	@Override
	public void onInitialize() {
		BiomeModifications.create(new Identifier(Grisha.MOD_ID, "features"))
				.add(ModificationPhase.ADDITIONS,
						// we want our ore possibly everywhere in the overworld
						BiomeSelectors.foundInOverworld(),
						myOreModifier())
				.add(ModificationPhase.ADDITIONS,
						// we want our ore possibly everywhere in the overworld
						BiomeSelectors.includeByKey(BiomeKeys.PLAINS,BiomeKeys.WINDSWEPT_HILLS,BiomeKeys.WINDSWEPT_FOREST,BiomeKeys.FOREST,BiomeKeys.SUNFLOWER_PLAINS,BiomeKeys.FLOWER_FOREST,BiomeKeys.BIRCH_FOREST,BiomeKeys.GROVE,BiomeKeys.MEADOW,BiomeKeys.WINDSWEPT_SAVANNA,BiomeKeys.SAVANNA),
						myJurdaModifier());

	}


	private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> myOreModifier() {
		return (biomeSelectionContext, biomeModificationContext) ->
				// here we can potentially narrow our biomes down
				// but here we won't
				biomeModificationContext.getGenerationSettings().addFeature(
						// ores to ores
						GenerationStep.Feature.UNDERGROUND_ORES,
						// this is the key of the placed feature
						ORE_PEBBLE_BLOCK_PF);
	}

	private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> myJurdaModifier() {
		return (biomeSelectionContext, biomeModificationContext) ->
				// here we can potentially narrow our biomes down
				// but here we won't
				biomeModificationContext.getGenerationSettings().addFeature(
						// ores to ores
						GenerationStep.Feature.VEGETAL_DECORATION,
						// this is the key of the placed feature
						WILD_JURDA_PF);
	}
}