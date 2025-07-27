package com.emperdog.hexstages.fabric

import com.emperdog.hexstages.Hexstages
import net.fabricmc.api.ModInitializer

object FabricHexstages : ModInitializer {
    override fun onInitialize() {
        Hexstages.init()
    }
}
