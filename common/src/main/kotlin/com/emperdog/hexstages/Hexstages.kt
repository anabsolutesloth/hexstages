package com.emperdog.hexstages

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import com.emperdog.hexstages.registry.HexstagesActions

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
}
