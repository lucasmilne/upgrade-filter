package com.lockeddoors.upgradefilter;

import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.net.URL;

@SpireInitializer
public class UpgradeFilter implements EditStringsSubscriber {
    private static final String modID = "upgradefilter";

    public static void initialize() {
        BaseMod.subscribe(new UpgradeFilter());
    }
    @Override
    public void receiveEditStrings() {
        loadLocStrings("eng");
        if (!languageSupport().equals("eng")) {
            loadLocStrings(languageSupport());
        }
    }

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    private String languageSupport() {
        String language = Settings.language.name().toLowerCase();
        String path = getUIStringsLocation(language);
        ClassLoader classLoader = UIStrings.class.getClassLoader();
        URL url = classLoader.getResource(path);

        if (url != null) {
            return language;
        } else {
            return "eng";
        }

    }

    private void loadLocStrings(String language) {
        String path = getUIStringsLocation(language);
        BaseMod.loadCustomStringsFile(UIStrings.class, path);
    }

    private String getUIStringsLocation(String language) {
        String path = this.getClass().getPackage().getName().replace(".", "/");
        return path + "/localization/" + language + "/UI-Strings.json";
    }
}
