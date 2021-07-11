package fun.divinetales.Core.Alignments.Listeners;

import de.netzkronehd.WGRegionEvents.events.RegionEnterEvent;
import de.netzkronehd.WGRegionEvents.events.RegionLeaveEvent;
import fun.divinetales.Core.Alignments.AlignmentManager;
import fun.divinetales.Core.Alignments.AlignmentType;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.MYSQL.Data.RegionData.SQLRegionData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import static fun.divinetales.Core.Utils.ColorUtil.*;

public class LawfulListener implements Listener {

    private final SQLRegionData data = new SQLRegionData(CoreMain.getInstance());
    private final AlignmentManager manager = new AlignmentManager(CoreMain.getInstance());

    @EventHandler
    public void LawfulStateEnter(RegionEnterEvent e) {

        Player player = e.getPlayer();

        if (data.exists(e.getRegion().getId())) {
            if (data.getAlignment(e.getRegion().getId()).equals("TYPE_LAWFUL")) {
                manager.setAlignmentType(player, AlignmentType.Lawful);
            }
        }

    }

    @EventHandler
    public void LawfulStateLeave(RegionLeaveEvent e) {

        Player player = e.getPlayer();
        manager.setAlignmentType(player, AlignmentType.Neutral);

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

        Player player = e.getEntity();

        if (AlignmentManager.getPlayerState().get(player.getUniqueId()) == AlignmentType.Lawful) {
            e.setKeepInventory(true);
            e.setKeepLevel(true);
            e.setDroppedExp(0);
            msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINEPLAYER) + color(" &f&lYou have died in a Lawful state, and have lost nothing!"));
        }

    }

}
