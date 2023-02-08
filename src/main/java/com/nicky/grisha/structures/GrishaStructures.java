package com.nicky.grisha.structures;

import com.nicky.grisha.Grisha;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

public class GrishaStructures {
	/**
    /**
     * Registers the structure itself and sets what its path is. In this case, the
     * structure will have the Identifier of structure_tutorial:run_down_house.
     *
     * It is always a good idea to register your Structures so that other mods and datapacks can
     * use them too directly from the registries. It great for mod/datapacks compatibility.
     */
    //public static StructureType<Stone> STONE;
    public static final RegistryKey<Structure> STONE = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier(Grisha.MOD_ID, "stone"));
    public static final RegistryKey<StructureSet> STONE_SET = RegistryKey.of(RegistryKeys.STRUCTURE_SET, new Identifier(Grisha.MOD_ID, "stone_set"));
    public static final RegistryKey<StructurePool> STONE_TEMPLATE_POOL = RegistryKey.of(RegistryKeys.TEMPLATE_POOL, new Identifier(Grisha.MOD_ID, "stone/start_pool"));
    public static final RegistryKey<Structure> SMALL_PALACE = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier(Grisha.MOD_ID, "small_palace"));
    public static final RegistryKey<StructureSet> SMALL_PALACE_SET = RegistryKey.of(RegistryKeys.STRUCTURE_SET, new Identifier(Grisha.MOD_ID, "small_palace_set"));
    public static final RegistryKey<StructurePool> SMALL_PALACE_TEMPLATE_POOL = RegistryKey.of(RegistryKeys.TEMPLATE_POOL, new Identifier(Grisha.MOD_ID, "small_palace/start_centre_back_pool"));
    public static final RegistryKey<Structure> CAMPSITE = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier(Grisha.MOD_ID, "campsite"));
    public static final RegistryKey<StructureSet> CAMPSITE_SET = RegistryKey.of(RegistryKeys.STRUCTURE_SET, new Identifier(Grisha.MOD_ID, "campsite_set"));
    public static final RegistryKey<StructurePool> CAMPSITE_TEMPLATE_POOL = RegistryKey.of(RegistryKeys.TEMPLATE_POOL, new Identifier(Grisha.MOD_ID, "campsite/start_pool"));
    //public static StructureType<Small_Palace> SMALL_PALACE;
    //public static StructureType<Campsite> CAMPSITE;
    
    /**
     * This is where we use Fabric API's structure API to setup the StructureFeature
     * See the comments in below for more details.
     */
    public static void registerStructureFeatures() {

        // This is Fabric API's builder for structures.
        // It has many options to make sure your structure will spawn and work properly.
        // Give it your structure and the identifier you want for it.
        System.out.println("I register the structures here");
        /*STONE = Registry.register(Registry.STRUCTURE_TYPE,new Identifier(StructuresMain.MOD_ID, "stone"), () -> Stone.CODEC);
        SMALL_PALACE = Registry.register(Registry.STRUCTURE_TYPE,new Identifier(StructuresMain.MOD_ID,"small_palace"), () -> Small_Palace.CODEC);
        CAMPSITE = Registry.register(Registry.STRUCTURE_TYPE,new Identifier(StructuresMain.MOD_ID,"campsite"), () -> Campsite.CODEC);

/*        StructureFeature F = StructureFeatureAccessor.callRegister(StructuresMain.MOD_ID + ":stone", STONE, GenerationStep.Feature.SURFACE_STRUCTURES);

        StructureFeatureAccessor.callRegister(StructuresMain.MOD_ID + ":small_palace", SMALL_PALACE, GenerationStep.Feature.SURFACE_STRUCTURES);

        StructureFeatureAccessor.callRegister(StructuresMain.MOD_ID + ":campsite", CAMPSITE, GenerationStep.Feature.SURFACE_STRUCTURES);
*/
        // Add more structures here and so on
    }
}
