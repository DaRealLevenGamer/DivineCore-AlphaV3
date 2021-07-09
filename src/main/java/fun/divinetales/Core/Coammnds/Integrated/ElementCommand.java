package fun.divinetales.Core.Coammnds.Integrated;

import fun.divinetales.Core.Coammnds.SubCommands.Elements.*;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.Data.PlayerData.ProfileCreateEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElementCommand implements TabExecutor {

    private ArrayList<SubCommand> subCommands = new ArrayList<>();
    MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

    public ElementCommand() {
        subCommands.add(new SetAir());
        subCommands.add(new SetEarth());
        subCommands.add(new SetFire());
        subCommands.add(new SetWater());
        subCommands.add(new SetRifted());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

             ProfileCreateEvent config = CoreMain.getInstance().getPlayerProfileManager().getPlayer(player.getUniqueId());

            if (!sender.hasPermission("staff.element")) {
                msgPlayer(player, this.msgUtil.getCReplaceMessage(MessageUtils.Message.NO_PERMISSION));
                return true;
            }

            if (args.length > 0) {

                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        if (args[0].equals("setCursed") && !sender.hasPermission("rifted.curse")) {
                            msgPlayer(player, this.msgUtil.getCReplaceMessage(MessageUtils.Message.EXCEPTION) + this.msgUtil.getCReplaceMessage(MessageUtils.Message.NO_PERMISSION));
                            return true;
                        }
                        Player player1 = Bukkit.getPlayer(args[1]);
                        try {
                            getSubCommands().get(i).perform(player, args);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        msgPlayer(player1, this.msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINEPLAYER) + color(" &f&lYou now have the " + config.getElement()));
                        msgPlayer(player, this.msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF) + color(" &f&lPlayer: " + args[1] + " &f&lNow has the " + config.getElement()));
                        return true;
                    }
                }

            }

            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                player.sendMessage(color("&7&l---------------&8&l>>") + this.msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF) + color("&8&l<<&7&l---------------"));
                for (int i = 0; i < getSubCommands().size(); i++) {

                    SubCommand suncommand = getSubCommands().get(i);

                   TextComponent msg = new TextComponent(suncommand.getSyntax());
                   msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(suncommand.getName())));
                   msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(suncommand.getDescription())));
                   msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suncommand.getSyntax()));

                   player.spigot().sendMessage(msg);


                }
                player.sendMessage(color("&7&l--------------------------------------------"));
                return true;
            }
        }

        return false;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {
            ArrayList<String> subCommandArgs = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++) {

                subCommandArgs.add(getSubCommands().get(i).getName());

            }
            return subCommandArgs;
        }

        if (args.length == 2) {
            ArrayList<String> names = new ArrayList<>();

            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);

            for (int i = 0; i < players.length; i++) {
                names.add(players[i].getName());
            }

            return names;
        }

        return null;
    }
}
