package de.xenodev.unlimitedblocks.listeners;

import de.xenodev.unlimitedblocks.utils.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();

        e.setQuitMessage("");
        PlayerManager playerManager = new PlayerManager(p);
        playerManager.setLastPosition();
    }

}
