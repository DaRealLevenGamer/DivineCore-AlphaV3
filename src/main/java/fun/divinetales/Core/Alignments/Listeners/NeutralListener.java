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
    private final AlignmentManager manager = new AlignmentManager(CoreMain.getInstance());
    public static final HashMap<UUID, ItemStack[]> inv = new HashMap<>();

    @EventHandler
    public void NeutralStateEnter(RegionEnterEvent e) {

        Player player = e.getPlayer();

        if (data.exists(e.getRegion().getId())) {
            if (data.getAlignment(e.getRegion().getId()).equals("Neutral")) {
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

            ItemStack[] newStack = newInv.toArray(new ItemStack[0]);
            inv.put(player.getUniqueId(), newStack);
            e.getDrops().removeAll(newInv);

            msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINEPLAYER) + color(" &6&lYou died in a NeutralState, bye bye to your items!"));

        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        ItemStack[] newInv = inv.get(player.getUniqueId());

        if (inv.containsKey(player.getUniqueId())) {
            player.getInventory().setArmorContents(newInv);
            inv.remove(player.getUniqueId());
            player.sendMessage(color("&c&lYou died in a neutral state!"));
        }
    }

}
