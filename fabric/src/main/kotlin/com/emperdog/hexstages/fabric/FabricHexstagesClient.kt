package com.emperdog.hexstages.fabric

import com.emperdog.hexstages.HexstagesClient
import net.fabricmc.api.ClientModInitializer

object FabricHexstagesClient : ClientModInitializer {
    override fun onInitializeClient() {
        HexstagesClient.init()
    }
}
