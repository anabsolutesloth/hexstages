package com.emperdog.hexstages.forge

import dev.architectury.platform.forge.EventBuses
import com.emperdog.hexstages.Hexstages
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.PackOutput
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.event.AddReloadListenerEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Hexstages.MODID)
class HexstagesForge {
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(Hexstages.MODID, this)
            addListener(ForgeHexstagesClient::init)
            addListener(::gatherData)
        }
        FORGE_BUS.addListener(::addReloadListeners)
        Hexstages.init()
        //HexstagesForgeConfig.init()
    }

    private fun gatherData(event: GatherDataEvent) {
        event.apply {
            // TODO: add datagen providers here
        }
    }

    private fun addReloadListeners(event: AddReloadListenerEvent) {
        event.apply {
            addListener(Hexstages.dataLoader)
        }
    }
}

fun <T : DataProvider> GatherDataEvent.addProvider(run: Boolean, factory: (PackOutput) -> T) =
    generator.addProvider(run, Factory { factory(it) })