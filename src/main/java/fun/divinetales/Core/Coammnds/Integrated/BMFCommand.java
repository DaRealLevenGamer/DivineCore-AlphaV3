package fun.divinetales.Core.Coammnds.Integrated;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static fun.divinetales.Core.Utils.ColorUtil.*;

public class BMFCommand implements CommandExecutor {

    @SuppressWarnings("unused")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("bmf.hat")) {
                msgPlayer(player, color(msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINEPLAYER)) + " &f&lYou do not have permission to run this command!");
                return true;
            }

            if (player.getInventory().getItemInMainHand().getType().isAir()) {
                msgPlayer(player, color(msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINEPLAYER)) + " &f&lThe item cannot be Air!");
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 2.0F, 0);
                return true;
            }

            ItemStack inHelmet = player.getInventory().getHelmet();

            if (inHelmet != null) {
                msgPlayer(player, color(msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINEPLAYER)) + " &f&lYou cannot run /hat with an item already in your helmet slot!");
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 2.0F, 0);
                return true;
            }

            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                if (inHelmet == null) {
                    player.getLocation().getWorld().spawnParticle(Particle.TOTEM, player.getLocation(), 90);
                    player.getInventory().setHelmet(player.getInventory().getItemInMainHand());
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 0);
                    msgPlayer(player, color(msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINEPLAYER)) + " &c&lItem Equipped!");
                    return true;
                }
            }
        }
        return false;
    }
}