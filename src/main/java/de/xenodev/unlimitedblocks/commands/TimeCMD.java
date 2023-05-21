package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TimeCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!p.hasPermission("ub.command.time")){
                p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.time");
                return true;
            }

            if(args.length == 1){
                if(args[0].equalsIgnoreCase("day")){
                    p.getWorld().setTime(1000);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast die Zeit auf §6" + "Tag" + " §7gestellt");
                }else if(args[0].equalsIgnoreCase("night")){
                    p.getWorld().setTime(13000);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast die Zeit auf §6" + "Nacht" + " §7gestellt");
                }else{
                    Integer time;
                    try{
                        time = Integer.valueOf(args[0]);
                    }catch (NumberFormatException e1){
                        p.sendMessage(CityBuild.getPrefix() + "§7Falscher Syntax: §c" + args[0]);
                        return true;
                    }
                    p.getWorld().setTime(time);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast die Zeit auf §6" + time + " §7gestellt");
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
            arrayList.add("day");
            arrayList.add("night");
        }

        return arrayList;
    }
}
