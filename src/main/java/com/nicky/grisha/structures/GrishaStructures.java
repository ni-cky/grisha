package com.nicky.grisha.structures;

import com.nicky.grisha.structures.mixin.StructureFeatureAccessor;
import com.nicky.grisha.structures_classes.Campsite;
import com.nicky.grisha.structures_classes.Small_Palace;
import com.nicky.grisha.structures_classes.Stone;

import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;

public class GrishaStructures {
	/**
    /**
     * Registers the structure itself and sets what its path is. In this case, the
     * structure will have the Identifier of structure_tutorial:run_down_house.
     *
     * It is always a good idea to register your Structures so that other mods and datapacks can
     * use them too directly from the registries. It great for mod/datapacks compatibility.
     */
    public static StructureFeature<?> STONE = new Stone();
    public static StructureFeature<?> SMALL_PALACE = new Small_Palace();
    public static StructureFeature<?> CAMPSITE = new Campsite();
    
    /**
     * This is where we use Fabric API's structure API to setup the StructureFeature
     * See the comments in below for more details.
     */
    public static void registerStructureFeatures() {

        // This is Fabric API's builder for structures.
        // It has many options to make sure your structure will spawn and work properly.
        // Give it your structure and the identifier you want for it.
        StructureFeature F = StructureFeatureAccessor.callRegister(StructuresMain.MOD_ID + ":stone", STONE, GenerationStep.Feature.SURFACE_STRUCTURES);
        System.out.println("GrishaStructures, l34: "+F.toString());

        StructureFeatureAccessor.callRegister(StructuresMain.MOD_ID + ":small_palace", SMALL_PALACE, GenerationStep.Feature.SURFACE_STRUCTURES);

        StructureFeatureAccessor.callRegister(StructuresMain.MOD_ID + ":campsite", CAMPSITE, GenerationStep.Feature.SURFACE_STRUCTURES);

        // Add more structures here and so on
    }
}
