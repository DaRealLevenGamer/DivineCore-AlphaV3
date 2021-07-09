package fun.divinetales.Core.Coammnds.SubCommands.Profile;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.MYSQL.Data.SQLPlayerProfile;
import fun.divinetales.Core.Utils.MYSQL.Data.SQLPlayerSkins;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import static fun.divinetales.Core.Utils.ColorUtil.*;

public class ChangeSkinURL extends SubCommand {
    @Override
    public String getName() {
        return "setSkin";
    }

    @Override
    public String getDescription() {
        return "Sets RPSkin!";
    }

    @Override
    public String getSyntax() {
        return "/profile setSkin [profile] [url]";
    }

    @Override
    public void perform(Player player, String[] args) throws IOException {

        SQLPlayerSkins skins = CoreMain.getInstance().getSqlPlayerSkins();
        UUID id = player.getUniqueId();

        final String url = args[1];
        int profileNum = Integer.parseInt(args[0]);

        DataOutputStream out = null;
        BufferedReader reader = null;


        try {

            URL target = new URL("https://api.mineskin.org/generate/url");
            HttpURLConnection con = (HttpURLConnection) target.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setConnectTimeout(1000);
            con.setReadTimeout(30000);
            out = new DataOutputStream(con.getOutputStream());
            out.writeBytes("url=" + URLEncoder.encode(url, "UTF-8"));
            out.close();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JSONObject output = (JSONObject) new JSONParser().parse(reader);
            JSONObject data = (JSONObject) output.get("data");
            String uuid = (String) data.get("uuid");
            JSONObject texture = (JSONObject) data.get("texture");
            String textureEncoded = (String) texture.get("value");
            String signature = (String) texture.get("signature");
            con.disconnect();

            try {
                skins.setSkin(id, textureEncoded, signature, profileNum);
                msgPlayer(player, color("&f&lSkin has been changed!"));
                Bukkit.getScheduler().runTaskLater(CoreMain.getInstance(), () -> player.kickPlayer(color("&c&lPlease ReLog to see your new skin!")), 200);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }

    }

    public static void ChangeSkin(Player player, int profileNum) {

        SQLPlayerSkins skins = CoreMain.getInstance().getSqlPlayerSkins();
        UUID id = player.getUniqueId();

        GameProfile profile = ((CraftPlayer) player).getHandle().getProfile();
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) player).getHandle()));
        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", new Property("textures", skins.getSkinTexture(id, profileNum), skins.getSkinSignature(id, profileNum)));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle()));

    }

}
