package com.lockeddoors.upgradefilter.patch;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.MasterDeckSortHeader;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import com.megacrit.cardcrawl.screens.mainMenu.SortHeaderButton;

import java.util.Comparator;

public class UpgradeFilterPatches {

    @SpirePatch(clz = MasterDeckSortHeader.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
            MasterDeckViewScreen.class
    })
    public static class AddUpgradeFilterButton {
        @SpirePrefixPatch
        public static void patch(MasterDeckSortHeader __instance) {
            SortHeaderButton upgradeButton = new SortHeaderButton("Upgraded", MasterDeckSortHeader.START_X, 0.0F, __instance);
            SortHeaderButton[] newButtons = new SortHeaderButton[__instance.buttons.length + 1];
            for (int i = 0; i < __instance.buttons.length; i++) {
                newButtons[i] = __instance.buttons[i];
            }
            newButtons[newButtons.length - 1] = upgradeButton;
            __instance.buttons = newButtons;
        }
    }

    @SpirePatch(clz = MasterDeckSortHeader.class, method = "didChangeOrder", paramtypez = {
            SortHeaderButton.class,
            boolean.class
    })
    public static class ApplyUpgradeFilter {
        private static final Comparator<AbstractCard> BY_UPGRADE = (Comparator.comparing(a -> (!a.upgraded + a.name)));;
        @SpirePrefixPatch
        public static void patch(MasterDeckSortHeader __instance, SortHeaderButton button, boolean isAscending, MasterDeckViewScreen ___masterDeckView) {
            Comparator<AbstractCard> order = BY_UPGRADE;
            button.setActive(true);
            if (button == __instance.buttons[__instance.buttons.length - 1]) {
                ___masterDeckView.setSortOrder(order);
                if (!isAscending) {
                    order = order.reversed();
                    ___masterDeckView.setSortOrder(order);
                }
            }
        }

    }
}
