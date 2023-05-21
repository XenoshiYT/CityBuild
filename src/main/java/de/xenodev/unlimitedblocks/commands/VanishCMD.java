package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VanishCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        VanishManager vanishManager = CityBuild.getVanishManager();

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 1){
                if(!p.hasPermission("ub.command.vanish.other")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.vanish.other");
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[0]);
                if(!target.isOnline()){
                    p.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                    return true;
                }
                if(vanishManager.isVanished(target)){
                    vanishManager.setVanished(target, false);
                    p.sendMessage(CityBuild.getPrefix() + "§6" + target.getName() + " §7ist nicht mehr im Vanish");
                }else{
                    vanishManager.setVanished(target, true);
                    p.sendMessage(CityBuild.getPrefix() + "§6" + target.getName() + " §7ist nun im Vanish");
                }
            }else{
                if(!p.hasPermission("ub.command.vanish")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.vanish");
                    return true;
                }
                if(vanishManager.isVanished(p)){
                    vanishManager.setVanished(p, false);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du ist nicht mehr im Vanish");
                }else{
                    vanishManager.setVanished(p, true);
                    p.sendMessage(CityBuild.getPrefix() + "§7Du ist nun im Vanish");
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
            for(Player all : Bukkit.getOnlinePlayers()){
                arrayList.add(all.getName());
            }
        }

        return arrayList;
    }
}
