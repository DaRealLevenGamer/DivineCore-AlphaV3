package fun.divinetales.Core;

import com.grinderwolf.swm.api.SlimePlugin;
import fun.divinetales.Core.Coammnds.*;
import fun.divinetales.Core.Dungeons.Commands.Dungeons;
import fun.divinetales.Core.Dungeons.Commands.DungeonTeamCreate;
import fun.divinetales.Core.Events.ChatEvents.*;
import fun.divinetales.Core.Events.MainEvent.CurseListeners;
import fun.divinetales.Core.Utils.ChatUtils.ChatInvUtils;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.CosmicUtils.CurseDefine;
import fun.divinetales.Core.Utils.CosmicUtils.CurseUtil;
import fun.divinetales.Core.Utils.Data.AllyData.AllyManager;
import fun.divinetales.Core.Utils.Data.ChatPlayerManager;
import fun.divinetales.Core.Utils.Data.Config;
import fun.divinetales.Core.Utils.Data.PlayerData.PlayerProfileManager;
import fun.divinetales.Core.Utils.InventoryUtils.GUIManager;
import fun.divinetales.Core.Utils.MYSQL.ConfigUtils;
import fun.divinetales.Core.Utils.MYSQL.Data.SQLPlayerData;
import fun.divinetales.Core.Utils.MYSQL.Data.SQLPlayerProfile;
import fun.divinetales.Core.Utils.MYSQL.Data.SQLPlayerSkins;
import fun.divinetales.Core.Utils.MYSQL.SQL;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import static fun.divinetales.Core.Utils.ColorUtil.*;

public class CoreMain extends JavaPlugin {

    //Starter
    private static CoreMain plugin;
    private static Logger logger;

    //Imports for the chat
    private MessageUtils msgUtil;
    private ChatPlayerManager pManager;
    private AllyManager aManager;
    private ChatInvUtils iutil;

    //FOR MYSQL
    private ConfigUtils configUtils;
    private SQL sql;
    private SQLPlayerData sqlPlayerData;
    private SQLPlayerProfile sqlPlayerProfile;
    private SQLPlayerSkins sqlPlayerSkins;

    //FOR GUI STUFF
    private GUIManager manager;

    //Imports for playerData
    private PlayerProfileManager playerProfileManager;

    //Setters for the chat
    private final HashMap<String, String> pexGroups = new HashMap<>();

    //This is the instance of the main class for the swm loader plugin
    SlimePlugin slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");

    @Override
    public void onEnable() {
        plugin = this;
        logger = getLogger();
/*        try {
            slimePlugin.registerLoader("my_loader", new DungeonLoader());
        } catch (IOException | UnknownWorldException | WorldInUseException exception) {
            exception.printStackTrace();
            log("DungeonLoader failed to load!");
        }*/
        Config config = new Config("config", Boolean.TRUE);
        Config msgc = new Config("Message", Boolean.TRUE);
        this.msgUtil = new MessageUtils(msgc);
        this.configUtils = new ConfigUtils(config);
        this.pManager = new ChatPlayerManager();
        //For MYSQL
        this.sql = new SQL();
        this.sqlPlayerProfile = new SQLPlayerProfile(this);
        this.sqlPlayerSkins = new SQLPlayerSkins(this);
        this.sqlPlayerData = new SQLPlayerData(this);
        if (configUtils.getBoolean("use_sql")) {
            try {
                sql.Connect();
            } catch (ClassNotFoundException | SQLException e) {
               if (configUtils.getBoolean("sql_debug")) {
                   e.printStackTrace();
               }
                Bukkit.getLogger().log(Level.SEVERE, "SQL Failed to load, Make sure your using a MySQLDataBase and that the info is correct!");
            }

            if (sql.isConnected()) {
                Bukkit.getLogger().log(Level.INFO, "The DataBase " + configUtils.getMessage(ConfigUtils.getConfig.MYSQLDataBase) + " Is connected!");
                sqlPlayerData.createPlayerTable();
                sqlPlayerProfile.createProfileTable();
                sqlPlayerSkins.createSkinTable();
            }

        }
        this.aManager = new AllyManager(new Config("allys", Boolean.FALSE));
        this.manager = new GUIManager(this);
        this.playerProfileManager = new PlayerProfileManager();
        this.aManager.load();
        this.iutil = new ChatInvUtils(plugin);
        //Registers events
        registerEvent(new ChatEvent(), new JoinEvent(), new QuitEvent(), new PvpEvents(),
                new InventoryEvent(), new JoinCommand(), new CurseListeners(new Config("Curse_Files", "PlayerRep", false)));
        //registers online players
        for (Player p : Bukkit.getOnlinePlayers()) {
            getInstance().getPlayerManager().registerPlayer(p.getUniqueId());
            getInstance().getPlayerProfileManager().registerPlayer(p.getUniqueId());
            UUID id = p.getUniqueId();
            if (CurseListeners.getRepConfig().getConfig().contains(id.toString())) {
                CurseDefine.reputation.put(id, CurseListeners.getRepConfig().getConfig().getDouble(id.toString()));
                if (CurseDefine.reputation.get(id) < 0.0D)
                    CurseUtil.stage(id);
            }
        }
        Objects.requireNonNull(getServer().getPluginCommand("chatspy")).setExecutor(new ChatSpyCommand());
        Objects.requireNonNull(getServer().getPluginCommand("ally")).setExecutor(new AllyCommand());
        Objects.requireNonNull(getServer().getPluginCommand("chat")).setExecutor(new ChatCommand());
        Objects.requireNonNull(getServer().getPluginCommand("join")).setExecutor(new JoinCommand());
        Objects.requireNonNull(getServer().getPluginCommand("mcreload")).setExecutor(new ReloadCommand());
        Objects.requireNonNull(getCommand("playerElement")).setExecutor(new ElementCommand());
        Objects.requireNonNull(getCommand("profile")).setExecutor(new ProfileCommand());
        Objects.requireNonNull(getCommand("hat")).setExecutor(new BMFCommand());
        Objects.requireNonNull(getCommand("curse")).setExecutor(new CosmicCommand());
        Objects.requireNonNull(getCommand("dungeonteam")).setExecutor(new DungeonTeamCreate());
        Objects.requireNonNull(getCommand("dungeons")).setExecutor(new Dungeons());

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (CurseDefine.on) {
                long current = System.currentTimeMillis();
                for (UUID u : CurseDefine.timer.keySet()) {
                    if (current >= CurseDefine.timer.get(u)) {
                        if (CurseDefine.badrep.containsKey(u)) {
                            CurseUtil.bpot(Bukkit.getPlayer(u));
                        } else if (CurseDefine.reputation.get(u) >= 25.0D) {
                            CurseUtil.gpot(Bukkit.getPlayer(u));
                        }
                        CurseDefine.timer.replace(u, CurseUtil.random());
                    }
                    if (CurseDefine.badrep.containsKey(u)) {
                        if (ThreadLocalRandom.current().nextInt() * 100 < 6)
                            Bukkit.getPlayer(u).getLocation().getWorld()
                                    .strikeLightning(Bukkit.getPlayer(u).getLocation());
                        if (ThreadLocalRandom.current().nextInt() * 100 < 6)
                            Bukkit.getPlayer(u).getLocation().getWorld().playSound(
                                    Bukkit.getPlayer(u).getLocation(), Sound.ENTITY_GHAST_SCREAM, 60.0F, 60.0F);
                    }
                }
            }
        }, 0L, 1200L);
        log("DivineCore-AlphaV3 has loaded successfully!");
    }

    @Override
    public void onDisable() {
        //Unresgisters MYSQL DataBase
        if (configUtils.getBoolean("use_sql")) {
            sql.Disconnect();
        }
        //Unregisters players when they leave
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID u = p.getUniqueId();
            CurseListeners.getRepConfig().getConfig().set(u.toString(), CurseDefine.reputation.get(u));
            CurseDefine.reputation.remove(u);
            CurseDefine.timer.remove(u);
            CurseDefine.badrep.remove(u);
            CurseListeners.getRepConfig().save();
            getInstance().getPlayerManager().unregisterPlayer(p.getUniqueId());
            getInstance().getPlayerProfileManager().unregisterPlayer(p.getUniqueId());
        }
        this.aManager.save();
        log("DivineCore-AlphaV3 has been disabled!");
    }

    private void registerEvent(Listener... listener) {
        byte b;
        int i;
        Listener[] arrayOfListener;
        for (i = (arrayOfListener = listener).length, b = 0; b < i; ) {
            Listener l = arrayOfListener[b];
            getServer().getPluginManager().registerEvents(l, this);
            b++;
        }
    }

    public static CoreMain getInstance() {
        return plugin;
    }

    public static Logger getPluginLogger() {
        return logger;
    }

    //Gets the pexGroups groups
    public HashMap<String, String> getPexGroups() {
        return this.pexGroups;
    }

    //Getters for the chat imports
    public ChatPlayerManager getPlayerManager() {
        return this.pManager;
    }

    public MessageUtils getMsgUtil() {
        return this.msgUtil;
    }

    public ConfigUtils getConfigUtils() {
        return this.configUtils;
    }
    public SQL getSql() {
        return this.sql;
    }

    public SQLPlayerData getSqlPlayerData() {
        return sqlPlayerData;
    }

    public SQLPlayerSkins getSqlPlayerSkins() {
        return sqlPlayerSkins;
    }

    public SQLPlayerProfile getSqlPlayerProfile() {
        return sqlPlayerProfile;
    }

    public AllyManager getAllyManager() {
        return this.aManager;
    }

    public ChatInvUtils getInventoryUtil() {
        return this.iutil;
    }

    public GUIManager getGUIManager() {
        return manager;
    }

    //Getters for playerProfiles
    public PlayerProfileManager getPlayerProfileManager() {
        return  this.playerProfileManager;
    }

    //Gets Pex
    public PermissionsEx getPex() {
        try {
            return (PermissionsEx) getServer().getPluginManager().getPlugin("PermissionsEx");
        } catch (Exception e) {
            return null;
        }

    }
}
