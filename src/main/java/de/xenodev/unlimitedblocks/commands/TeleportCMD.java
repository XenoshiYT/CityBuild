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

public class TeleportCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(cmd.getName().equalsIgnoreCase("tp")){
                if(args.length == 2){
                    if(!p.hasPermission("ub.command.teleport.other")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.teleport.other");
                        return true;
                    }
                    Player tpplayer = Bukkit.getPlayerExact(args[0]);
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if(!tpplayer.isOnline() || !target.isOnline()){
                        p.sendMessage(CityBuild.getPrefix() + "§7Ein Spieler ist nicht online");
                        return true;
                    }
                    tpplayer.teleport(target);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast §6" + tpplayer.getName() + " §7zu §a" + target.getName() + " §7teleportiert");
                }else if(args.length == 1){
                    if(!p.hasPermission("ub.command.teleport")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.teleport");
                        return true;
                    }
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if(!target.isOnline()){
                        p.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                        return true;
                    }
                    p.teleport(target);
                    p.sendMessage(CityBuild.getPrefix() + "§7du hast dich zu §6" + target.getName() + " §7teleportiert");
                }else{
                    p.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
                }
            }

            if(cmd.getName().equalsIgnoreCase("tphere")){
                if(!p.hasPermission("ub.command.teleport.here")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.teleport.here");
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[0]);
                if(!target.isOnline()){
                    p.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                    return true;
                }
                target.teleport(p);
                p.sendMessage(CityBuild.getPrefix() + "§7du hast §6" + target.getName() + " §7zu dir teleportiert");
            }

            if(cmd.getName().equalsIgnoreCase("tpall")){
                if(!p.hasPermission("ub.command.teleport.all")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.teleport.all");
                    return true;
                }
                for(Player all : Bukkit.getOnlinePlayers()){
                    all.teleport(p);
                }
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast §calle §7zu dir teleportiert");
            }
        }else{
            sender.sendMessage(CityBuild.getPrefix() + "§cDu musst ein Spieler sein");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> arrayList = new ArrayList();

        if(cmd.getName().equalsIgnoreCase("tp")){
            if(args.length == 2){
                for(Player player : Bukkit.getOnlinePlayers()){
                    arrayList.add(player.getName());
                }
            }else if(args.length == 1){
                for(Player player : Bukkit.getOnlinePlayers()){
                    arrayList.add(player.getName());
                }
            }
        }

        if(cmd.getName().equalsIgnoreCase("tphere")){
            if(args.length == 1){
                for(Player player : Bukkit.getOnlinePlayers()){
                    arrayList.add(player.getName());
                }
            }
        }

        return arrayList;
    }
}
