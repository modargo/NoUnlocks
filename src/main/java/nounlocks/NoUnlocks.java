package nounlocks;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class NoUnlocks implements
        PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(NoUnlocks.class.getName());

    public NoUnlocks() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new NoUnlocks();
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeTexture = new Texture("nounlocks/images/NoUnlocksBadge.png");
        BaseMod.registerModBadge(badgeTexture, "NoUnlocks", "modargo", "Locks all cards and relics that are normally unlocked by leveling up characters.", new ModPanel());
    }

    @SpirePatch2(clz = UnlockTracker.class, method = "addCard")
    public static class AddCardPatch {
        @SpirePostfixPatch
        public static void alwaysLock(String key) {
            if (!UnlockTracker.lockedCards.contains(key)) {
                UnlockTracker.lockedCards.add((key));
            }
        }
    }
    @SpirePatch2(clz = UnlockTracker.class, method = "addRelic")
    public static class AddRelicPatch {
        @SpirePostfixPatch
        public static void alwaysLock(String key) {
            if (!UnlockTracker.lockedRelics.contains(key)) {
                UnlockTracker.lockedRelics.add((key));
            }
        }
    }
}