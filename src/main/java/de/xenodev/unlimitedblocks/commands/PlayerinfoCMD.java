package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerinfoCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                OfflinePlayer offlinePlayer = Bukkit.getPlayerExact(args[0]);
                PlayerManager playerManager = new PlayerManager(offlinePlayer);
                PlaytimeManager playtimeManager = new PlaytimeManager(offlinePlayer);
                if(playerManager.isCreated()) {
                    p.sendMessage(CityBuild.getPrefix() + "§7Informationen von §e§l" + offlinePlayer.getName());
                    p.sendMessage("§7First Join: §6" + playerManager.getFirstJoin());
                    p.sendMessage("§7Last Join: §6" + playerManager.getLastJoin());
                    p.sendMessage("§7Total Joins: §6" + playerManager.getTotalJoin());
                    p.sendMessage("§7Playtime: §6" + playtimeManager.getTime());
                    p.sendMessage("§7UUID: §6" + p.getUniqueId());
                    if (p.hasPermission("ub.command.playerinfo.admin")) {
                        p.sendMessage("§7Last Position:"
                                + "\n§8- §7X: §a" + playerManager.getLastPosition().getX()
                                + "\n§8- §7Y: §a" + playerManager.getLastPosition().getY()
                                + "\n§8- §7Z: §a" + playerManager.getLastPosition().getZ());
                        p.sendMessage("§7Last IP's:");
                        for (String ipString : playerManager.getLastIP()) {
                            p.sendMessage("§8- §c" + ipString.replaceAll("/", ""));
                        }
                        p.sendMessage("§7Last Name's:");
                        for (String nameString : playerManager.getLastName()) {
                            p.sendMessage("§8- §4" + nameString);
                        }
                    }
                }else{
                    p.sendMessage(CityBuild.getPrefix() + "§6" + offlinePlayer.getName() + "'s §7Spielerdaten existieren nicht");
                }
            }else{
                PlayerManager playerManager = new PlayerManager(p);
                PlaytimeManager playtimeManager = new PlaytimeManager(p);
                p.sendMessage(CityBuild.getPrefix() + "§7Deine Informationen");
                p.sendMessage("§7First Join: §6" + playerManager.getFirstJoin());
                p.sendMessage("§7Last Join: §6" + playerManager.getLastJoin());
                p.sendMessage("§7Total Joins: §6" + playerManager.getTotalJoin());
                p.sendMessage("§7Playtime: §6" + playtimeManager.getTime());
                p.sendMessage("§7UUID: §6" + p.getUniqueId());
                p.sendMessage("§7Last Position:"
                        + "\n§8- §7X: §a" + playerManager.getLastPosition().getX()
                        + "\n§8- §7Y: §a" + playerManager.getLastPosition().getY()
                        + "\n§8- §7Z: §a" + playerManager.getLastPosition().getZ());
                p.sendMessage("§7Last IP's:");
                for(String ipString : playerManager.getLastIP()){
                    p.sendMessage("§8- §c" + ipString.replaceAll("/", ""));
                }
                p.sendMessage("§7Last Name's:");
                for(String nameString : playerManager.getLastName()){
                    p.sendMessage("§8- §4" + nameString);
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
