package de.xenodev.unlimitedblocks.utils;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SettingsManager {

    private Player p;
    private File file;
    private YamlConfiguration cfg;

    public SettingsManager(Player p){
        this.p = p;
        file = new File("plugins/" + CityBuild.getInstance().getName() + "/Players/Settings", p.getUniqueId() + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public Boolean getSetting(String settingName){
        if(isCreated()) {
            return cfg.getBoolean(settingName);
        }else{
            return false;
        }
    }

    public void updateSetting(String settingName){
        if(isCreated()){
            if(getSetting(settingName)){
                cfg.set(settingName, false);
                try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
            }else{
                cfg.set(settingName, true);
                try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
            }
        }else{
            cfg.set(settingName, false);
            try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
        }
    }

    private Boolean isCreated(){
        if(!file.exists()){
            return false;
        }else{
            return true;
        }
    }
}
