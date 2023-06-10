package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.KitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitCMD implements CommandExecutor, TabCompleter {

    private ArrayList<Player> commandCooldown = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 2){
                if(!p.hasPermission("ub.command.kit.create")){
                    p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.kit.create");
                    return true;
                }

                if(args[0].equalsIgnoreCase("create")) {
                    String kitName = args[1];
                    if (args[1].equalsIgnoreCase("spieler")) {
                        KitManager.setKit(p, kitName);
                    } else if (args[1].equalsIgnoreCase("ultra")) {
                        KitManager.setKit(p, kitName);
                    } else if (args[1].equalsIgnoreCase("platinum")) {
                        KitManager.setKit(p, kitName);
                    } else if (args[1].equalsIgnoreCase("omega")) {
                        KitManager.setKit(p, kitName);
                    } else {
                        p.sendMessage(CityBuild.getPrefix() + "§7Falsches Kit: §c" + args[0]);
                    }
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast das Kit §6" + kitName.toUpperCase() + " §7erstellt");
                }else{
                    p.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
                }
            }else if(args.length == 1){
                String kitName = args[0];
                if(args[0].equalsIgnoreCase("spieler")){
                    if(!KitManager.existsKit(kitName)){
                        p.sendMessage(CityBuild.getPrefix() + "§7Das Kit §6" + kitName.toUpperCase() + " §7existiert nicht");
                        return true;
                    }
                    if(KitManager.getCooldown(p, kitName) <= 0) {
                        KitManager.getKit(p, kitName);
                        if(!p.hasPermission("ub.command.kit.bypass")){
                            KitManager.setCooldown(p, kitName, 18000);
                        }
                    }else{
                        p.sendMessage(CityBuild.getPrefix() + "§7Das Kit §6" + kitName.toUpperCase() + " §7ist noch auf Cooldown §8(§c" + KitManager.formateTime(KitManager.getCooldown(p, kitName)) + "§8)");
                    }
                }else if(args[0].equalsIgnoreCase("ultra")){
                    if(!KitManager.existsKit(kitName)){
                        p.sendMessage(CityBuild.getPrefix() + "§7Das Kit §6" + kitName.toUpperCase() + " §7existiert nicht");
                        return true;
                    }
                    if(!p.hasPermission("ub.command.kit.ultra")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.kit.ultra");
                        return true;
                    }
                    if(KitManager.getCooldown(p, kitName) <= 0) {
                        KitManager.getKit(p, kitName);
                        if(!p.hasPermission("ub.command.kit.bypass")){
                            KitManager.setCooldown(p, kitName, 43200);
                        }
                    }else{
                        p.sendMessage(CityBuild.getPrefix() + "§7Das Kit §6" + kitName.toUpperCase() + " §7ist noch auf Cooldown §8(§c" + KitManager.formateTime(KitManager.getCooldown(p, kitName)) + "§8)");
                    }
                }else if(args[0].equalsIgnoreCase("platinum")){
                    if(!KitManager.existsKit(kitName)){
                        p.sendMessage(CityBuild.getPrefix() + "§7Das Kit §6" + kitName.toUpperCase() + " §7existiert nicht");
                        return true;
                    }
                    if(!p.hasPermission("ub.command.kit.platinum")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.kit.platinum");
                        return true;
                    }
                    if(KitManager.getCooldown(p, kitName) <= 0) {
                        KitManager.getKit(p, kitName);
                        if(!p.hasPermission("ub.command.kit.bypass")){
                            KitManager.setCooldown(p, kitName, 64800);
                        }
                    }else{
                        p.sendMessage(CityBuild.getPrefix() + "§7Das Kit §6" + kitName.toUpperCase() + " §7ist noch auf Cooldown §8(§c" + KitManager.formateTime(KitManager.getCooldown(p, kitName)) + "§8)");
                    }
                }else if(args[0].equalsIgnoreCase("omega")){
                    if(!KitManager.existsKit(kitName)){
                        p.sendMessage(CityBuild.getPrefix() + "§7Das Kit §6" + kitName.toUpperCase() + " §7existiert nicht");
                        return true;
                    }
                    if(!p.hasPermission("ub.command.kit.omega")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.kit.omega");
                        return true;
                    }
                    if(KitManager.getCooldown(p, kitName) <= 0) {
                        KitManager.getKit(p, kitName);
                        if(!p.hasPermission("ub.command.kit.bypass")){
                            KitManager.setCooldown(p, kitName, 86400);
                        }
                    }else{
                        p.sendMessage(CityBuild.getPrefix() + "§7Das Kit §6" + kitName.toUpperCase() + " §7ist noch auf Cooldown §8(§c" + KitManager.formateTime(KitManager.getCooldown(p, kitName)) + "§8)");
                    }
                }else {
                    p.sendMessage(CityBuild.getPrefix() + "§7Falsches Kit: §c" + args[0]);
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
            if(sender.hasPermission("ub.command.kit.create")){
                arrayList.add("create");
            }
            arrayList.add("Spieler");
            arrayList.add("Ultra");
            arrayList.add("Platinum");
            arrayList.add("Omega");
        }else if(args.length == 2){
            if(sender.hasPermission("ub.command.kit.create")) {
                arrayList.add("Spieler");
                arrayList.add("Ultra");
                arrayList.add("Platinum");
                arrayList.add("Omega");
            }
        }

        return arrayList;
    }
}
