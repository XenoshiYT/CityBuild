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

public class BroadcastCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            String messageString = "";
            if(args.length == 0){
                p.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
                return true;
            }
            for(int i = 0; i < args.length; i++){
                messageString = messageString + args[i] + " ";
            }
            Bukkit.broadcastMessage(CityBuild.getPrefix() + " §c§l" + messageString.replaceAll("&", "§"));
        }else{
            String messageString = "";
            if(args.length == 0){
                sender.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
                return true;
            }
            for(int i = 0; i < args.length; i++){
                messageString = messageString + args[i] + " ";
            }
            Bukkit.broadcastMessage(CityBuild.getPrefix() + messageString.replaceAll("&", "§"));
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> arrayList = new ArrayList();

        return arrayList;
    }
}
