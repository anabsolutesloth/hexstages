package com.emperdog.hexstages.mixin;

import at.petrak.hexcasting.api.casting.PatternShapeMatch;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import com.emperdog.hexstages.Hexstages;
import com.emperdog.hexstages.HexstagesMishap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CastingEnvironment.class)
public abstract class CastingEnvironmentMixin {

    @WrapOperation(method = "precheckAction", remap = false,
            at = @At(value = "INVOKE",
                    target = "Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;actionKey(Lat/petrak/hexcasting/api/casting/PatternShapeMatch;)Lnet/minecraft/resources/ResourceLocation;"))
    public ResourceLocation hexstages$precheckAction(CastingEnvironment instance, PatternShapeMatch match, Operation<ResourceLocation> original) throws HexstagesMishap {
        ResourceLocation actionKey = original.call(instance, match);
        if(instance instanceof PlayerBasedCastEnv playerCastEnv)
            hexstages$isPatternStaged(playerCastEnv, actionKey);
        return actionKey;
    }

    @Unique
    private void hexstages$isPatternStaged(PlayerBasedCastEnv playerCastEnv, ResourceLocation actionKey) throws HexstagesMishap {
        String actionStage = Hexstages.INSTANCE.getDataLoader().getActionStage(actionKey);
        //Hexstages.LOGGER.info("actionStage: {}, actionKey: {}", actionStage, actionKey);
        if(actionStage != null
                && !GameStageHelper.hasStage(playerCastEnv.getCaster(), actionStage)) {
            throw new HexstagesMishap(actionStage, actionKey);
        }
    }
}
