package com.nicky.grisha.structures;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nicky.grisha.Grisha;

import net.fabricmc.api.ModInitializer;

public class StructuresMain implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = Grisha.MOD_ID;

    public static final RegistryKey<Structure> STONE = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier(Grisha.MOD_ID, "stone"));
    public static final RegistryKey<StructureSet> STONE_SET = RegistryKey.of(RegistryKeys.STRUCTURE_SET, new Identifier(Grisha.MOD_ID, "stone_set"));
    public static final RegistryKey<StructurePool> STONE_TEMPLATE_POOL = RegistryKey.of(RegistryKeys.TEMPLATE_POOL, new Identifier(Grisha.MOD_ID, "stone/start_pool"));
    public static final RegistryKey<Structure> SMALL_PALACE = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier(Grisha.MOD_ID, "small_palace"));
    public static final RegistryKey<StructureSet> SMALL_PALACE_SET = RegistryKey.of(RegistryKeys.STRUCTURE_SET, new Identifier(Grisha.MOD_ID, "small_palace_set"));
    public static final RegistryKey<StructurePool> SMALL_PALACE_TEMPLATE_POOL = RegistryKey.of(RegistryKeys.TEMPLATE_POOL, new Identifier(Grisha.MOD_ID, "small_palace/start_centre_back_pool"));
    public static final RegistryKey<Structure> CAMPSITE = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier(Grisha.MOD_ID, "campsite"));
    public static final RegistryKey<StructureSet> CAMPSITE_SET = RegistryKey.of(RegistryKeys.STRUCTURE_SET, new Identifier(Grisha.MOD_ID, "campsite_set"));
    public static final RegistryKey<StructurePool> CAMPSITE_TEMPLATE_POOL = RegistryKey.of(RegistryKeys.TEMPLATE_POOL, new Identifier(Grisha.MOD_ID, "campsite/start_pool"));


    @Override
    public void onInitialize() {
        System.out.println("Hey");
        /*
         * We setup and register our structures here.
         * You should always register your stuff to prevent mod compatibility issue down the line.
         */
        //GrishaStructures.registerStructureFeatures();
    }
}
