package dev.naturegecko.netheroverworldratio;

import java.util.List;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

public final class NetherRatioConfig {
    public static final double DEFAULT_RATIO = 8.0D;
    private static volatile double ratio = DEFAULT_RATIO;
    private static final String FILE_NAME = NetherOverworldRatio.MOD_ID + ".toml";
    private static final String KEY = "ratio";

    private NetherRatioConfig() {
    }

    public static double getRatio() {
        return ratio;
    }

    public static void load(Path configDir) {
        Path configFile = configDir.resolve(FILE_NAME);

        if (!Files.exists(configFile)) {
            writeDefault(configFile);
            ratio = DEFAULT_RATIO;
            return;
        }
        ratio = readRatio(configFile);
    }

    private static double readRatio(Path configFile) {
        try {
            for (String line : Files.readAllLines(configFile, StandardCharsets.UTF_8)) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }

                int separator = trimmed.indexOf('=');
                if (separator < 0) {
                    continue;
                }

                String key = trimmed.substring(0, separator).trim();
                String value = trimmed.substring(separator + 1).trim();

                if (key.equals(KEY)) {
                    return parseRatio(value);
                }
            }
        } catch (IOException e) {
            NetherOverworldRatio.LOGGER.error("Failed to read {}, using default ratio {}", FILE_NAME, DEFAULT_RATIO, e);
            return DEFAULT_RATIO;
        }

        NetherOverworldRatio.LOGGER.warn("No '{}' entry found in {}, using default {}", KEY, FILE_NAME, DEFAULT_RATIO);
        return DEFAULT_RATIO;
    }

    private static double parseRatio(String value) {
        double parsed;
        try {
            parsed = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            NetherOverworldRatio.LOGGER.warn("Could not parse ratio '{}' in {}, using default {}", value, FILE_NAME,
                    DEFAULT_RATIO);
            return DEFAULT_RATIO;
        }

        if (!(parsed > 0.0D) || Double.isInfinite(parsed)) {
            NetherOverworldRatio.LOGGER.warn("Invalid ratio '{}' in {} (must be a positive number), using default {}",
                    value, FILE_NAME, DEFAULT_RATIO);
            return DEFAULT_RATIO;
        }

        return parsed;
    }

    private static void writeDefault(Path configFile) {
        List<String> lines = List.of(
                "# " + NetherOverworldRatio.MOD_NAME + " - configuration",
                "# Vanilla value: 8, Allowed value: 0.01 to 64",
                "#   ratio = 8      -> 1:8 ( Vanilla )",
                "#   ratio = 1      -> 1:1",
                "#   ratio = 0.125  -> 8:1 ( reversed )",
                KEY + "=" + DEFAULT_RATIO);
        try {
            Files.createDirectories(configFile.getParent());
            Files.write(configFile, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            NetherOverworldRatio.LOGGER.error("Failed to write default config to {}", configFile, e);
        }
    }
}
