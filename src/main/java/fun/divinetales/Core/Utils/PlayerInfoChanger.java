package fun.divinetales.Core.Utils;

import java.util.*;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Tasks.SkinApplier;
import fun.divinetales.Core.Utils.MYSQL.Data.SQLChangeSkin;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This class allows you to change a player's nametag
 * <p>
 * It requires ProtocolLib and only works on 1.8-1.8.3
 * If you need a 1.7 version, use TagAPI
 * </p>
 *
 * @author Techcable
 */
public class PlayerInfoChanger {
    private final Map<Player, String> fakeNames = new WeakHashMap<>();
    private final CoreMain plugin;
    
    private static final Map<UUID, ItemStack[]> invContents = new HashMap<>();
    private static final HashMap<UUID, ItemStack> helmet = new HashMap<>();
    private static final HashMap<UUID, ItemStack> chest = new HashMap<>();
    private static final HashMap<UUID, ItemStack> leg = new HashMap<>();
    private static final HashMap<UUID, ItemStack> boot = new HashMap<>();
    
    public PlayerInfoChanger(CoreMain plugin) {
        this.plugin = plugin;
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.PLAYER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacket().getPlayerInfoAction().read(0) != PlayerInfoAction.ADD_PLAYER) return;
                List<PlayerInfoData> newPlayerInfoDataList = new ArrayList<PlayerInfoData>();
                List<PlayerInfoData> playerInfoDataList = event.getPacket().getPlayerInfoDataLists().read(0);
                for (PlayerInfoData playerInfoData : playerInfoDataList) {
                    if (playerInfoData == null || playerInfoData.getProfile() == null || Bukkit.getPlayer(playerInfoData.getProfile().getUUID()) == null) { //Unknown Player
                        newPlayerInfoDataList.add(playerInfoData);
                        continue;
                    }
                    WrappedGameProfile profile = playerInfoData.getProfile();
                    profile = profile.withName(getNameToSend(profile.getUUID()));
                    PlayerInfoData newPlayerInfoData = new PlayerInfoData(profile, playerInfoData.getPing(), playerInfoData.getGameMode(), playerInfoData.getDisplayName());
                    newPlayerInfoDataList.add(newPlayerInfoData);
                }
                event.getPacket().getPlayerInfoDataLists().write(0, newPlayerInfoDataList);
            }
        });
    }

    private String getNameToSend(UUID id) {
        Player player = Bukkit.getPlayer(id);
        if (!fakeNames.containsKey(player)) return player.getName();
        return fakeNames.get(player);
    }

    public static void ChangeSkin(Player player) {
        UUID id = player.getUniqueId();
        SQLChangeSkin skins = new SQLChangeSkin(CoreMain.getInstance());
        GameProfile profile = ((CraftPlayer) player).getHandle().getProfile();
        WrappedGameProfile wrappedGameProfile = WrappedGameProfile.fromPlayer(player);
        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", new Property("textures", skins.getSkinTexture(id), skins.getSkinSignature(id)));
        SkinApplier applier = new SkinApplier(CoreMain.getInstance(), null, player, false);
        removeInventory(player);
        Bukkit.getScheduler().runTaskLater(CoreMain.getInstance(), () -> {
            applier.sendPacketsSelf(wrappedGameProfile);
            applier.sendUpdateOthers();
        }, 60L);
        Bukkit.getScheduler().runTaskLater(CoreMain.getInstance(), () -> {
            addInventory(player);
        }, 100L);
    }

    private static void addInventory(Player player) {

        if (invContents.containsKey(player.getUniqueId())) {
            player.getInventory().setHelmet(helmet.get(player.getUniqueId()));
            player.getInventory().setChestplate(chest.get(player.getUniqueId()));
            player.getInventory().setLeggings(leg.get(player.getUniqueId()));
            player.getInventory().setBoots(boot.get(player.getUniqueId()));
            player.getInventory().setContents(invContents.get(player.getUniqueId()));
            invContents.remove(player.getUniqueId());
            helmet.remove(player.getUniqueId());
            chest.remove(player.getUniqueId());
            leg.remove(player.getUniqueId());
            boot.remove(player.getUniqueId());
        }

    }

    private static void removeInventory(Player player) {

        invContents.put(player.getUniqueId(), player.getInventory().getContents());
        helmet.put(player.getUniqueId(), player.getInventory().getHelmet());
        chest.put(player.getUniqueId(), player.getInventory().getChestplate());
        leg.put(player.getUniqueId(), player.getInventory().getLeggings());
        boot.put(player.getUniqueId(), player.getInventory().getBoots());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        
    }

    public static void ChangeSkin(Player player, UUID id) {
        SQLChangeSkin skins = new SQLChangeSkin(CoreMain.getInstance());
        GameProfile profile = ((CraftPlayer) player).getHandle().getProfile();
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) player).getHandle()));
        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", new Property("textures", skins.getSkinTexture(id), skins.getSkinSignature(id)));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle()));

    }

    /**
     * Change the player's name to the provided string
     * <br>
     * The player may disappear for approximately 2 ticks after you change it
     * </br>
     * @param player player whos name to change
     * @param fakeName the player's new name
     */
    public void changeName(final Player player, String fakeName) {
        fakeNames.put(player, fakeName);
        refresh(player);
    }

    /**
     * Reset the player's name to it's original value
     * <br>
     * The player may disappear for approximately 2 ticks after you change it
     * </br>
     * @param player player whos name to change back to the original value
     */
    public void changeNameBack(Player player, String fakeName) {
        if (!fakeNames.containsKey(player))
            fakeNames.remove(player);
        refresh(player);
    }

    private void refresh(final Player player) {
        for (final Player forWhom : player.getWorld().getPlayers()) {
            if (player.equals(forWhom) || !player.getWorld().equals(forWhom.getWorld()) || !forWhom.canSee(player)) {
                forWhom.hidePlayer(player);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        forWhom.showPlayer(player);
                    }
                }.runTaskLater(plugin, 2);
            }
        }
    }
}
