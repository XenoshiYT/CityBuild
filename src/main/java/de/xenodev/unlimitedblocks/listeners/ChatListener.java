package de.xenodev.unlimitedblocks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void handlePlayerChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(p.hasPermission("ub.event.chat.color")){
            e.setFormat("" + " §8| §7" + p.getName() + " §8» §f" + e.getMessage().replace("&", "§").replace("%", "%%"));
        }else{
            e.setFormat("" + " §8| §7" + p.getName() + " §8» §f" + e.getMessage().replace("%", "%%"));
        }
    }

}
