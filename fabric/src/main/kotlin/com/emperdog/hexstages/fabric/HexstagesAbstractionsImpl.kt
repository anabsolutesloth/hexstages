@file:JvmName("HexstagesAbstractionsImpl")

package com.emperdog.hexstages.fabric

import com.emperdog.hexstages.registry.HexstagesRegistrar
import net.minecraft.core.Registry

fun <T : Any> initRegistry(registrar: HexstagesRegistrar<T>) {
    val registry = registrar.registry
    registrar.init { id, value -> Registry.register(registry, id, value) }
}
