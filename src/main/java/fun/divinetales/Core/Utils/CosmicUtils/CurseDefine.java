package fun.divinetales.Core.Utils.CosmicUtils;

import java.util.HashMap;
import java.util.UUID;

public class CurseDefine {

    public static HashMap<UUID, Double> reputation = new HashMap<>();

    public static HashMap<UUID, Integer> badrep = new HashMap<>();

    public static HashMap<UUID, Long> timer = new HashMap<>();

    public static double pkillrep = 1.0;

    public static boolean on = true;

    public static boolean klls = true;

    public static double stage1 = -25;

    public static double stage2 = -50;

    public static double stage3 = -75;

    public static double stage4 = -100;

    public static String heal = "&7[&9&lDivinePlayer&7] &f&lYou have been healed!";

    public static String permission;

    public static String enabled =  "&7[&9&lDivineStaff&7] &c&lThe curse has been made!";

    public static String disabled = "&7[&9&lDivineStaff&7] &a&lThe curse has been lifted!";

    public static String added = "&7[&9&lDivineStaff&7] &eYou added &c{rep} &eto &c{player}";

    public static String removed = "&7[&9&lDivineStaff&7] &eYou removed &c{rep} &efrom &c{player}";

    public static String number = "&7[&9&lDivineStaff&7] &cAmount should be a number!";

    public static String rep = "&7[&9&lDivineStaff&7] &eThis player &eHas &c{rep} &eReputation";

    public static String notplayed = "&7[&9&lDivineStaff&7] &cSelected player doesn't exist!";

    public static String reset = "&7[&9&lDivineStaff&7] &eReputation set to &c0 &efor selected player!";

}
