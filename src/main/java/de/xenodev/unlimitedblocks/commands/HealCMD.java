package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class HealCMD implements CommandExecutor, TabCompleter {

    private ArrayList<Player> commandCooldown = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 1){
                if(!p.hasPermission("ub.command.heal.other")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.heal.other");
                    return true;
                }
                if(commandCooldown.contains(p)){
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast noch Cooldown auf diesem Command");
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[0]);
                if(!target.isOnline()){
                    p.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                    return true;
                }
                target.setHealth(20);
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast §6" + target.getName() + " §7geheilt");
            }else{
                if(!p.hasPermission("ub.command.heal")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.heal");
                    return true;
                }
                if(commandCooldown.contains(p)){
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast noch Cooldown auf diesem Command");
                    return true;
                }
                p.setHealth(20);
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast dich geheilt");
            }
            if(!p.hasPermission("ub.command.heal.bypass")){
                commandCooldown.add(p);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        commandCooldown.remove(p);
                    }
                }.runTaskLater(CityBuild.getInstance(), 20*60);
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
