package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 1){
                String stringName = args[0];
                LocationManager locationManager = new LocationManager(stringName);
                locationManager.teleportLocation(p);
            }else if(args.length == 2){
                String stringName = args[1];
                if(args[0].equalsIgnoreCase("delete")){
                    if(!p.hasPermission("ub.command.warp.delete")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.warp.delete");
                        return true;
                    }
                    LocationManager locationManager = new LocationManager(stringName);
                    locationManager.deleteLocation();
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast den Warp §6" + stringName + " §7gelöscht");
                }else if(args[0].equalsIgnoreCase("create")){
                    if(!p.hasPermission("ub.command.warp.create")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.warp.create");
                        return true;
                    }
                    LocationManager locationManager = new LocationManager(stringName, p.getLocation());
                    locationManager.createLocation();
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast den Warp §6" + stringName + " §7erstellt");
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
        ArrayList<String> arrayList = new ArrayList<>();

        if(args.length == 1){
            arrayList.add("delete");
            arrayList.add("create");
            for(Object stringName : LocationManager.getLocationList()){
                arrayList.add(stringName.toString());
            }
        }else if(args.length == 2){
            if(args[1].equalsIgnoreCase("create") || args[1].equalsIgnoreCase("delete")) {
                for (Object stringName : LocationManager.getLocationList()) {
                    arrayList.add(stringName.toString());
                }
            }
        }
        return arrayList;
    }
}
