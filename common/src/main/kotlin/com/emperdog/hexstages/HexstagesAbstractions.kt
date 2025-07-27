@file:JvmName("HexstagesAbstractions")

package com.emperdog.hexstages

import dev.architectury.injectables.annotations.ExpectPlatform
import com.emperdog.hexstages.registry.HexstagesRegistrar

fun initRegistries(vararg registries: HexstagesRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

@ExpectPlatform
fun <T : Any> initRegistry(registrar: HexstagesRegistrar<T>) {
    throw AssertionError()
}
