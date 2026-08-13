package dev.naturegecko.netheroverworldratio;

import org.slf4j.Logger;
import java.nio.file.Path;
import org.slf4j.LoggerFactory;

public final class NetherOverworldRatio {

    public static final String MOD_ID = "nether_overworld_ratio";
    public static final String MOD_NAME = "Nether to Overworld Ratio";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    private NetherOverworldRatio() {
    }

    public static void init(Path configDir) {
        NetherRatioConfig.load(configDir);
        LOGGER.info("[{}] Nether <-> Overworld ratio set to 1:{}", MOD_NAME, NetherRatioConfig.getRatio());
    }
}
