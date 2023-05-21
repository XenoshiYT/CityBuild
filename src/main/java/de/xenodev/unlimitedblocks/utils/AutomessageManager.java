package de.xenodev.unlimitedblocks.utils;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Random;

public class AutomessageManager {

    private static String pickMessage(){
        List<String> messages = CityBuild.getInstance().getConfig().getStringList("automessages");
        Integer messageID = new Random().nextInt(messages.size());
        return messages.get(messageID);
    }

    public static void startAutomessage(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CityBuild.getInstance(), new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(CityBuild.getPrefix() + ChatColor.translateAlternateColorCodes('&', pickMessage()));
            }
        }, 0, 20L*60*5);
    }

}
