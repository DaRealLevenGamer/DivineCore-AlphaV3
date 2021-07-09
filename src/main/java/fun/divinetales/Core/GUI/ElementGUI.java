package fun.divinetales.Core.GUI;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.GUI.Entries.ElementEntries.AirEntry;
import fun.divinetales.Core.GUI.Entries.ElementEntries.EarthEntry;
import fun.divinetales.Core.GUI.Entries.ElementEntries.FireEntry;
import fun.divinetales.Core.GUI.Entries.ElementEntries.WaterEntry;
import fun.divinetales.Core.Utils.InventoryUtils.GUIEntry;
import fun.divinetales.Core.Utils.InventoryUtils.GUIManager;
import fun.divinetales.Core.Utils.InventoryUtils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.*;

import static fun.divinetales.Core.Utils.ColorUtil.*;

public class ElementGUI extends GUIUtils {

    private final GUIManager manager = new GUIManager(CoreMain.getInstance());
    private HashMap<UUID, Boolean> canClose = new HashMap<>();
    private final List<GUIEntry> entries;
    private final List<String> FireLore = new ArrayList<>();
    private final List<String> WaterLore = new ArrayList<>();
    private final List<String> AirLore = new ArrayList<>();
    private final List<String> EarthLore = new ArrayList<>();

    public ElementGUI(String identifier, int size, String title) {
        super(identifier, size, title);
        setLore();
        this.entries = Arrays.asList(

               new FireEntry(Material.RED_STAINED_GLASS_PANE, color("&8[&c&lFireElement&8]"), FireLore, 1, 10),
                new WaterEntry(Material.BLUE_STAINED_GLASS_PANE, color("&8[&3&lWaterElement&8]"), WaterLore, 1, 12),
                new AirEntry(Material.WHITE_STAINED_GLASS_PANE, color("&8[&f&lAirElement&8]"), AirLore, 1, 14),
                new EarthEntry(Material.GREEN_STAINED_GLASS_PANE, color("&8[&a&lEarthElement&8]"), EarthLore, 1, 16)

        );

    }

    private void setLore() {
        FireLore.add("&8[&f&lDescription&8]");
        FireLore.add(color("&c&lNorth, Element of Summer ~ Passion"));
        FireLore.add(color("&c&lEnergy, Will, Courage."));
        FireLore.add(color("&c&lThe most destructive element "));
        FireLore.add(color("&c&lthat can be casted by magical beings."));
        FireLore.add(color("&c&lUnder the right conditions fire is enhanced "));
        FireLore.add(color("&c&lyet difficult to master and use. "));
        FireLore.add(color("&c&lperfect for those with fire in their heart."));
        FireLore.add("&8[&f&lDescription&8]");
        WaterLore.add("&8[&f&lDescription&8]");
        WaterLore.add(color("&3&lWest, Element of Autumn ~ Dreams, "));
        WaterLore.add(color("&3&lFeeling, Compassion, Intuition. "));
        WaterLore.add(color("&3&lWater is the calmest of the elements.  "));
        WaterLore.add(color("&3&lConcentration of water manipulation, ice, vapor. "));
        WaterLore.add(color("&3&lAn adaptable element for one to master and use.  "));
        WaterLore.add("&8[&f&lDescription&8]");
        AirLore.add("&8[&f&lDescription&8]");
        AirLore.add(color("&f&lSouth, Element of Spring ~ Breath, "));
        AirLore.add(color("&f&lIntellect, Mind."));
        AirLore.add(color("&f&lAn invisible element used by mind."));
        AirLore.add(color("&f&lAir is an element considered "));
        AirLore.add(color("&f&lto be the most deadliest."));
        AirLore.add(color("&f&lEvasive element great for those"));
        AirLore.add(color("&f&lwho think with their inner side."));
        AirLore.add("&8[&f&lDescription&8]");
        EarthLore.add("&8[&f&lDescription&8]");
        EarthLore.add(color("&a&lEast, Element of Winter ~ Strength, "));
        EarthLore.add(color("&a&lForm, Body. Earthy material "));
        EarthLore.add(color("&a&lcan be manipulated, rocks, sand, gems. "));
        EarthLore.add(color("&a&lThe element of "));
        EarthLore.add(color("&a&lstrong defense and offensive power."));
        EarthLore.add(color("&a&lRaw physical power, "));
        EarthLore.add(color("&a&lperfect for those who use their body."));
        EarthLore.add("&8[&f&lDescription&8]");
    }


    @Override
    public List<GUIEntry> getEntries() {
        return this.entries;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void onClose(InventoryCloseEvent event) {


    }
}
