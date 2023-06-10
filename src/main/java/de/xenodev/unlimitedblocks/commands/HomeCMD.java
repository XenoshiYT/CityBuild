package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.HomeManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                Location location = new HomeManager(p).loadLocation(args[0]);
                if(location != null){
                    p.teleport(location);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast dich zu §6" + args[0] + " §7teleportiert");
                }else{
                    p.sendMessage(CityBuild.getPrefix() + "§7Das Home §6" + args[0] + " §7existiert nicht");
                }
            }else if(args.length == 2){
                if(args[0].equalsIgnoreCase("create")){
                    new HomeManager(p).saveLocation(args[1]);
                }else if(args[0].equalsIgnoreCase("delete")){
                    new HomeManager(p).deleteLocation(args[1]);
                }else{
                    p.sendMessage(CityBuild.getPrefix() + "§7Falscher Syntax: §c" + args[0]);
                }
            }else{
                p.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
            }
        }else{
            sender.sendMessage(CityBuild.getPrefix() + "§cDu musst ein Spieler sein");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> arrayList = new ArrayList();

        if(args.length == 1){
            arrayList.add("create");
            arrayList.add("delete");
            for(String all : new HomeManager((Player) sender).getLocations()){
                arrayList.add(all);
            }
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("delete")){
                for(String all : new HomeManager((Player) sender).getLocations()){
                    arrayList.add(all);
                }
            }
        }

        return arrayList;
    }
}
