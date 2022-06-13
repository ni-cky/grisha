package com.nicky.grisha.structures;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nicky.grisha.Grisha;
import com.nicky.grisha.structures.mixin.StructuresConfigAccessor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

@SuppressWarnings("deprecation")
public class StructuresMain implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = Grisha.MOD_ID;

    @Override
    public void onInitialize() {

        /*
         * We setup and register our structures here.
         * You should always register your stuff to prevent mod compatibility issue down the line.
         */
        GrishaStructures.setupAndRegisterStructureFeatures();
        GrishaConfiguredStructures.registerConfiguredStructures();

        /*
         * This is the API you will use to add anything to any biome.
         * This includes spawns, changing the biome's looks, messing with its surfacebuilders,
         * adding carvers, spawning new features... etc
         *
         * Make sure you give this an identifier to make it clear later what mod did a change and why.
         * It'll help people look to see if your mod was removing something from biomes.
         * The biome modifier identifier might also be used by modpacks to disable mod's modifiers too for customization.
         */
        BiomeModifications.create(new Identifier(MOD_ID, "stone"))
                .add(   // Describes what we are doing. SInce we are adding a structure, we choose ADDITIONS.
                        ModificationPhase.ADDITIONS,

                        // Add our structure to all biomes including other modded biomes.
                        // You can filter to certain biomes based on stuff like temperature, scale, precipitation, mod id.
                        BiomeSelectors.all(),

                        // context is basically the biome itself. This is where you do the changes to the biome.
                        // Here, we will add our ConfiguredStructureFeature to the biome.
                        context -> {
                            context.getGenerationSettings().addBuiltInStructure(GrishaConfiguredStructures.CONFIGURED_STONE);
                        });
        
        BiomeModifications.create(new Identifier(MOD_ID, "small_palace"))
        .add(   // Describes what we are doing. SInce we are adding a structure, we choose ADDITIONS.
                ModificationPhase.ADDITIONS,
                BiomeSelectors.all(),
                context -> {
                    context.getGenerationSettings().addBuiltInStructure(GrishaConfiguredStructures.CONFIGURED_SMALL_PALACE);
                });
        
        BiomeModifications.create(new Identifier(MOD_ID, "campsite"))
        .add(   // Describes what we are doing. SInce we are adding a structure, we choose ADDITIONS.
                ModificationPhase.ADDITIONS,
                BiomeSelectors.all(),
                context -> {
                    context.getGenerationSettings().addBuiltInStructure(GrishaConfiguredStructures.CONFIGURED_CAMPSITE);
                });
    }


    // This is optional and can be used for blacklisting the structure from dimensions.
    // These two are for making sure our ServerWorldEvents.LOAD event always fires after Fabric API's usage of the same event.
    // This is done so our changes don't get overwritten by Fabric API adding structure spacings to all dimensions.
    // To activate these methods, make this class implement this:
    //    `implements ModInitializer, DedicatedServerModInitializer, ClientModInitializer {`
    // And then go to fabric.mod.json and add this class to a "client" and "server" entry within "entrypoints" section.

//    @Override
//    public void onInitializeServer() {
//        removeStructureSpawningFromSelectedDimension();
//    }
//
//    @Override
//    public void onInitializeClient() {
//        removeStructureSpawningFromSelectedDimension();
//    }

    /**
     * || OPTIONAL ||
     *  This is optional as Fabric API already adds your structure to all dimension.
     *  But if you want to do dimension based blacklisting, you will need to both
     *  manually remove your structure from the chunkgenerator's structure spacing map.
     * If the spacing or our structure is not added, the structure doesn't spawn in that dimension.
     */
    public static void removeStructureSpawningFromSelectedDimension() {
        // Controls the dimension blacklisting
        ServerWorldEvents.LOAD.register((MinecraftServer minecraftServer, ServerWorld serverWorld)->{

            // Need temp map as some mods use custom chunk generators with immutable maps in themselves.
            Map<StructureFeature<?>, StructureConfig> tempMap = new HashMap<>(serverWorld.getChunkManager().getChunkGenerator().getStructuresConfig().getStructures());

            // Make absolutely sure modded dimension cannot spawn our structures.
            // New dimensions under the minecraft namespace will still get it (datapacks might do this)
            if(!serverWorld.getRegistryKey().getValue().getNamespace().equals("minecraft")) {
                tempMap.keySet().remove(GrishaStructures.STONE);
                tempMap.keySet().remove(GrishaStructures.SMALL_PALACE);
                tempMap.keySet().remove(GrishaStructures.CAMPSITE);
            }

            // Set the new modified map of structure spacing to the dimension's chunkgenerator.
            ((StructuresConfigAccessor)serverWorld.getChunkManager().getChunkGenerator().getStructuresConfig()).setStructures(tempMap);
        });
    }
}
