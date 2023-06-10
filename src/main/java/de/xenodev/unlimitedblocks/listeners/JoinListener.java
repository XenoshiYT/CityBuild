package de.xenodev.unlimitedblocks.listeners;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        VanishManager vanishManager = CityBuild.getVanishManager();
        if(!p.hasPermission("ub.command.vanish.bypass")) {
            vanishManager.hideAll(p);
        }
        LocationManager locationManager = new LocationManager("Spawn");
        Location location = locationManager.loadLocation();
        if(location == null){
            p.sendMessage(CityBuild.getPrefix() + "§7Der §6Spawn §7wurde noch nicht gesetzt");
        }else{
            if(!p.hasPermission("ub.event.bypass.spawn")) {
                p.teleport(location);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 10F);
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1));
            }
        }
        e.setJoinMessage("");
        EconomyManager economyManager = new EconomyManager(p);
        economyManager.createBank();
        String moneyAmount = String.format("%.2f", economyManager.getOfflineZins());
        if(economyManager.getOfflineZins() != 0){
            p.sendMessage(CityBuild.getPrefix() + "§7Du hast Offline §e" + moneyAmount + " §7€ an Zinsen bekommen");
            economyManager.resetOfflineZins();
        }

        PlayerManager playerManager = new PlayerManager(p);
        if(playerManager.getLastIP() != null) {
            if (!playerManager.getLastIP().contains(p.getAddress().getAddress().toString())) {
                playerManager.addLastIP();
            }
        }else{
            playerManager.addLastIP();
        }
        if(playerManager.getLastName() != null) {
            if (!playerManager.getLastName().contains(p.getName().toString())) {
                playerManager.addLastName();
            }
        }else{
            playerManager.addLastName();
        }
        playerManager.addTotalJoin();
        playerManager.setLastJoin();
        if(playerManager.getFirstJoin() == null){
            playerManager.setFirstJoin();
        }
        ScoreboardManager.createScoreboard(p);
    }

}
