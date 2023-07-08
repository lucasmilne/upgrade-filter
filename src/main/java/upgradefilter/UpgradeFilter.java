package upgradefilter;

import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Properties;

@SpireInitializer
public class UpgradeFilter implements
        PostInitializeSubscriber,
        EditStringsSubscriber, PostUpdateSubscriber {

    private static SpireConfig modConfig = null;
    private static String modID = "upgradefilter";

    public static final Logger logger = LogManager.getLogger(UpgradeFilter.class);

    public static void initialize() {
        BaseMod.subscribe(new UpgradeFilter());
        try {
            Properties defaults = new Properties();
            modConfig = new SpireConfig(getModID(), "Config", defaults);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void receivePostInitialize() {
    }

    @Override
    public void receiveEditStrings() {
        loadLocStrings("eng");
        if (!languageSupport().equals("eng")) {
            loadLocStrings(languageSupport());
        }
    }

    public static String getModID() {
        return modID;
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private String languageSupport() {
        String language = Settings.language.name().toLowerCase();
        String urlPath = getModID() + "Resources/localization/" + language + "/UI-Strings.json";
        ClassLoader classLoader = UIStrings.class.getClassLoader();
        URL url = classLoader.getResource(urlPath);

        if (url != null) {
            return language;
        } else {
            return "eng";
        }

    }

    private void loadLocStrings(String language) {
        BaseMod.loadCustomStringsFile(UIStrings.class, getModID() + "/localization/" + language + "/UI-Strings.json");
    }
    public static String makePath(String resourcePath) {
        return getModID() + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return getModID() + "Resources/img/" + resourcePath;
    }

    public static float time = 0f;

    @Override
    public void receivePostUpdate() {
        time += Gdx.graphics.getRawDeltaTime();
    }
}