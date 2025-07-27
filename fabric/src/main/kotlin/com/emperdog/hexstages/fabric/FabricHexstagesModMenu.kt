package com.emperdog.hexstages.fabric

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import com.emperdog.hexstages.HexstagesClient

object FabricHexstagesModMenu : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory(HexstagesClient::getConfigScreen)
}
