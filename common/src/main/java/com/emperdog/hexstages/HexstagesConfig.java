package com.emperdog.hexstages;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface HexstagesConfig {

    HexstagesConfig INSTANCE = find();

    String getActionStage(ResourceLocation actionKey);

    private static HexstagesConfig find() {
        List<ServiceLoader.Provider<HexstagesConfig>> providers = ServiceLoader.load(HexstagesConfig.class).stream().toList();
        int size = providers.size();
        if(size != 1) {

            throw new IllegalStateException("There should be exactly one HexstagesConfig implementation on the classpath. Found "+ size +":"+
                    providers.stream().map(p -> p.type().getName()).collect(Collectors.joining(",", "[", "]")));
        } else {
            ServiceLoader.Provider<HexstagesConfig> provider = providers.get(0);
            Hexstages.LOGGER.info("Instantiating HexstagesConfig impl: {}", provider.type().getName());
            return provider.get();
        }
    }
}
