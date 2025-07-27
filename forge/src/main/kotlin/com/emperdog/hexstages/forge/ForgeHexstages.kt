package com.emperdog.hexstages.forge

import dev.architectury.platform.forge.EventBuses
import com.emperdog.hexstages.Hexstages
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.PackOutput
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Hexstages.MODID)
class HexstagesForge {
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(Hexstages.MODID, this)
            addListener(ForgeHexstagesClient::init)
            addListener(::gatherData)
        }
        Hexstages.init()
        HexstagesForgeConfig.init()
    }

    private fun gatherData(event: GatherDataEvent) {
        event.apply {
            // TODO: add datagen providers here
        }
    }
}

fun <T : DataProvider> GatherDataEvent.addProvider(run: Boolean, factory: (PackOutput) -> T) =
    generator.addProvider(run, Factory { factory(it) })
