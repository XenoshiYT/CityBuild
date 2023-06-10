package de.xenodev.unlimitedblocks.utils;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AutomessageManager {

    private static ArrayList<String> messagesArray = (ArrayList<String>) CityBuild.getInstance().getConfig().getStringList("automessages");
    private static HashMap<Integer, String> messagesHash = new HashMap<>();

    public static void loadMessages(){
        Integer stringID = 0;
        for(String message : messagesArray){
            stringID++;
            messagesHash.put(stringID, message);
        }
    }

    private static String pickMessage(Integer stringID){
        return messagesHash.get(stringID);
    }

    public static void startAutomessage(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CityBuild.getInstance(), new Runnable() {
            Integer getID = 0;
            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().size() >= 1) {
                    if (getID >= messagesHash.size()) {
                        getID = 0;
                    }
                    getID++;
                    Bukkit.broadcastMessage(CityBuild.getPrefix() + "§c" + ChatColor.translateAlternateColorCodes('&', pickMessage(getID).replace("&", "§")));
                }
            }
        }, 0, 30*60*20);
    }

}
