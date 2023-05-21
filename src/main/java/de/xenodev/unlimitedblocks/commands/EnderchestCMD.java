package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class EnderchestCMD implements CommandExecutor, TabCompleter, Listener {

    private Player target;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 1){
                if(!p.hasPermission("ub.command.enderchest.other")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.enderchest.other");
                    return true;
                }
                target = Bukkit.getPlayerExact(args[0]);
                if(!target.isOnline()){
                    p.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                    return true;
                }
                p.openInventory(target.getEnderChest());
            }else{
                if(!p.hasPermission("ub.command.enderchest")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.enderchest");
                    return true;
                }
                p.openInventory(p.getEnderChest());
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

    @EventHandler
    public void handleEnderseeInventory(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if(e.getInventory() == target.getEnderChest()){
            if(p.hasPermission("ub.command.enderchest.bypass")){
                e.setCancelled(false);
            }else{
                e.setCancelled(true);
            }
        }

    }
}
