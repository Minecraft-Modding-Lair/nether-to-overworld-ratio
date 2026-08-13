package dev.naturegecko.netheroverworldratio.neoforge;

import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLPaths;
import dev.naturegecko.netheroverworldratio.NetherOverworldRatio;

@Mod(NetherOverworldRatio.MOD_ID)
public class NetherOverworldRatioNeoForge {
    public NetherOverworldRatioNeoForge(IEventBus modEventBus) {
        NetherOverworldRatio.init(FMLPaths.CONFIGDIR.get());
    }
}
