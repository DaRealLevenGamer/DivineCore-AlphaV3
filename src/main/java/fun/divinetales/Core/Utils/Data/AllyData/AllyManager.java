package fun.divinetales.Core.Utils.Data.AllyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.Data.Config;
import org.bukkit.configuration.ConfigurationSection;
import ru.tehkode.permissions.PermissionGroup;

public class AllyManager {
    private final List<Ally> list = new ArrayList<>();

    private final Config cfg;

    public AllyManager(Config cfg) {
        this.cfg = cfg;
    }

    public void save() {
        this.cfg.set("allys", null);
        for (Ally ally : this.list) {
            List<String> newlist = new ArrayList<>();
            if (isValid(ally.getName())) {
                for (String str : ally.getAllys()) {
                    if (isValid(str) &&
                            !str.equalsIgnoreCase(ally.getName()))
                        newlist.add(str);
                }
                this.cfg.set("allys." + ally.getName(), newlist);
            }
        }
        this.cfg.save();
    }

    public void load() {
        ConfigurationSection sfg = this.cfg.getConfig().getConfigurationSection("allys");
        if (sfg != null)
            for (String str : sfg.getKeys(false)) {
                Ally a = create(str);
                try {
                    a.setAllys(this.cfg.getConfig().getStringList("allys." + a.getName()));
                } catch (Exception ignored) {

                }
            }
    }

    public Ally create(String aname) {
        if (get(aname) == null)
            this.list.add(new Ally(aname.toLowerCase()));
        return get(aname);
    }

    public Ally remove(String aname) {
        Ally a = get(aname);
        this.list.remove(a);
        return get(aname);
    }

    public Ally get(String aname) {
        for (Ally ally : this.list) {
            if (ally.getName().equalsIgnoreCase(aname))
                return ally;
        }
        return null;
    }

    public boolean isAlly(String arg1, String arg2) {
        if (get(arg1) == null || get(arg2) == null || arg1.equalsIgnoreCase(arg2))
            return false;
        return get(arg1).getAllys().contains(get(arg2).getName()) && get(arg2).getAllys().contains(get(arg1).getName());
    }

    public void setAlly(String arg1, String arg2, boolean ally) {
        Ally a = get(arg1);
        Ally b = get(arg2);
        if (a == null)
            a = create(arg1);
        if (b == null)
            b = create(arg2);
        if (ally) {
            a.addAlly(arg2);
            b.addAlly(arg1);
        } else {
            a.removeAlly(arg2);
            b.removeAlly(arg1);
        }
    }

    public boolean isValid(String aname) {
        byte b;
        int i;
        PermissionGroup[] arrayOfPermissionGroup;
        for (i = (arrayOfPermissionGroup = Objects.requireNonNull(CoreMain.getInstance().getPex()).getPermissionsManager().getGroups()).length, b = 0; b < i; ) {
            PermissionGroup group = arrayOfPermissionGroup[b];
            if (group.getOption("land", null, "").equalsIgnoreCase(aname))
                return true;
            b++;
        }
        return false;
    }
}


