package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GamemodeCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 1){
                if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                    if(!p.hasPermission("ub.command.gamemode.survival")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.gamemode.survival");
                        return true;
                    }
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(CityBuild.getPrefix() + "§7Dein Gamemode wurde auf §eSurvival §7geändert");
                } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                    if(!p.hasPermission("ub.command.gamemode.creative")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.gamemode.creative");
                        return true;
                    }
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(CityBuild.getPrefix() + "§7Dein Gamemode wurde auf §eCreative §7geändert");
                } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                    if(!p.hasPermission("ub.command.gamemode.adventure")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.gamemode.adventure");
                        return true;
                    }
                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(CityBuild.getPrefix() + "§7Dein Gamemode wurde auf §eCreative §7geändert");
                } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                    if(!p.hasPermission("ub.command.gamemode.spectator")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.gamemode.spectator");
                        return true;
                    }
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(CityBuild.getPrefix() + "§7Dein Gamemode wurde auf §eSpectator §7geändert");
                }else{
                    p.sendMessage(CityBuild.getPrefix() + "§7Falscher Syntax: §c" + args[0]);
                }
            }else if(args.length == 2){
                Player target = Bukkit.getPlayerExact(args[1]);
                if(!target.isOnline()){
                    p.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                    return true;
                }
                if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                    if(!p.hasPermission("ub.command.gamemode.survival.other")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.gamemode.survival.other");
                        return true;
                    }
                    target.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(CityBuild.getPrefix() + "§7" + target.getName() + "§7's Gamemode wurde auf §eSurvival §7geändert");
                } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                    if(!p.hasPermission("ub.command.gamemode.creative.other")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.gamemode.creative.other");
                        return true;
                    }
                    target.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(CityBuild.getPrefix() + "§7" + target.getName() + "§7's Gamemode wurde auf §eCreative §7geändert");
                } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                    if(!p.hasPermission("ub.command.gamemode.adventure.other")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.gamemode.adventure.other");
                        return true;
                    }
                    target.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(CityBuild.getPrefix() + "§7" + target.getName() + "§7's Gamemode wurde auf §eCreative §7geändert");
                } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                    if(!p.hasPermission("ub.command.gamemode.spectator.other")){
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.gamemode.spectator.other");
                        return true;
                    }
                    target.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(CityBuild.getPrefix() + "§7" + target.getName() + "§7's Gamemode wurde auf §eSpectator §7geändert");
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
            arrayList.add("survival");
            arrayList.add("creative");
            arrayList.add("adventure");
            arrayList.add("spectator");
        }else if(args.length == 2){
            for(Player all : Bukkit.getOnlinePlayers()){
                arrayList.add(all.getName());
            }
        }

        return arrayList;
    }
}
