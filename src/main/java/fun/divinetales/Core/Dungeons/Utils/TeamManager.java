package fun.divinetales.Core.Dungeons.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class TeamManager {

    private static TeamManager instance;

    private HashMap<String, Teams> teams = new HashMap<>();
    private HashMap<UUID, String> players = new HashMap<>();

    public TeamManager() {
        instance = this;
    }

    public boolean createTeam(Player owner, String name) {
        if (players.containsKey(owner.getUniqueId()))
            return false;
        Teams team = new Teams(owner, name);
        teams.put(name, team);
        players.put(owner.getUniqueId(), name);
        return true;
    }

    public boolean deleteTeam(Player owner, String name) {

        Teams team = teams.get(name);
        String rank = team.getRank(owner);

        if (!rank.equals("owner")){
            return false;
        }
        if (!players.containsKey(owner.getUniqueId())) {
            return false;
        }
        teams.remove(name);
        players.remove(owner.getUniqueId(), name);
        return true;
    }

    public boolean addPlayer(Player player, String name, String rank) {

        if (teams.containsKey(name) && !players.containsKey(player.getUniqueId())) {
            Teams team = teams.get(name);
            team.addMember(player, rank);
            players.put(player.getUniqueId(), name);
            return true;
        }
        return false;
    }

    public void removePlayer(Player player) {
        if (players.containsKey(player.getUniqueId())) {
            String name = players.get(player.getUniqueId());
            players.remove(player.getUniqueId());
            Teams team = teams.get(name);
            String rank = team.getRank(player);
            team.removeMember(player);
            if (rank.equals("owner")) {
                Set<UUID> members = team.getMembers();
                for (UUID uuid : members) {
                    players.remove(uuid);
                }
                teams.remove(name);
            }
        }
    }

    public String getPlayerRole(Player player) {

        if (players.containsKey(player.getUniqueId())) {

            String name = players.get(player.getUniqueId());
            Teams team = teams.get(name);
            return team.getRank(player);
        }
        return null;
    }

    public String getTeamOwner(String team) {

        if (teams.containsKey(team)) {
            Teams teams1 = teams.get(team);
            UUID id = teams1.getOwner();
            return Bukkit.getPlayer(id).getDisplayName();
        }
        return null;
    }

    public Set<UUID> getTeamMembers(String name) {
        if (teams.containsKey(name)) {

            Teams teams1 = teams.get(name);
            return teams1.getMembers();

        }
        return null;
    }

    public void leaveTeam(Player player) {

        if (players.containsKey(player.getUniqueId())) {

            String name = players.get(player.getUniqueId());
            players.remove(player.getUniqueId());
            Teams team = teams.get(name);
            String rank = team.getRank(player);
            team.removeMember(player);
            if (rank.equals("Default")) {
                Set<UUID> members = team.getMembers();
                for (UUID uuid : members) {
                    players.remove(uuid);
                }
                teams.remove(name);
            }
        }

    }


    public boolean isInTeam(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public String getTeam(Player player) {
        if (players.containsKey(player.getUniqueId()))
            return players.get(player.getUniqueId());
        return null;
    }

    public boolean getTeamExists(String teamname) {

        return teams.containsKey(teamname);
    }

    public Teams getTeamTeam(Player player) {
        return teams.get(player);
    }


    public Set<String> getTeams() {
        return teams.keySet();
    }

}
