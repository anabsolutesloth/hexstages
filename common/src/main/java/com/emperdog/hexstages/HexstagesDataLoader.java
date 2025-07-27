package com.emperdog.hexstages;

import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HexstagesDataLoader extends SimpleJsonResourceReloadListener {

    private Map<ResourceLocation, String> stagedActions = ImmutableMap.of();

    public HexstagesDataLoader(Gson gson) {
        super(gson, "hexstages/staged_actions");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> files, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<ResourceLocation, String> builder = ImmutableMap.builder();
        Set<ResourceLocation> allActions = IXplatAbstractions.INSTANCE.getActionRegistry().keySet();

        List<String> alreadyStaged = new ArrayList<>();

        files.forEach((location, json) -> {
            Hexstages.LOGGER.info("Loading file \"{}\"", location);
            json.getAsJsonObject().entrySet().forEach(entry -> {
                String stage = entry.getKey();

                if(!GameStageHelper.getKnownStages().contains(stage))
                    Hexstages.LOGGER.warn("Unknown stage \"{}\", this may cause problems!",
                            stage);

                entry.getValue().getAsJsonArray().forEach(value -> {
                    String action = value.getAsString();
                    ResourceLocation actionResource = ResourceLocation.tryParse(action);
                    if(actionResource == null) {
                        Hexstages.LOGGER.error("invalid ResourceLocation \"{}\". This will cause problems!",
                                action);
                        return;
                    }
                    if(!allActions.contains(actionResource)) {
                        Hexstages.LOGGER.info("No registered Action with ID \"{}\".",
                                actionResource);
                        return;
                    }

                    if(alreadyStaged.contains(action))
                        Hexstages.LOGGER.warn("Found duplicate Stage for Action \"{}\". Ignoring new: {}",
                                action, stage);
                    builder.put(actionResource, stage);
                    alreadyStaged.add(action);
                });
            });
        });

        stagedActions = builder.buildOrThrow();
        Hexstages.LOGGER.info("Staged {} actions.", stagedActions.size());
    }

    @Nullable
    public String getActionStage(ResourceLocation action) {
        return stagedActions.get(action);
    }

    public Map<ResourceLocation, String> getAllActionStages() {
        return ImmutableMap.copyOf(stagedActions);
    }
}
