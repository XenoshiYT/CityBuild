package de.xenodev.unlimitedblocks.utils;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomeManager {

    private Player p;
    private File file;
    private YamlConfiguration cfg;

    public HomeManager(Player p){
        this.p = p;
        file = new File("plugins/" + CityBuild.getInstance().getName() + "/Players/Homes", p.getUniqueId() + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void saveLocation(String name){
        if(!existsLocation(name)) {
            cfg.set("Home." + name, p.getLocation());
            try {
                cfg.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            p.sendMessage(CityBuild.getPrefix() + "§7Das Home §6" + name + " §7wurde erstellt");
        }else{
            p.sendMessage(CityBuild.getPrefix() + "§7Das Home §6" + name + " §7existiert bereits");
        }
    }

    public Location loadLocation(String name){
        if(existsLocation(name)) {
            return cfg.getLocation("Home." + name);
        }else{
            return null;
        }
    }

    public void deleteLocation(String name){
        if(existsLocation(name)){
            cfg.set("Home." + name, null);
            try {
                cfg.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            p.sendMessage(CityBuild.getPrefix() + "§7Das Home §6" + name + " §7wurde gelöscht");
        }else{
            p.sendMessage(CityBuild.getPrefix() + "§7Das Home §6" + name + " §7existiert nicht");
        }
    }

    private Boolean existsLocation(String name){
        if(cfg.getLocation("Home." + name) != null) {
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<String> getLocations(){
        ArrayList<String> arrayList = new ArrayList<>();
        ConfigurationSection configurationSection = cfg.getConfigurationSection("Home");

        if(configurationSection != null){
            for(String name : configurationSection.getKeys(false)){
                arrayList.add(name);
            }
        }

        return arrayList;
    }

}
