package fun.divinetales.Core.Coammnds.SubCommands.Profile;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Events.ChatEvents.JoinEvent;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.MYSQL.Data.SQLChangeSkin;
import org.bukkit.Bukkit;
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

        SQLChangeSkin sqlChangeSkin = new SQLChangeSkin(CoreMain.getInstance());

        final String url = args[2];
        Bukkit.getScheduler().runTaskAsynchronously(CoreMain.getInstance(), () -> {
            DataOutputStream out = null;
            BufferedReader reader = null;
            try {
                URL target = new URL("https://api.mineskin.org/generate/url");
                HttpURLConnection con = (HttpURLConnection)target.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setConnectTimeout(1000);
                con.setReadTimeout(30000);
                out = new DataOutputStream(con.getOutputStream());
                out.writeBytes("url=" + URLEncoder.encode(url, "UTF-8"));
                out.close();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                JSONObject output = (JSONObject)(new JSONParser()).parse(reader);
                JSONObject data = (JSONObject)output.get("data");
                JSONObject texture = (JSONObject)data.get("texture");
                final String textureEncoded = (String)texture.get("value");
                final String signature = (String)texture.get("signature");
                con.disconnect();
                Bukkit.getScheduler().runTask(CoreMain.getInstance(), () -> {
                    try {
                        sqlChangeSkin.setSkinInfo(player.getUniqueId(), Integer.parseInt(args[1]), textureEncoded, signature);
                        msgPlayer(player, color("&a&lYour skin has been changed!"));
                        JoinEvent.ChangeSkin(player);
                    } catch (IllegalArgumentException e) {
                        msgPlayer(player, color("&c&lThere was an error changing your skin!"));
                        e.printStackTrace();
                    }
                });
            } catch (Throwable t) {
                Bukkit.getScheduler().runTask(CoreMain.getInstance(), () -> msgPlayer(player, color("&c&lPlease input a valid URL!")));
            } finally {
                if (out != null)
                    try {
                        out.close();
                    } catch (IOException iOException) {}
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException iOException) {}
            }
        });
    }

}
