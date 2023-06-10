package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.LocationManager;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class SpawnCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 1){
                if(args[0].equalsIgnoreCase("create")) {
                    LocationManager locationManager = new LocationManager("Spawn", p.getLocation());
                    if (!p.hasPermission("ub.command.spawn.create")) {
                        p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.spawn.create");
                        return true;
                    }
                    locationManager.createLocation();
                    p.sendMessage(CityBuild.getPrefix() + "§7Du hast den §6Spawn §7gesetzt");
                }else{
                    p.sendMessage(CityBuild.getPrefix() + "§7Falscher Syntax: §c" + args[0]);
                }
            }else{
                LocationManager locationManager = new LocationManager("Spawn");
                Location location = locationManager.loadLocation();
                if(location == null){
                    p.sendMessage(CityBuild.getPrefix() + "§7Der §6Spawn §7wurde noch nicht gesetzt");
                    return true;
                }
                p.teleport(location);
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast dich zum §6Spawn §7teleportiert");
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 10F);
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 0));
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
