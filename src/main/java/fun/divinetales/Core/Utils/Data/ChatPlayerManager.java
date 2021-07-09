package fun.divinetales.Core.Utils.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatPlayerManager {
    private final Map<UUID, ChatPlayerData> players = new HashMap<>();

    public ChatPlayerData getPlayer(UUID id) {
        return (this.players.get(id) == null) ? new ChatPlayerData(id) : this.players.get(id);
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

