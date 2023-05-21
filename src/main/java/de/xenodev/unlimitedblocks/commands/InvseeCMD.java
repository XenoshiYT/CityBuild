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

public class InvseeCMD implements CommandExecutor, TabCompleter, Listener {

    private Player target;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!p.hasPermission("ub.command.invsee")){
                p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.invsee");
                return true;
            }

            if(args.length == 1){
                target = Bukkit.getPlayerExact(args[0]);
                if(!target.isOnline()){
                    p.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                    return true;
                }
                p.openInventory(target.getInventory());
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
            for(Player all : Bukkit.getOnlinePlayers()){
                arrayList.add(all.getName());
            }
        }

        return arrayList;
    }

    @EventHandler
    public void handleInvseeInventory(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if(e.getInventory() == target.getInventory()){
            if(p.hasPermission("ub.command.invsee.bypass")){
                e.setCancelled(false);
            }else{
                e.setCancelled(true);
            }
        }

    }
}
