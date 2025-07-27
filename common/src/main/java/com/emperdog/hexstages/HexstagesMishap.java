package com.emperdog.hexstages;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HexstagesMishap extends Mishap {

    public final String stage;
    public final ResourceLocation actionKey;

    public HexstagesMishap(String stage, ResourceLocation actionKey) {
       this.stage = stage;
       this.actionKey = actionKey;
    }

    @Override
    public @NotNull FrozenPigment accentColor(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
        return dyeColor(DyeColor.BLUE);
    }

    @Override
    public void execute(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context, @NotNull List<Iota> list) {
        castingEnvironment.getMishapEnvironment().blind(60);
    }

    @Override
    protected @Nullable Component errorMessage(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
        return Component.translatable("hexstages.mishap.missing_stage", stage, Component.translatable("hexcasting.action."+ actionKey));
    }
}
