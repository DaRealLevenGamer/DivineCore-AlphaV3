package fun.divinetales.Core.Utils.Data.PlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfileManager {

    private final Map<UUID, ProfileCreateEvent> players = new HashMap<>();

    public ProfileCreateEvent getPlayer(UUID id) {
        return (this.players.get(id) == null) ? new ProfileCreateEvent(id) : this.players.get(id);
    }

    public void registerPlayer(UUID id) {
        this.players.put(id, getPlayer(id));
        getPlayer(id).load();
    }

    public void unregisterPlayer(UUID id) {
        getPlayer(id).save();
        this.players.remove(id);
    }

}
