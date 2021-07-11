package fun.divinetales.Core.Alignments.Utils;

import fun.divinetales.Core.Alignments.AlignmentManager;
import fun.divinetales.Core.Alignments.AlignmentType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class WastelandRunnable extends BukkitRunnable {

    @Override
    public void run() {

        for (Player isWasteland : Bukkit.getOnlinePlayers()) {

            if (AlignmentManager.getPlayerState().get(isWasteland.getUniqueId()) == AlignmentType.WasteLand) {

                if (isWasteland.hasPotionEffect(PotionEffectType.POISON)) {
                    isWasteland.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, Objects.requireNonNull(isWasteland.getPotionEffect(PotionEffectType.POISON)).getAmplifier() + 1, true, true, false));
                } else {
                    isWasteland.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 1, true, true, false));
                }

            }

        }

    }

}
