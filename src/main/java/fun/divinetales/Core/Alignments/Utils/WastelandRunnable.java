package fun.divinetales.Core.Alignments.Utils;

import fun.divinetales.Core.Alignments.AlignmentManager;
import fun.divinetales.Core.Alignments.AlignmentType;
import fun.divinetales.Core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class WastelandRunnable extends BukkitRunnable {

    private final AlignmentManager manager = new AlignmentManager(CoreMain.getInstance());


    @Override
    public void run() {

        for (Player isWasteland : Bukkit.getOnlinePlayers()) {

            if (AlignmentManager.getPlayerState().get(isWasteland.getUniqueId()) == AlignmentType.WasteLand) {

                isWasteland.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 6000, Objects.requireNonNull(isWasteland.getPotionEffect(PotionEffectType.POISON)).getAmplifier() + 1, true, true, false));
            }

        }

    }


}
