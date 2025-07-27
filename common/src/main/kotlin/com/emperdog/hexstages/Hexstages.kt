package com.emperdog.hexstages

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import com.emperdog.hexstages.registry.HexstagesActions
import com.google.gson.GsonBuilder

object Hexstages {
    const val MODID = "hexstages"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    @JvmStatic
    fun id(path: String) = ResourceLocation(MODID, path)

    fun init() {
        initRegistries(
            HexstagesActions,
        )
    }

    val dataLoader: HexstagesDataLoader = HexstagesDataLoader(GsonBuilder().create())
        get() = field
}
