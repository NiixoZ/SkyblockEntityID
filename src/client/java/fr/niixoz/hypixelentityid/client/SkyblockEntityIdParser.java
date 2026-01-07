package fr.niixoz.hypixelentityid.client;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public final class SkyblockEntityIdParser {
    private SkyblockEntityIdParser() {}

    // Nombre Hypixel : 100, 13,000, 2.5, 750k, 1M, 2B, etc.
    private static final String NUM = "\\d[\\d,\\.]*";
    private static final String SUFFIX = "[kKmMbBtT]?";
    private static final String HEART = "[❤♥]";

    // " 1M/1M❤" ou " 13,000/13,000❤" (coeur optionnel)
    private static final Pattern TRAILING_HP_SLASH = Pattern.compile(
            "\\s+" + NUM + SUFFIX + "\\s*/\\s*" + NUM + SUFFIX + "\\s*" + HEART + "?\\s*$"
    );

    // " 2M❤" ou " 750k❤" (cœur obligatoire)
    private static final Pattern TRAILING_HP_SINGLE = Pattern.compile(
            "\\s+" + NUM + SUFFIX + "\\s*" + HEART + "\\s*$"
    );

    private static final Pattern LEVEL_PREFIX = Pattern.compile("^\\s*\\[(?:Lv|Lvl)\\s*\\d+\\]\\s*");
    private static final Pattern MC_COLOR = Pattern.compile("§.");
    /**
     * Convertit un nom d'ArmorStand Hypixel du style:
     * "[Lv55] ⊙☠ Obsidian Defender 10,000/10,000❤"
     * en:
     * "OBSIDIAN_DEFENDER"
     */
    public static String toEntityId(String armorStandLabel) {
        if (armorStandLabel == null) return "";

        String s = armorStandLabel;

        // 0) Codes couleur
        s = MC_COLOR.matcher(s).replaceAll("");

        // 1) Préfixe [LvXX] / [LvlXX]
        s = LEVEL_PREFIX.matcher(s).replaceFirst("");

        // 2) Retire la vie en fin de string
        if (s.contains("/")) {
            s = TRAILING_HP_SLASH.matcher(s).replaceFirst("");
        } else {
            s = TRAILING_HP_SINGLE.matcher(s).replaceFirst("");
        }

        // 3) Normalisation (accents)
        s = Normalizer.normalize(s, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}+", "");

        // 4) Enlève symboles (garde lettres + espaces)
        s = s.replaceAll("[^\\p{L} ]+", " ");

        // 5) Cleanup
        s = s.trim().replaceAll("\\s+", " ");
        if (s.isEmpty()) return "";

        // 6) Upper snake
        return s.replace(' ', '_').toUpperCase(Locale.ROOT);
    }
}