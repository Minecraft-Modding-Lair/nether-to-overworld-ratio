package dev.naturegecko.netheroverworldratio.fabric;

import dev.naturegecko.netheroverworldratio.NetherOverworldRatio;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class NetherOverworldRatioFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        NetherOverworldRatio.init(FabricLoader.getInstance().getConfigDir());
    }
}
