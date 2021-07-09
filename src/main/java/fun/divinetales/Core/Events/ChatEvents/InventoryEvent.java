package fun.divinetales.Core.Events.ChatEvents;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.Data.ChatPlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEvent implements Listener {
    CoreMain main = CoreMain.getInstance();

    MessageUtils msg = this.main.getMsgUtil();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        try {
            if (p.getOpenInventory().getTitle().equalsIgnoreCase(this.msg.getCMessage(MessageUtils.Message.CHAT_MENU_TITLE))) {
                if (e.getView().getTitle().equalsIgnoreCase(this.msg.getCMessage(MessageUtils.Message.CHAT_MENU_TITLE))) {
                    ChatPlayerData mp = this.main.getPlayerManager().getPlayer(p.getUniqueId());
                    switch (e.getSlot()) {
                        case 0:
                            if (mp.hasEnabledLChat()) {
                                mp.setEnabledLChat(Boolean.FALSE);
                                this.msg.sendMessage(p, MessageUtils.Message.CHAT_NORECIEVE_LAND);
                                break;
                            }
                            mp.setEnabledLChat(Boolean.TRUE);
                            this.msg.sendMessage(p, MessageUtils.Message.CHAT_RECIEVE_LAND);
                            break;
                        case 1:
                            if (mp.hasEnabledGChat()) {
                                mp.setEnabledGChat(Boolean.FALSE);
                                this.msg.sendMessage(p, MessageUtils.Message.CHAT_NORECIEVE_ROLEPLAY);
                                break;
                            }
                            mp.setEnabledGChat(Boolean.TRUE);
                            this.msg.sendMessage(p, MessageUtils.Message.CHAT_RECIEVE_ROLEPLAY);
                            break;
                        case 2:
                            if (mp.hasGChat()) {
                                mp.setGChat(Boolean.FALSE);
                                this.msg.sendMessage(p, MessageUtils.Message.CHAT_NORECIEVE_GLOBAL);
                                break;
                            }
                            mp.setGChat(Boolean.TRUE);
                            this.msg.sendMessage(p, MessageUtils.Message.CHAT_RECIEVE_GLOBAL);
                            break;
                        case 4:
                            if (mp.hasGlobalChat() == 0) {
                                mp.setGlobalChat(1);
                                mp.save();
                                this.msg.sendMessage(p, MessageUtils.Message.DEFAULT_CHAT_SET, this.msg.getCMessage(MessageUtils.Message.GLOBAL_CHAT_NAME));
                                break;
                            }
                            if (mp.hasGlobalChat() == 1) {
                                mp.setGlobalChat(2);
                                this.msg.sendMessage(p, MessageUtils.Message.DEFAULT_CHAT_SET, this.msg.getCMessage(MessageUtils.Message.ROLEPLAY_CHAT_NAME));
                                break;
                            }
                            mp.setGlobalChat(0);
                            this.msg.sendMessage(p, MessageUtils.Message.DEFAULT_CHAT_SET, this.msg.getCMessage(MessageUtils.Message.LAND_CHAT_NAME));
                            break;
                    }
                    this.main.getInventoryUtil().setupChatInventory(p, mp.hasEnabledLChat(), mp.hasGlobalChat(), mp.hasEnabledGChat(), mp.hasGChat());
                }
                e.setCancelled(true);
            }
        } catch (Exception ignored) {}
    }
}

