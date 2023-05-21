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

public class FlyCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 1){
                if(!p.hasPermission("ub.command.fly.other")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.fly.other");
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[0]);
                if(!target.isOnline()){
                    p.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                    return true;
                }
                if(target.getAllowFlight()){
                    target.setAllowFlight(false);
                    target.setFlying(false);
                    p.sendMessage(CityBuild.getPrefix() + "§6" + target.getName() + " §7kann nicht mehr fliegen");
                }else{
                    target.setAllowFlight(true);
                    target.setFlying(true);
                    p.sendMessage(CityBuild.getPrefix() + "§6" + target.getName() + " §7kann nun fliegen");
                }
            }else{
                if(!p.hasPermission("ub.command.fly")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.fly");
                    return true;
                }
                if(p.getAllowFlight()){
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du kannst nicht mehr fliegen");
                }else{
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du kannst nun fliegen");
                }
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
            for(Player all: Bukkit.getOnlinePlayers()){
                arrayList.add(all.getName());
            }
        }

        return arrayList;
    }
}
