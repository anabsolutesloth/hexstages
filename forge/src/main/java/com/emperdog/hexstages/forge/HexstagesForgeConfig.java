package com.emperdog.hexstages.forge;

import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.emperdog.hexstages.Hexstages;
import com.emperdog.hexstages.HexstagesConfig;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import thedarkcolour.kotlinforforge.KotlinModLoadingContext;

import java.util.*;

public class HexstagesForgeConfig implements HexstagesConfig {

    private static final Map<ResourceLocation, String> stagedActions = new HashMap<>();

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<List<?>> STAGED_ACTIONS = BUILDER
            .comment("Actions and their associated required Gamestage.")
            .comment("Format: namespace:action_name=stage")
            .comment("ex: hexcasting:teleport/great=master")
            .defineListAllowEmpty("stagedActions", List.of(), p -> p instanceof String);

    public static ForgeConfigSpec SPEC = BUILDER.build();

    public static void onConfigReload(final ModConfigEvent.Reloading event) {
        Hexstages.LOGGER.info("file: {}, type: {}", event.getConfig().getFileName(), event.getConfig().getType());
        load();
    }

    public static void onConfigLoad(final ModConfigEvent.Loading event) {
        load();
    }

    public static void load() {
        Hexstages.LOGGER.info("Loading Hexstages config entries...");
        List<?> actionsList = STAGED_ACTIONS.get();
        stagedActions.clear();
        Set<ResourceLocation> allActions = IXplatAbstractions.INSTANCE.getActionRegistry().keySet();
        actionsList.forEach(entry -> {
            Hexstages.LOGGER.info("entry: {}, is String: {}", entry, entry instanceof String);
            if(entry instanceof String entryString) {
                String[] actionAndStage = entryString.split("=");
                if(actionAndStage.length != 2)
                    throw new IllegalArgumentException("Error loading Hexstages Config: String for ActionID and Stage split created an array with a length not equal to 2.");
                ResourceLocation actionId = ResourceLocation.tryParse(actionAndStage[0]);
                if(!allActions.contains(actionId))
                    throw new IllegalArgumentException("Error loading Hexstages config: unkown action \""+ actionId +"\"");
                String stage = actionAndStage[1];
                if(!GameStageHelper.getKnownStages().contains(stage))
                    Hexstages.LOGGER.warn("Problem loading Hexstages config: Unknown stage \"{}\"", stage);

                stagedActions.put(actionId, stage);
            }
        });
    }

    public String getActionStage(ResourceLocation actionKey) {
        return stagedActions.getOrDefault(actionKey, null);
    }

    protected static void init() {
        Hexstages.LOGGER.info("HexstagesForgeConfig#init");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC);
        IEventBus bus = KotlinModLoadingContext.Companion.get().getKEventBus();
        bus.addListener(HexstagesForgeConfig::onConfigLoad);
        bus.addListener(HexstagesForgeConfig::onConfigReload);
    }
}
