package dev.naturegecko.netheroverworldratio.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.world.level.dimension.DimensionType;
import dev.naturegecko.netheroverworldratio.NetherRatioConfig;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public abstract class DimensionTypeMixin {

    private static final double VANILLA_OVERWORLD_SCALE = 1.0D;
    private static final double VANILLA_NETHER_SCALE = 8.0D;
    private static final double EPSILON = 1.0E-6D;

    @Inject(method = "getTeleportationScale", at = @At("HEAD"), cancellable = true)
    private static void netherOverworldRatio$getTeleportationScale(DimensionType from, DimensionType to,
            CallbackInfoReturnable<Double> cir) {
        double ratio = NetherRatioConfig.getRatio();

        if (isVanillaNether(from) && isVanillaOverworld(to)) {
            cir.setReturnValue(ratio);
        } else if (isVanillaOverworld(from) && isVanillaNether(to)) {
            cir.setReturnValue(1.0D / ratio);
        }
    }

    private static boolean isVanillaNether(DimensionType type) {
        return Math.abs(type.coordinateScale() - VANILLA_NETHER_SCALE) < EPSILON;
    }

    private static boolean isVanillaOverworld(DimensionType type) {
        return Math.abs(type.coordinateScale() - VANILLA_OVERWORLD_SCALE) < EPSILON;
    }
}
