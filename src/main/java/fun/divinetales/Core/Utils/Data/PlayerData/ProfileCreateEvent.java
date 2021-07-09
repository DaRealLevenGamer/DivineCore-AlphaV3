package fun.divinetales.Core.Utils.Data.PlayerData;

import fun.divinetales.Core.Utils.Data.Config;

import java.util.UUID;

public class ProfileCreateEvent {

    public ProfileCreateEvent(UUID id) {
        this.id = id;
        playerData = new Config("PlayerProfiles", id.toString(), false);
    }

   private final UUID id;

   private static Config playerData;

   private String RPName = "Default;";

   private Boolean NameEnabled = false;

    private String Element = "Default";

   private String Gender = "Default";

    private int Age = 0;

    private boolean ChangeSkin = false;

    private String TextureSkin;

    private String SignatureSkin;


   public void load() {
       this.RPName = playerData.getConfig().getString("RPName", "default");
       this.NameEnabled = playerData.getConfig().getBoolean("NameEnabled", false);
       this.Element = playerData.getConfig().getString("Element", "default");
       this.Gender = playerData.getConfig().getString("Gender", "default");
       this.Age = playerData.getConfig().getInt("Age", 0);
       this.TextureSkin = playerData.getConfig().getString("TextureSkin", "Default");
       this.SignatureSkin = playerData.getConfig().getString("SignatureSkin", "Default");
       this.ChangeSkin = playerData.getConfig().getBoolean("SkinEnabled", false);
   }

   public void save() {
       playerData.set("RPName", this.RPName);
       playerData.set("NameEnabled", this.NameEnabled);
       playerData.set("Element", this.Element);
       playerData.set("Gender", this.Gender);
       playerData.set("Age", this.Age);
       playerData.set("TextureSkin", this.TextureSkin);
       playerData.set("SignatureSkin", this.SignatureSkin);
       playerData.set("SkinEnabled", this.ChangeSkin);
       playerData.save();
   }

    public UUID getId() {
        return id;
    }

    public static Config getPlayerData() {
        return playerData;
    }

    public String getRPName() {
        return RPName;
    }

    public void setRPName(String RPName) {
        this.RPName = RPName;
    }

    public Boolean getNameEnabled() {
        return NameEnabled;
    }

    public void setNameEnabled(Boolean nameEnabled) {
        NameEnabled = nameEnabled;
    }

    public String getElement() {
        return Element;
    }

    public void setElement(String element) {
        Element = element;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public boolean isChangeSkin() {
        return ChangeSkin;
    }

    public void setChangeSkin(boolean changeSkin) {
        ChangeSkin = changeSkin;
    }

    public String getTextureSkin() {
        return TextureSkin;
    }

    public void setTextureSkin(String textureSkin) {
        TextureSkin = textureSkin;
    }

    public String getSignatureSkin() {
        return SignatureSkin;
    }

    public void setSignatureSkin(String signatureSkin) {
        SignatureSkin = signatureSkin;
    }
}
