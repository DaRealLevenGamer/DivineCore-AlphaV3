package fun.divinetales.Core.Alignments;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static fun.divinetales.Core.Utils.ColorUtil.*;

public class AlignmentManager {

    private final CoreMain plugin;

    public AlignmentManager(CoreMain main) {
        this.plugin = main;
    }
    private static final HashMap<UUID, AlignmentType> playerState = new HashMap<>();

    public void setAlignmentType(Player player, AlignmentType aType) {

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

        switch (aType) {

            case Neutral:

                player.sendTitle("", color("&6&lYou have entered a Neutral state!"), 20, 60, 20);
                playerState.remove(player.getUniqueId());
                playerState.put(player.getUniqueId(), AlignmentType.Neutral);
                break;

            case Lawful:

                player.sendTitle("", color("&f&lYou have entered a Lawful state!"), 20, 60, 20);
                playerState.remove(player.getUniqueId());
                playerState.put(player.getUniqueId(), AlignmentType.Lawful);
                break;

            case Chaotic:

                player.sendTitle("", color("&4&lYou have entered a Chaotic state!"), 20, 60, 20);
                playerState.remove(player.getUniqueId());
                playerState.put(player.getUniqueId(), AlignmentType.Chaotic);
                break;

            case WasteLand:

                player.sendTitle("", color("&c&lYou have entered a Wasteland!"), 20, 60, 20);
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 1, true, true, false));
                playerState.remove(player.getUniqueId());
                playerState.put(player.getUniqueId(), AlignmentType.WasteLand);
                break;
        }

    }

    public static HashMap<UUID, AlignmentType> getPlayerState() {
        return playerState;
    }

    public void cleanState() {
        playerState.clear();
    }
}
