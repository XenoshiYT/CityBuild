package de.xenodev.unlimitedblocks.utils;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlayerManager {

    private OfflinePlayer p;
    private File file;
    private YamlConfiguration cfg;

    public PlayerManager(OfflinePlayer p){
        this.p = p;
        file = new File("plugins/" + CityBuild.getInstance().getName() + "/Players/Infos", p.getUniqueId() + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void setFirstJoin(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        cfg.set("Join.First", simpleDateFormat.format(new Date()));
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public String getFirstJoin(){
        return cfg.getString("Join.First");
    }

    public void setLastJoin(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        cfg.set("Join.Last", simpleDateFormat.format(new Date()));
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public String getLastJoin(){
        return cfg.getString("Join.Last");
    }

    public void addTotalJoin(){
        cfg.set("Join.Total", getTotalJoin() + 1);
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public Integer getTotalJoin(){
        return cfg.getInt("Join.Total");
    }

    public void setLastPosition(){
        cfg.set("Leave.Position", p.getPlayer().getLocation());
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public Location getLastPosition(){
        return cfg.getLocation("Leave.Position");
    }

    public void addLastIP(){
        ArrayList<String>  list = getLastIP();
        list.add(((CraftPlayer)p).getAddress().getAddress().toString());
        cfg.set("IP's", list);
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public ArrayList<String> getLastIP(){
        if(cfg.get("IP's") != null) {
            return (ArrayList<String>) cfg.getList("IP's");
        }else {
            return new ArrayList<>();
        }
    }

    public void addLastName(){
        ArrayList<String> list = new ArrayList();
        list.add(p.getName().toString());
        cfg.set("Name's", list);
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public ArrayList<String> getLastName(){
        if(cfg.get("Name's") != null) {
            return (ArrayList<String>) cfg.getList("Name's");
        }else {
            return new ArrayList<>();
        }
    }

    public Boolean isCreated(){
        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }

}
