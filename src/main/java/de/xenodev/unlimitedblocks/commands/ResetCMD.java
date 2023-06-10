package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.FarmweltManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResetCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!p.hasPermission("ub.command.reset")){
                p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.reset");
                return true;
            }

            if(args.length == 0){
                Bukkit.broadcastMessage(CityBuild.getPrefix() + "§e" + p.getName() + " §7hat die Farmwelt resetet");
                FarmweltManager.resetWorld();
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

        return arrayList;
    }
}
