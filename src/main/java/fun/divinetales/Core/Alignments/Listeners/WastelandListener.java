package fun.divinetales.Core.Alignments.Listeners;

import de.netzkronehd.WGRegionEvents.events.RegionEnterEvent;
import fun.divinetales.Core.Alignments.AlignmentManager;
import fun.divinetales.Core.Alignments.AlignmentType;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.MYSQL.Data.RegionData.SQLRegionData;
import fun.divinetales.Core.Utils.MYSQL.Data.RegionData.SQLRegionWasteland;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static fun.divinetales.Core.Utils.ColorUtil.color;
import static fun.divinetales.Core.Utils.ColorUtil.msgPlayer;

public class WastelandListener implements Listener {

    private final SQLRegionData data = new SQLRegionData(CoreMain.getInstance());
    private final AlignmentManager manager = new AlignmentManager(CoreMain.getInstance());
    private final SQLRegionWasteland wasteland =  new SQLRegionWasteland(CoreMain.getInstance());

    @EventHandler
    public void WastelandStateEnter(RegionEnterEvent e) {

        Player player = e.getPlayer();

        if (data.exists(e.getRegion().getId())) {
            if (data.getAlignment(e.getRegion().getId()).equals("Wasteland") || wasteland.exists(e.getRegion().getId())) {
                manager.setAlignmentType(player, AlignmentType.WasteLand);
            }
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
