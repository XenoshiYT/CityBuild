package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClearchatCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player)sender;
            if(!p.hasPermission("ub.command.clearchat")){
                p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.clearchat");
                return true;
            }
            for(int i = 0; i < 105; i++){
                for(Player all : Bukkit.getOnlinePlayers()){
                    if(!all.hasPermission("ub.command.clearchat.bypass")){
                        all.sendMessage("");
                    }
                }
            }
            Bukkit.broadcastMessage(CityBuild.getPrefix() + "§7Der Chat wurde von §e§l" + p.getName() + " §7gelöscht");
        }else{
            for(int i = 0; i < 105; i++){
                for(Player all : Bukkit.getOnlinePlayers()){
                    all.sendMessage("");
                }
            }
            Bukkit.broadcastMessage(CityBuild.getPrefix() + "§7Der Chat wurde vom §e§lServer §7gelöscht");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> arrayList = new ArrayList();

        return arrayList;
    }
}
