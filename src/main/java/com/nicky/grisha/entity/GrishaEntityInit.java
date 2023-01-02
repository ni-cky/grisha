package com.nicky.grisha.entity;

import com.nicky.grisha.Grisha;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class GrishaEntityInit implements ModInitializer {
    
    /*
     * Registers our Cube Entity under the ID "entitytesting:cube".
     *
     * The entity is registered under the SpawnGroup#CREATURE category, which is what most animals and passive/neutral mobs use.
     * It has a hitbox size of .75x.75, or 12 "pixels" wide (3/4ths of a block).
     */
    public static final EntityType<GrishaCorporalkiEntity> GRISHA_CORPORALKI_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Grisha.MOD_ID, "grisha_corporalki_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GrishaCorporalkiEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.99f)).build()
    );
    
    public static final EntityType<GrishaMaterialkiEntity> GRISHA_MATERIALKI_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Grisha.MOD_ID, "grisha_materialki_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GrishaMaterialkiEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.99f)).build()
    );
    
    public static final EntityType<GrishaEtherealkiEntity> GRISHA_ETHERIALKI_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Grisha.MOD_ID, "grisha_etherealki_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GrishaEtherealkiEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.99f)).build()
    );
 
    @Override
    public void onInitialize() {
    	FabricDefaultAttributeRegistry.register(GRISHA_CORPORALKI_ENTITY, GrishaCorporalkiEntity.createGrishaAttributes());
    	FabricDefaultAttributeRegistry.register(GRISHA_MATERIALKI_ENTITY, GrishaMaterialkiEntity.createGrishaAttributes());
    	FabricDefaultAttributeRegistry.register(GRISHA_ETHERIALKI_ENTITY, GrishaEtherealkiEntity.createGrishaAttributes());
    }
}
