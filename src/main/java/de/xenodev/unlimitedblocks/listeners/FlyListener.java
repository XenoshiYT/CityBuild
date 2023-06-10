package de.xenodev.unlimitedblocks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FlyListener implements Listener {

    @EventHandler
    public void handleFlyInPlotworld(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(!p.getWorld().getName().equalsIgnoreCase("desire")){
            if(!p.hasPermission("ub.event.fly.bypass")){
                if(p.getAllowFlight()){
                    p.setAllowFlight(false);
                    p.setFlying(false);
                }
            }
        }
    }

}
