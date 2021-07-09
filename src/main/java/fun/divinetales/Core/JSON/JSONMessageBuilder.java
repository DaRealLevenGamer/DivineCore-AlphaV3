package fun.divinetales.Core.JSON;

import java.util.ArrayList;

public class JSONMessageBuilder {
    public String hoverevent = "";

    public String clickevent = "";

    private String color = "white";

    private final ArrayList<String> messages;

    private String message;

    private boolean bold = false;

    private boolean italic = false;

    private boolean strikethrough = false;

    private boolean underlined = false;

    private boolean obfuscated = false;

    public JSONMessageBuilder(String message) {
        this.message = "{\"text\":\"" + message + "\"";
        this.messages = new ArrayList<>();
    }

    public JSONMessageBuilder setHoverEvent(JSONEvent.HoverEvent event, String value) {
        if (value.equals("")) {
            this.hoverevent = "";
        } else {
            this.hoverevent = "\"hoverEvent\":{\"action\":\"" + event.getName() + "\",\"value\":\"" + value + "\"}";
        }
        return this;
    }

    public JSONMessageBuilder setClickEvent(JSONEvent.ClickEvent event, String value) {
        if (value.equals("")) {
            this.clickevent = "";
        } else {
            this.clickevent = "\"clickEvent\":{\"action\":\"" + event.getName() + "\",\"value\":\"" + value + "\"}";
        }
        return this;
    }

    public JSONMessageBuilder addMessage(String message) {
        saveMessage();
        reset();
        this.hoverevent = "";
        this.clickevent = "";
        this.message = ",{\"text\":\"" + message + "\"";
        return this;
    }

    public JSONMessageBuilder newLine(String message) {
        addMessage("\n");
        addMessage(message);
        return this;
    }

    public JSONMessageBuilder newLine() {
        addMessage("\n");
        return this;
    }

    public JSONMessageBuilder setBold(boolean value) {
        this.bold = value;
        return this;
    }

    public JSONMessageBuilder setItalic(boolean value) {
        this.italic = value;
        return this;
    }

    public JSONMessageBuilder setStrikeThrough(boolean value) {
        this.strikethrough = value;
        return this;
    }

    public JSONMessageBuilder setUnderlined(boolean value) {
        this.underlined = value;
        return this;
    }

    public JSONMessageBuilder setObfuscated(boolean value) {
        this.obfuscated = value;
        return this;
    }

    public JSONMessageBuilder setColor(JSONColor.Color color) {
        this.color = color.getName();
        return this;
    }

    private JSONMessageBuilder saveMessage() {
        String newmsg = this.message;
        newmsg = newmsg + ",\"bold\":" + this.bold + ",\"italic\":" + this.italic + ",\"strikethrough\":" + this.strikethrough + ",\"underlined\":" + this.underlined + ",\"obfuscated\":" + this.obfuscated + ",\"color\":\"" + this.color + "\"";
        if (!this.clickevent.equals(""))
            newmsg = newmsg + "," + this.clickevent;
        if (!this.hoverevent.equals(""))
            newmsg = newmsg + "," + this.hoverevent;
        newmsg = newmsg + "}";
        this.messages.add(newmsg);
        return this;
    }

    public String build() {
        saveMessage();
        StringBuilder newmsg = new StringBuilder("[\"\",");
        for (String msg : this.messages)
            newmsg.append(msg);
        newmsg.append("]");
        return newmsg.toString();
    }

    private void reset() {
        this.bold = false;
        this.italic = false;
        this.strikethrough = false;
        this.underlined = false;
        this.obfuscated = false;
        this.clickevent = "";
        this.hoverevent = "";
        this.color = "white";
    }
}

