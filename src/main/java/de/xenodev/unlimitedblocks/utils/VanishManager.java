package de.xenodev.unlimitedblocks.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class VanishManager {

    private Plugin plugin;
    private List<Player> vanished;

    public VanishManager(Plugin plugin){
        this.plugin = plugin;
        this.vanished = new ArrayList<>();
    }

    public List<Player> getVanished() {
        return vanished;
    }

    public Boolean isVanished(Player p){
        return vanished.contains(p);
    }

    public void setVanished(Player p, Boolean bool) {
        if(bool){
            vanished.add(p);
        }else{
            vanished.remove(p);
        }

        for(Player all : Bukkit.getOnlinePlayers()){
            if(p.equals(all)) continue;
            if(bool){
                if(!all.hasPermission("ub.command.vanish.bypass")){
                    all.hidePlayer(plugin, p);
                }
            }else{
                all.showPlayer(plugin, p);
            }
        }
    }

    public void hideAll(Player p){
        vanished.forEach(target -> p.hidePlayer(plugin, target));
    }
}
