package fun.divinetales.Core.Alignments.Listeners;

import de.netzkronehd.WGRegionEvents.events.RegionEnterEvent;
import de.netzkronehd.WGRegionEvents.events.RegionLeaveEvent;
import fun.divinetales.Core.Alignments.AlignmentManager;
import fun.divinetales.Core.Alignments.AlignmentType;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.MYSQL.Data.RegionData.SQLRegionData;
import fun.divinetales.Core.Utils.MYSQL.Data.RegionData.SQLRegionWasteland;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

import static fun.divinetales.Core.Utils.ColorUtil.color;
import static fun.divinetales.Core.Utils.ColorUtil.msgPlayer;

public class WastelandListener implements Listener {

    private final SQLRegionData data = new SQLRegionData(CoreMain.getInstance());
    private final AlignmentManager manager = new AlignmentManager();
    private final SQLRegionWasteland wasteland =  new SQLRegionWasteland(CoreMain.getInstance());

    @EventHandler
    public void WastelandStateEnter(RegionEnterEvent e) {

        Player player = e.getPlayer();

        if (data.exists(e.getRegion().getId())) {
            if (data.getAlignment(e.getRegion().getId()).equals("TYPE_WASTELAND")) {
                manager.setAlignmentType(player, AlignmentType.WasteLand);
            }
            return;
        }

        if (wasteland.exists(e.getRegion().getId())) {
            manager.setAlignmentType(player, AlignmentType.WasteLand);
        }

    }

    @EventHandler
    public void onHeal(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();

        if (AlignmentManager.getPlayerState().get(player.getUniqueId()) == AlignmentType.WasteLand) {
            if (e.getItem().getType() == Material.GOLDEN_APPLE && !player.isOp() || e.getItem().getType() == Material.POTION && !player.isOp()) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void WastelandStateLeave(RegionLeaveEvent e) {

        Player player = e.getPlayer();

        if (data.exists(e.getRegion().getId())) {
            if (data.getAlignment(e.getRegion().getId()).equals("TYPE_WASTELAND")) {
                if (player.hasPotionEffect(PotionEffectType.POISON))
                    player.removePotionEffect(PotionEffectType.POISON);
            }
        }

        if (wasteland.exists(e.getRegion().getId())) {
            if (player.hasPotionEffect(PotionEffectType.POISON))
                player.removePotionEffect(PotionEffectType.POISON);
        }

    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

        Player player = e.getEntity();

        if (AlignmentManager.getPlayerState().get(player.getUniqueId()) == AlignmentType.WasteLand) {

            e.setDroppedExp(0);
            e.setKeepLevel(false);
            e.setKeepInventory(false);
            msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINEPLAYER) + color(" &c&lThe air might have been a bit to toxic, better luck next time!"));

        }

    }


}
