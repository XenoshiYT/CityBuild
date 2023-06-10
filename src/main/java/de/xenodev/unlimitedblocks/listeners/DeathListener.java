package de.xenodev.unlimitedblocks.listeners;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.LocationManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {

    @EventHandler
    public void handlePlayerDeath(PlayerDeathEvent e){
        e.setDeathMessage("");
        Player target = e.getEntity().getPlayer();
        Player killer = target.getKiller();

        LocationManager locationManager = new LocationManager("Spawn");
        Location location = locationManager.loadLocation();

        if(killer == null){
            target.sendMessage(CityBuild.getPrefix() + "§7Du bist gestorben");
            respawn(target, location);
        }else{
            if(killer.getInventory().getItemInMainHand() == null || killer.getInventory().getItemInMainHand().getType() == Material.AIR){
                target.sendMessage(CityBuild.getPrefix() + "§7Du wurdest von §6" + killer.getName() + " §7getötet");
                target.sendMessage(CityBuild.getPrefix() + "§7Du hast §6" + target.getName() + " §7getötet");
                respawn(target, location);
            }else{
                target.sendMessage(CityBuild.getPrefix() + "§7Du wurdest von §6" + killer.getName() + " §7mit §8[ §a" + killer.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + "§8] §7getötet");
                target.sendMessage(CityBuild.getPrefix() + "§7Du hast §6" + target.getName() + " §7mit §8[ §a" + killer.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + "§8] §7getötet");
                respawn(target, location);
            }
        }
    }

    public static void respawn(Player target, Location location){
        new BukkitRunnable(){

            @Override
            public void run() {
                target.spigot().respawn();
                target.teleport(location);
            }
        }.runTaskLater(CityBuild.getInstance(), 3);
    }

}
