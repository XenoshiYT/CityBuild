package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WeatherCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!p.hasPermission("ub.command.weather")){
                p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.weather");
                return true;
            }

            if(args.length == 1){
                if(args[0].equalsIgnoreCase("clear")){
                    p.getWorld().setThundering(false);
                    p.getWorld().setStorm(false);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast das Wetter auf §6" + "Klar" + " §7gestellt");
                }else if(args[0].equalsIgnoreCase("rain")){
                    p.getWorld().setStorm(true);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast das Wetter auf §6" + "Regen" + " §7gestellt");
                }else if(args[0].equalsIgnoreCase("thunder")){
                    p.getWorld().setThundering(true);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast das Wetter auf §6" + "Sturm" + " §7gestellt");
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
            arrayList.add("clear");
            arrayList.add("rain");
            arrayList.add("thunder");
        }

        return arrayList;
    }
}
