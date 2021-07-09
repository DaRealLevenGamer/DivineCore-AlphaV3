package fun.divinetales.Core.Utils.Data.AllyData;

import java.util.ArrayList;
import java.util.List;

public class Ally {
    private final String name;

    private final List<String> ally = new ArrayList<>();

    public Ally(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addAlly(String aname) {
        if (!this.ally.contains(aname))
            this.ally.add(aname.toLowerCase());
    }

    public void removeAlly(String aname) {
        this.ally.remove(aname.toLowerCase());
    }

    public List<String> getAllys() {
        return this.ally;
    }

    public void setAllys(List<String> list) {
        this.ally.addAll(list);
    }

    public void removeAllys(List<String> list) {
        this.ally.removeAll(list);
    }
}

