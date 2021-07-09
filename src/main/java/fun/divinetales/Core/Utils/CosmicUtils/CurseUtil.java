package fun.divinetales.Core.Utils.CosmicUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CurseUtil {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void stage(UUID p) {
        Double i = CurseDefine.reputation.get(p);
        if (i <= CurseDefine.stage1 && i >= CurseDefine.stage2) {
            CurseDefine.badrep.put(p, 1);
        } else if (i <= CurseDefine.stage2 && i >= CurseDefine.stage3) {
            CurseDefine.badrep.put(p, 2);
        } else if (i <= CurseDefine.stage3 && i >= CurseDefine.stage4) {
            CurseDefine.badrep.put(p, 3);
        } else if (i <= CurseDefine.stage4) {
            CurseDefine.badrep.put(p, 4);
        }
    }

    public static void stagerep(UUID p) {
        Double i = CurseDefine.reputation.get(p);
        if (i <= CurseDefine.stage1 && i >= CurseDefine.stage2) {
            CurseDefine.badrep.replace(p, 1);
        } else if (i <= CurseDefine.stage2 && i >= CurseDefine.stage3) {
            CurseDefine.badrep.replace(p, 2);
        } else if (i <= CurseDefine.stage3 && i >= CurseDefine.stage4) {
            CurseDefine.badrep.replace(p, 3);
        } else if (i <= CurseDefine.stage4) {
            CurseDefine.badrep.replace(p, 4);
        }
    }

    public static Boolean number(String s) {
        try {
            Double.parseDouble(s);
            return Boolean.TRUE;
        } catch (NumberFormatException e) {
            return Boolean.FALSE;
        }
    }

    public static long random() {
        return System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(ThreadLocalRandom.current().nextInt(5, 30));
    }

    public static void bpot(Player p) {
        switch (ThreadLocalRandom.current().nextInt(0, 9)) {
            case 0:
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 2));
                return;
            case 1:
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 2));
                return;
            case 2:
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 2));
                return;
            case 3:
                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 2));
                return;
            case 4:
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 2));
                return;
            case 5:
                p.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 60, 2));
                return;
            case 6:
                p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 2));
                return;
            case 7:
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 2));
                return;
            case 8:
                p.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, 400, 2));
                return;
            case 9:
                p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 80, 2));
                return;
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, 400, 2));
    }

    public static void gpot(Player p) {
        switch (ThreadLocalRandom.current().nextInt(0, 8)) {
            case 0:
                p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 300, 2));
                return;
            case 1:
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 2));
                return;
            case 2:
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 2));
                return;
            case 3:
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 2));
                return;
            case 4:
                p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 2));
                return;
            case 5:
                p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 400, 2));
                return;
            case 6:
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 2));
                return;
            case 7:
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 2));
                return;
            case 8:
                p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 400, 2));
                return;
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 400, 2));
    }

}
