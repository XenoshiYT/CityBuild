package de.xenodev.unlimitedblocks.utils;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LocationManager {

    private static File file = new File("plugins/" + CityBuild.getInstance().getName() + "/locations", "saves.yml");
    private static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    private String locationName;
    private Location location;

    public LocationManager(String locationName, Location location){
        this.locationName = locationName;
        this.location = location;
    }

    public LocationManager(String locationName){
        this(locationName, null);
    }

    public void createLocation(){
        cfg.set(locationName, location);
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void deleteLocation(){
        if(isLocation()) {
            cfg.set(locationName, null);
            try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
        }
    }

    public Location loadLocation(){
        if(isLocation()) {
            return cfg.getLocation(locationName);
        }else{
            return null;
        }
    }

    public void teleportLocation(Player p){
        if(isLocation()){
            p.teleport(loadLocation());
            p.sendMessage(CityBuild.getPrefix() + "§7Du hast dich zum Warp §6" + locationName + " §7teleportiert");
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 10F);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 0));
        }else{
            p.sendMessage(CityBuild.getPrefix() + "§7Die Location §6" + locationName + " §7existiert nicht");
        }
    }

    private Boolean isLocation(){
        if(cfg.contains(locationName)){
            return true;
        }else {
            return false;
        }
    }

    public static ArrayList getLocationList(){
        ArrayList<String> arrayList = new ArrayList<>();

        for(String stringName : cfg.getKeys(false)){
            arrayList.add(stringName);
        }

        return arrayList;
    }
}
