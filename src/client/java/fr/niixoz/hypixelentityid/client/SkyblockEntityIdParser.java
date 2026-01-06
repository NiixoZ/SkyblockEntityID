package fr.niixoz.hypixelentityid.client;

import java.text.Normalizer;
import java.util.Locale;

public final class SkyblockEntityIdParser {
    private SkyblockEntityIdParser() {}

    /**
     * Convertit un nom d'ArmorStand Hypixel du style:
     * "[Lv55] ⊙☠ Obsidian Defender 10,000/10,000❤"
     * en:
     * "OBSIDIAN_DEFENDER"
     */
    public static String toEntityId(String armorStandLabel) {
        if (armorStandLabel == null) return "";

        String s = armorStandLabel;

        // 0) Supprime les codes couleur Minecraft (au cas où)
        s = s.replaceAll("§.", "");

        // 1) Supprime le préfixe [LvXX] (ou [LvlXX] si jamais)
        s = s.replaceAll("^\\s*\\[(?:Lv|Lvl)\\s*\\d+\\]\\s*", "");

        // 2) Supprime la partie HP en fin de string: " 10,000/10,000❤"
        // On enlève depuis le premier " <digits> / <digits>" jusqu'à la fin
        s = s.replaceAll("\\s+\\d[\\d,]*\\s*/\\s*\\d[\\d,]*.*$", "");

        // 3) Normalise unicode (retire accents)
        s = Normalizer.normalize(s, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}+", ""); // marks (accents)

        // 4) Enlève tout ce qui n'est pas lettre / espace (supprime les symboles Hypixel)
        // \p{L} = toutes les lettres unicode
        s = s.replaceAll("[^\\p{L} ]+", " ");

        // 5) Nettoyage: espaces multiples -> 1 espace
        s = s.trim().replaceAll("\\s+", " ");
        if (s.isEmpty()) return "";

        // 6) SPACES -> UNDERSCORE + UPPERCASE
        return s.replace(' ', '_').toUpperCase(Locale.ROOT);
    }
}