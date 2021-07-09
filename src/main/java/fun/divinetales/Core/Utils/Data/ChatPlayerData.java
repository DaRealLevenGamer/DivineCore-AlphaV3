package fun.divinetales.Core.Utils.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import fun.divinetales.Core.CoreMain;
import org.bukkit.Bukkit;
import ru.tehkode.permissions.PermissionGroup;

public class ChatPlayerData {
    private final UUID id;

    private final Config data;

    private Boolean spy = Boolean.FALSE;

    private Boolean landchatenabled = Boolean.TRUE;

    private Boolean gchat = Boolean.TRUE;

    private Boolean globalchatenabled = Boolean.TRUE;

    private int globalchat = 0;

    public ChatPlayerData(UUID id) {
        this.id = id;
        this.data = new Config("PlayerData", id.toString(), Boolean.FALSE);
    }

    public void load() {
        this.spy = this.data.getConfig().getBoolean("spy", false);
        this.landchatenabled = this.data.getConfig().getBoolean("lc", true);
        this.globalchatenabled = this.data.getConfig().getBoolean("gc", true);
        this.gchat = this.data.getConfig().getBoolean("gchat", true);
        this.globalchat = this.data.getConfig().getInt("toggle", 0);
    }

    public void save() {
        this.data.set("name", Bukkit.getOfflinePlayer(this.id).getName());
        this.data.set("spy", this.spy);
        this.data.set("lc", this.landchatenabled);
        this.data.set("gc", this.globalchatenabled);
        this.data.set("toggle", this.globalchat);
        this.data.save();
    }

    public Boolean hasSpy() {
        return this.spy;
    }

    public void setSpy(Boolean spy) {
        this.spy = spy;
    }

    public int hasGlobalChat() {
        return this.globalchat;
    }

    public void setGlobalChat(int globalchat) {
        this.globalchat = globalchat;
    }

    public Boolean hasGChat() {
        return this.gchat;
    }

    public void setGChat(Boolean gchat) {
        this.gchat = gchat;
    }

    public Boolean hasEnabledLChat() {
        return this.landchatenabled;
    }

    public void setEnabledLChat(Boolean landchatenabled) {
        this.landchatenabled = landchatenabled;
    }

    public Boolean hasEnabledGChat() {
        return this.globalchatenabled;
    }

    public void setEnabledGChat(Boolean globalchatenabled) {
        this.globalchatenabled = globalchatenabled;
    }

    public List<String> getLanden() {
        List<String> list = new ArrayList<>();
        byte b;
        int i;
        PermissionGroup[] arrayOfPermissionGroup;
        for (i = (arrayOfPermissionGroup = Objects.requireNonNull(CoreMain.getInstance().getPex()).getPermissionsManager().getUser(this.id).getGroups()).length, b = 0; b < i; ) {
            PermissionGroup group = arrayOfPermissionGroup[b];
            String land = group.getOption("land", null, "");
            if (!land.equals("") && !list.contains(land.toLowerCase()))
                list.add(land.toLowerCase());
            b++;
        }
        return list;
    }

    public boolean hasLand(String landn) {
        for (String land : getLanden()) {
            if (land.equalsIgnoreCase(landn))
                return true;
        }
        return false;
    }

    public String getPrefix(boolean multi) {
        StringBuilder newmsg = new StringBuilder();
        byte b;
        int i;
        PermissionGroup[] arrayOfPermissionGroup;
        for (i = (arrayOfPermissionGroup = Objects.requireNonNull(CoreMain.getInstance().getPex()).getPermissionsManager().getUser(this.id).getGroups()).length, b = 0; b < i; ) {
            PermissionGroup group = arrayOfPermissionGroup[b];
            newmsg.append(group.getPrefix());
            if (!multi)
                break;
            b++;
        }
        return newmsg.toString();
    }

    public String getSuffix(boolean multi) {
        StringBuilder newmsg = new StringBuilder();
        byte b;
        int i;
        PermissionGroup[] arrayOfPermissionGroup;
        for (i = (arrayOfPermissionGroup = Objects.requireNonNull(CoreMain.getInstance().getPex()).getPermissionsManager().getUser(this.id).getGroups()).length, b = 0; b < i; ) {
            PermissionGroup group = arrayOfPermissionGroup[b];
            newmsg.append(group.getSuffix());
            if (!multi)
                break;
            b++;
        }
        return newmsg.toString();
    }

    public String getOwnPrefix() {
        try {
            return Objects.requireNonNull(CoreMain.getInstance().getPex()).getPermissionsManager().getUser(this.id).getPrefix();
        } catch (Exception e) {
            return "";
        }
    }

    public String getOwnSuffix() {
        try {
            return Objects.requireNonNull(CoreMain.getInstance().getPex()).getPermissionsManager().getUser(this.id).getSuffix();
        } catch (Exception e) {
            return "";
        }
    }
}

