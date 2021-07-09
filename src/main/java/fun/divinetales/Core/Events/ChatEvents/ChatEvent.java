package fun.divinetales.Core.Events.ChatEvents;

import java.util.UUID;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import static fun.divinetales.Core.Utils.ColorUtil.*;

import fun.divinetales.Core.Utils.ChatUtils.RPName;
import fun.divinetales.Core.Utils.Data.ChatPlayerData;
import fun.divinetales.Core.Utils.Data.Config;
import fun.divinetales.Core.Utils.Data.PlayerData.ProfileCreateEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
    MessageUtils msg = CoreMain.getInstance().getMsgUtil();


    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent e) {
        Player pl = e.getPlayer();
        UUID id = pl.getUniqueId();
        ChatPlayerData p = CoreMain.getInstance().getPlayerManager().getPlayer(id);
        ProfileCreateEvent playerProfile = CoreMain.getInstance().getPlayerProfileManager().getPlayer(id);
        if (e.getMessage().startsWith("@") || (p.hasGlobalChat() == 1 && !e.getMessage().startsWith("!")) || (p.hasGlobalChat() != 2 && p.getLanden().isEmpty() && !e.getMessage().startsWith("!"))) {
            if (!p.hasGChat())
                this.msg.sendMessage(pl, MessageUtils.Message.CHAT_GLOBAL_DISABLED);
            if (e.getMessage().startsWith("@")) {
                e.setMessage(e.getMessage().replaceFirst("@", ""));
            } else if (e.getMessage().startsWith("@")) {
                e.setMessage(e.getMessage().replaceFirst("@", ""));
            }
            e.setFormat(this.msg.getCReplaceMessage(MessageUtils.Message.GLOBAL_FORMAT, pl.getName(), pl.getDisplayName(), p.getPrefix(this.msg.getBoolean("multiprefix")), p.getSuffix(this.msg.getBoolean("multisuffix")), p.getOwnPrefix(), p.getOwnSuffix()));
            for (Player on : Bukkit.getOnlinePlayers()) {
                if (on != pl) {
                    ChatPlayerData online = CoreMain.getInstance().getPlayerManager().getPlayer(on.getUniqueId());
                    if (!online.hasGChat())
                        e.getRecipients().remove(on);
                    if (!e.getRecipients().contains(on) && online.hasSpy()) {
                        if (!on.hasPermission("mc.chat.spy")) {
                            online.setSpy(Boolean.FALSE);
                            continue;
                        }
                        on.sendMessage(this.msg.getCReplaceMessage(MessageUtils.Message.SPY_FORMAT, pl.getName(), pl.getDisplayName(), p.getPrefix(this.msg.getBoolean("multiprefix")), p.getSuffix(this.msg.getBoolean("multisuffix")), p.getOwnPrefix(), p.getOwnSuffix(), e.getMessage()));
                    }
                }
            }
        } else if (e.getMessage().startsWith("!") || p.hasGlobalChat() == 2) {
            if (!p.hasEnabledGChat())
                this.msg.sendMessage(pl, MessageUtils.Message.CHAT_ROLEPLAY_DISABLED);
            if (e.getMessage().startsWith("! ")) {
                e.setMessage(e.getMessage().replaceFirst("! ", ""));
            } else if (e.getMessage().startsWith("!")) {
                e.setMessage(e.getMessage().replaceFirst("!", ""));
            }
            if (!playerProfile.getNameEnabled()) {
                e.setFormat(this.msg.getCReplaceMessage(MessageUtils.Message.ROLEPLAY_FORMAT, pl.getName(), pl.getDisplayName(), p.getPrefix(this.msg.getBoolean("multiprefix")), p.getSuffix(this.msg.getBoolean("multisuffix")), p.getOwnPrefix(), p.getOwnSuffix(), e.getMessage()));
                for (Player on : Bukkit.getOnlinePlayers()) {
                    ChatPlayerData online = CoreMain.getInstance().getPlayerManager().getPlayer(on.getUniqueId());
                    if (on != pl) {
                        if (!online.hasEnabledGChat())
                            e.getRecipients().remove(on);
                    }
                }
            } else if (playerProfile.getNameEnabled()) {
                e.setFormat(this.msg.getCReplaceMessage(MessageUtils.Message.ROLEPLAY_FORMAT, color(RPName.getName(pl)), color(RPName.getName(pl)), p.getPrefix(this.msg.getBoolean("multiprefix")), p.getSuffix(this.msg.getBoolean("multisuffix")), p.getOwnPrefix(), p.getOwnSuffix(), e.getMessage()));
                String format = e.getFormat();
                TextComponent newFormat = new TextComponent(format);
                TextComponent playerFormat = new TextComponent(format);
                newFormat.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(pl.getName()).create()));
                e.setCancelled(true);
                for (Player on : Bukkit.getOnlinePlayers()) {
                    if (on.isOp() || on.hasPermission("chat.identify.core")) {
                        on.spigot().sendMessage(newFormat);
                    } else {
                        on.spigot().sendMessage(playerFormat);
                    }
                    ChatPlayerData online = CoreMain.getInstance().getPlayerManager().getPlayer(on.getUniqueId());
                    if (on != pl) {
                        if (!online.hasEnabledGChat()) {
                            e.getRecipients().remove(on);
                        }
                    }
                }
            }
        } else {
            if (!p.hasEnabledLChat())
                this.msg.sendMessage(pl, MessageUtils.Message.CHAT_LAND_DISABLED);
            e.getRecipients().clear();
            for (Player on : Bukkit.getOnlinePlayers()) {
                ChatPlayerData online = CoreMain.getInstance().getPlayerManager().getPlayer(on.getUniqueId());
                if (on == pl) {
                    e.getRecipients().add(on);
                    continue;
                }
                for (String land : p.getLanden()) {
                    if (online.hasLand(land) && online.hasEnabledLChat())
                        e.getRecipients().add(on);
                }
                if (online.hasSpy() && !e.getRecipients().contains(on)) {
                    if (!on.hasPermission("mc.chat.spy")) {
                        online.setSpy(Boolean.FALSE);
                        continue;
                    }
                    on.sendMessage(this.msg.getCReplaceMessage(MessageUtils.Message.SPY_FORMAT, pl.getName(), pl.getDisplayName(), p.getPrefix(this.msg.getBoolean("multiprefix")), p.getSuffix(this.msg.getBoolean("multisuffix")), p.getOwnPrefix(), p.getOwnSuffix(), e.getMessage()));
                }
            }
            e.setFormat(this.msg.getCReplaceMessage(MessageUtils.Message.LAND_FORMAT, pl.getName(), pl.getDisplayName(), p.getPrefix(this.msg.getBoolean("multiprefix")), p.getSuffix(this.msg.getBoolean("multisuffix")), p.getOwnPrefix(), p.getOwnSuffix()));
        }
        e.setFormat(e.getFormat().replace("{6}", "%2$s"));
        e.setMessage(this.msg.getColloredMSG(e.getMessage(), pl.hasPermission("mc.chat.color"), pl.hasPermission("mc.chat.format")));
    }
}

