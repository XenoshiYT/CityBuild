package de.xenodev.unlimitedblocks.listeners;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.LocationManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PortalListener implements Listener {

    @EventHandler
    public void handleTravelPortal(PlayerPortalEvent e){
        Player p = e.getPlayer();

        if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) || e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){
            e.setCancelled(true);
            LocationManager locationManager = new LocationManager("Spawn");
            Location location = locationManager.loadLocation();
            p.teleport(location);
        }
    }

}
