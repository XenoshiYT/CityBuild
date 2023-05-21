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

public class PingCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                Player target = Bukkit.getPlayerExact(args[0]);
                if(!target.isOnline()){
                    p.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                    return true;
                }
                p.sendMessage(CityBuild.getPrefix() + "§6" + target.getName() + " §7hat einen Ping von §a" + target.getPing() + "ms");
            }else{
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast einen Ping von §a" + p.getPing() + "ms");
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
            for(Player all : Bukkit.getOnlinePlayers()){
                arrayList.add(all.getName());
            }
        }

        return arrayList;
    }
}
