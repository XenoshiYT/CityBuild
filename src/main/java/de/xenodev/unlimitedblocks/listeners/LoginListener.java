package de.xenodev.unlimitedblocks.listeners;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void handlePlayerLogin(PlayerLoginEvent e){
        Player p = e.getPlayer();
        if(CityBuild.getInstance().getConfig().getBoolean("wartung")) {
            if (!p.hasPermission("ub.command.wartung.bypass")) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, CityBuild.getPrefix() + "§cDer Server befindet zur Zeit in den Wartungsarbeiten");
            }
        }
    }

}
