package fun.divinetales.Core.JSON;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Objects;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JSONMessage {
    public static void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        try {
            Object enumTitle = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0].getField("TITLE").get(null);
            Object enumSubTitle = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            Object t = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, "{\"text\":\"" + title + "\"}");
            Object s = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, "{\"text\":\"" + subtitle + "\"}");
            Constructor<?> titleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
            Object a = titleConstructor.newInstance(enumTitle, t, fadeIn, stay, fadeOut);
            Object b = titleConstructor.newInstance(enumSubTitle, s, fadeIn, stay, fadeOut);
            sendPacket(p, a);
            sendPacket(p, b);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void sendTabHF(Player p, String header, String footer) {
        try {
            Constructor<?> constructor = Objects.requireNonNull(getNMSClass("PacketPlayOutPlayerListHeaderFooter")).getConstructor();
            Object packet = constructor.newInstance();
            Object h = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, "{\"text\":\"" + header + "\"}");
            Object f = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, "{\"text\":\"" + footer + "\"}");
            setField(packet, "a", h);
            setField(packet, "b", f);
            sendPacket(p, packet);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void sendAction(Player p, String message) {
        try {
            Object action = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, "{\"text\":\"" + message + "\"}");
            Constructor<?> actionConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutChat")).getConstructor(getNMSClass("IChatBaseComponent"), byte.class);
            Object a = actionConstructor.newInstance(action, (byte) 2);
            sendPacket(p, a);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void sendChat(Player p, String message) {
        try {
            Object action = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, message);
            Constructor<?> actionConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutChat")).getConstructor(getNMSClass("IChatBaseComponent"), byte.class);
            Object a = actionConstructor.newInstance(action, (byte) 0);
            sendPacket(p, a);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setField(Object change, String name, Object to) throws Exception {
        Field field = change.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(change, to);
        field.setAccessible(false);
    }

    public static String getItem(ItemStack is) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = new NBTTagCompound();
        nms.save(tag);
        return StringEscapeUtils.escapeJava(tag.toString().toLowerCase());
    }
}
