package de.xenodev.unlimitedblocks.listeners;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener {

    private String motdWartungString1 = "§e§lUnlimitedBocks §8■ §7Der wahre §d§l1.19.x §7CityBuild";
    private String motdWartungString2 = "§8§l» §cZur Zeit sind wir in Wartungen";
    private String motdString1 = "§e§lUnlimitedBocks §8■ §7Der wahre §d§l1.19.x §7CityBuild";
    private String motdString2 = "§8§l» §aWir verwirklichen deine Träume";

    @EventHandler
    public void handleServerPing(ServerListPingEvent e){
        if(CityBuild.getInstance().getConfig().getBoolean("wartung")){
            e.setMaxPlayers(0);
            e.setMotd(motdWartungString1 + "\n" + motdWartungString2);
        }else{
            e.setMotd(motdString1 + "\n" + motdString2);
        }
    }

}
