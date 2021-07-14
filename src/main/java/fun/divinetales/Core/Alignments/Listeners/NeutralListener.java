package fun.divinetales.Core.Alignments.Listeners;

import de.netzkronehd.WGRegionEvents.events.RegionEnterEvent;
import fun.divinetales.Core.Alignments.AlignmentManager;
import fun.divinetales.Core.Alignments.AlignmentType;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.MYSQL.Data.RegionData.SQLRegionData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static fun.divinetales.Core.Utils.ColorUtil.color;
import static fun.divinetales.Core.Utils.ColorUtil.msgPlayer;

public class NeutralListener implements Listener {

    private final SQLRegionData data = new SQLRegionData(CoreMain.getInstance());
    private final AlignmentManager manager = new AlignmentManager();
    public static final HashMap<UUID, ItemStack[]> inv = new HashMap<>();
    private static final HashMap<UUID, ItemStack> helmet = new HashMap<>();
    private static final HashMap<UUID, ItemStack> chest = new HashMap<>();
    private static final HashMap<UUID, ItemStack> leg = new HashMap<>();
    private static final HashMap<UUID, ItemStack> boot = new HashMap<>();


    @EventHandler
    public void NeutralStateEnter(RegionEnterEvent e) {

        Player player = e.getPlayer();

        if (data.exists(e.getRegion().getId())) {
            if (data.getAlignment(e.getRegion().getId()).equals("TYPE_NEUTRAL")) {
                manager.setAlignmentType(player, AlignmentType.Neutral);
            }
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

        Player player = e.getEntity();

        if (AlignmentManager.getPlayerState().get(player.getUniqueId()) == AlignmentType.Neutral) {

            e.setKeepLevel(true);
            e.setDroppedExp(0);

            List<ItemStack> newInv = new ArrayList<>();
            newInv.add(player.getInventory().getHelmet());
            newInv.add(player.getInventory().getChestplate());
            newInv.add(player.getInventory().getLeggings());
            newInv.add(player.getInventory().getBoots());

            helmet.put(player.getUniqueId(), player.getInventory().getHelmet());
            chest.put(player.getUniqueId(), player.getInventory().getChestplate());
            leg.put(player.getUniqueId(), player.getInventory().getLeggings());
            boot.put(player.getUniqueId(), player.getInventory().getBoots());

            ItemStack[] newStack = newInv.toArray(new ItemStack[0]);
            inv.put(player.getUniqueId(), newStack);
            e.getDrops().removeAll(newInv);

            msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINEPLAYER) + color(" &6&lYou died in a NeutralState,say bye bye to your items!"));

        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        if (inv.containsKey(player.getUniqueId())) {
            player.getInventory().setHelmet(helmet.get(player.getUniqueId()));
            player.getInventory().setChestplate(chest.get(player.getUniqueId()));
            player.getInventory().setLeggings(leg.get(player.getUniqueId()));
            player.getInventory().setBoots(boot.get(player.getUniqueId()));
            inv.remove(player.getUniqueId());
            helmet.remove(player.getUniqueId());
            chest.remove(player.getUniqueId());
            leg.remove(player.getUniqueId());
            boot.remove(player.getUniqueId());
        }
    }

}
