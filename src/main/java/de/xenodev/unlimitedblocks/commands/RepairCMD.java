package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RepairCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 1){
                if(args[0].equalsIgnoreCase("all")){
                    if(!p.hasPermission("ub.command.repair.all")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.repair.all");
                        return true;
                    }
                    for(ItemStack itemStack : p.getInventory().getContents()){
                        if(itemStack != null) {
                            itemStack.setDurability((short) 0);
                        }
                    }
                    for(ItemStack itemStack : p.getInventory().getArmorContents()){
                        if(itemStack != null) {
                            itemStack.setDurability((short) 0);
                        }
                    }
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast deine Items repariert");
                }else{
                    p.sendMessage(CityBuild.getPrefix() + "§7Falscher Syntax: §c" + args[0]);
                }
            }else{
                if(!p.hasPermission("ub.command.repair")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.repair");
                    return true;
                }
                ItemStack itemStack = p.getInventory().getItemInMainHand();
                if(itemStack == null){
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast kein Item in der Hand");
                    return true;
                }
                itemStack.setDurability((short) 0);
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast das Item in deiner Hand repariert");
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
            arrayList.add("all");
        }

        return arrayList;
    }
}
