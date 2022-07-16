package com.nicky.grisha.structures;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nicky.grisha.Grisha;

import net.fabricmc.api.ModInitializer;

public class StructuresMain implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = Grisha.MOD_ID;

    @Override
    public void onInitialize() {
        System.out.println("Hey");
        /*
         * We setup and register our structures here.
         * You should always register your stuff to prevent mod compatibility issue down the line.
         */
        GrishaStructures.registerStructureFeatures();
    }
}
