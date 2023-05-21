package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MsgCMD implements CommandExecutor, TabCompleter {

    private HashMap<Player, Player> replay = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player from = (Player) sender;
            SettingsManager settingsManager;

            if(cmd.getName().equalsIgnoreCase("msg")){
                if(args.length >= 2){
                    Player to = Bukkit.getPlayerExact(args[0]);
                    if(!to.isOnline()){
                        from.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                        return true;
                    }
                    settingsManager = new SettingsManager(to);
                    if(settingsManager.getSetting("allow-msg")){
                        from.sendMessage(CityBuild.getPrefix() + "§7Der Spieler erlaubt keine Privatnachrichten");
                        return true;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 1; i < args.length; i++){
                        stringBuilder.append(args[i]).append(" ");
                    }

                    to.sendMessage(CityBuild.getPrefix() + "§8[§c" + from.getName() + " §7» §eMir§8] §f" + stringBuilder);
                    from.sendMessage(CityBuild.getPrefix() + "§8[§cIch" + " §7» §e" + to.getName() + "§8] §f" + stringBuilder);
                    replay.put(from, to);
                    replay.put(to, from);
                    for(Player all : Bukkit.getOnlinePlayers()){
                        settingsManager = new SettingsManager(all);
                        if(settingsManager.getSetting("allow-socialspy")){
                            if(all != from || all != to) {
                                all.sendMessage(CityBuild.getPrefix() + "§8[§c" + from.getName() + " §7» §e" + to.getName() + "§8] §f" + stringBuilder);
                            }
                        }
                    }
                }else{
                    from.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
                }
            }

            if(cmd.getName().equalsIgnoreCase("r")){
                if(args.length >= 1){
                    Player to = Bukkit.getPlayerExact(replay.get(from).getName());
                    if(!to.isOnline()){
                        from.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                        return true;
                    }
                    settingsManager = new SettingsManager(to);
                    if(settingsManager.getSetting("allow-msg")){
                        from.sendMessage(CityBuild.getPrefix() + "§7Der Spieler erlaubt keine Privatnachrichten");
                        return true;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 0; i < args.length; i++){
                        stringBuilder.append(args[i]).append(" ");
                    }
                    to.sendMessage(CityBuild.getPrefix() + "§8[§c" + from.getName() + " §7» §eMir§8] §f" + stringBuilder);
                    from.sendMessage(CityBuild.getPrefix() + "§8[§cIch" + " §7» §e" + to.getName() + "§8] §f" + stringBuilder);
                    replay.put(from, to);
                    replay.put(to, from);
                    for(Player all : Bukkit.getOnlinePlayers()){
                        settingsManager = new SettingsManager(all);
                        if(settingsManager.getSetting("allow-socialspy")){
                            if(all != from || all != to) {
                                all.sendMessage(CityBuild.getPrefix() + "§8[§c" + from.getName() + " §7» §e" + to.getName() + "§8] §f" + stringBuilder);
                            }
                        }
                    }
                }else{
                    from.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
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

        if(cmd.getName().equalsIgnoreCase("msg")){
            for(Player all : Bukkit.getOnlinePlayers()){
                arrayList.add(all.getName());
            }
        }

        return arrayList;
    }
}
