package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TpaCMD implements CommandExecutor, TabCompleter {

    private HashMap<Player, ArrayList<String>> request = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player from = (Player) sender;

            if(cmd.getName().equalsIgnoreCase("tpa")){
                if(args.length == 1){
                    Player to = Bukkit.getPlayerExact(args[0]);
                    if(!to.isOnline()){
                        from.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                        return true;
                    }
                    if(to == from){
                        from.sendMessage(CityBuild.getPrefix() + "§7Du kannst dir keine Anfrage senden");
                        return true;
                    }
                    SettingsManager settingsManager = new SettingsManager(to);
                    if(settingsManager.getSetting("allow-tpa")){
                        from.sendMessage(CityBuild.getPrefix() + "§7Der Spieler erlaubt keine Anfragen");
                        return true;
                    }
                    if(request.containsKey(to)){
                        if(!request.get(to).contains(from)){
                            request.get(to).add(from.getName());
                            from.sendMessage(CityBuild.getPrefix() + "§7Du hast §6" + to.getName() + " §7eine Anfrage gesendet");
                            to.sendMessage(CityBuild.getPrefix() + "§7Du hast eine Anfrage von §6" + from.getName() + " §7erhalten");
                            to.sendMessage(CityBuild.getPrefix() + "§c§oDu hast §6§o15 Sekunden §c§oZeit die Anfrage anzunehmen!");
                            to.sendMessage(CityBuild.getPrefix() + "§7Akzeptieren kannst du den Teleport mit §a/tpaccept <name>");
                            to.sendMessage(CityBuild.getPrefix() + "§7Ablehnen kannst du den Teleport mit §c/tpdeny <name>");
                            new BukkitRunnable(){

                                @Override
                                public void run() {
                                    request.get(to).remove(from);
                                    from.sendMessage(CityBuild.getPrefix() + "§7Deine Anfrage an §6" + to.getName() + " §7ist abgelaufen");
                                    to.sendMessage(CityBuild.getPrefix() + "§7Die Anfrage von §6" + from.getName() + " §7 ist abgelaufen");
                                }
                            }.runTaskLater(CityBuild.getInstance(), 20*15);
                        }else{
                            from.sendMessage(CityBuild.getPrefix() + "§7Du hast §6" + to.getName() + " §7bereits eine Anfrage gesendet");
                        }
                    }else{
                        ArrayList<String> createRequest = new ArrayList<>();
                        createRequest.add(from.getName());
                        request.put(to, createRequest);
                        from.sendMessage(CityBuild.getPrefix() + "§7Du hast §6" + to.getName() + " §7eine Anfrage gesendet");
                        to.sendMessage(CityBuild.getPrefix() + "§7Du hast eine Anfrage von §6" + from.getName() + " §7erhalten");
                        to.sendMessage(CityBuild.getPrefix() + "§c§oDu hast §6§o15 Sekunden §c§oZeit die Anfrage anzunehmen!");
                        to.sendMessage(CityBuild.getPrefix() + "§7Akzeptieren kannst du den Teleport mit §a/tpaccept <name>");
                        to.sendMessage(CityBuild.getPrefix() + "§7Ablehnen kannst du den Teleport mit §c/tpdeny <name>");
                        new BukkitRunnable(){

                            @Override
                            public void run() {
                                request.get(to).remove(from);
                                from.sendMessage(CityBuild.getPrefix() + "§7Deine Anfrage an §6" + to.getName() + " §7ist abgelaufen");
                                to.sendMessage(CityBuild.getPrefix() + "§7Die Anfrage von §6" + from.getName() + " §7 ist abgelaufen");
                            }
                        }.runTaskLater(CityBuild.getInstance(), 20*15);
                    }
                }else{
                    from.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
                }
            }

            if(cmd.getName().equalsIgnoreCase("tpaccept")){
                if(args.length == 1){
                    Player from2 = Bukkit.getPlayerExact(args[0]);
                    if(request.containsKey(from)){
                        if(request.get(from).contains(from2.getName())){
                            if(!from2.isOnline()){
                                from.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                                return true;
                            }
                            from2.teleport(from);
                            request.get(from).remove(from2.getName());
                            from.sendMessage(CityBuild.getPrefix() + "§7Du hast die Anfrage von §6" + from2.getName() + " §7angenommen");
                            from2.sendMessage(CityBuild.getPrefix() + "§6" + from.getName() + " §7hat deine Anfrage angenommen");
                        }else{
                            from.sendMessage(CityBuild.getPrefix() + "§6" + from2.getName() + " §7hat dir keine Anfrage gesendet");
                        }
                    }else{
                        from.sendMessage(CityBuild.getPrefix() + "§6" + from2.getName() + " §7hat dir keine Anfrage gesendet");
                    }
                }else{
                    from.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
                }
            }

            if(cmd.getName().equalsIgnoreCase("tpdeny")){
                if(args.length == 1){
                    Player from2 = Bukkit.getPlayerExact(args[0]);
                    if(request.containsKey(from)){
                        if(request.get(from).contains(from2.getName())){
                            if(!from2.isOnline()){
                                from.sendMessage(CityBuild.getPrefix() + "§7Der Spieler ist nicht online");
                                return true;
                            }
                            request.get(from).remove(from2.getName());
                            from.sendMessage(CityBuild.getPrefix() + "§7Du hast die Anfrage von §6" + from2.getName() + " §7abgelehnt");
                            from2.sendMessage(CityBuild.getPrefix() + "§6" + from.getName() + " §7hat deine Anfrage abgelehnt");
                        }else{
                            from.sendMessage(CityBuild.getPrefix() + "§6" + from2.getName() + " §7hat dir keine Anfrage gesendet");
                        }
                    }else{
                        from.sendMessage(CityBuild.getPrefix() + "§6" + from2.getName() + " §7hat dir keine Anfrage gesendet");
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

        if(cmd.getName().equalsIgnoreCase("tpa")){
            if(args.length == 1) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    arrayList.add(all.getName());
                }
            }
        }

        if(cmd.getName().equalsIgnoreCase("tpaccept")){
            if(args.length == 1) {
                for (String all : request.get(sender)) {
                    arrayList.add(all);
                }
            }
        }

        if(cmd.getName().equalsIgnoreCase("tpdeny")){
            if(args.length == 1) {
                for (String all : request.get(sender)) {
                    arrayList.add(all);
                }
            }
        }

        return arrayList;
    }
}
