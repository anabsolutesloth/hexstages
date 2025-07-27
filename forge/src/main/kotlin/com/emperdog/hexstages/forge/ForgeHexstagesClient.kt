package com.emperdog.hexstages.forge

import com.emperdog.hexstages.HexstagesClient
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.LOADING_CONTEXT

object ForgeHexstagesClient {
    fun init(event: FMLClientSetupEvent) {
        HexstagesClient.init()
    }
}
