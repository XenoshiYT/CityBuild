package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SkullCMD implements CommandExecutor, TabCompleter {

    private ArrayList<Player> commandCooldown = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!p.hasPermission("ub.command.skull")){
                p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.skull");
                return true;
            }
            if(commandCooldown.contains(p)){
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast noch Cooldown auf diesem Command");
                return true;
            }
            for(ItemStack itemStack : p.getInventory().getContents()){
                if(itemStack == null){
                    p.getInventory().addItem(new ItemBuilder(Material.PLAYER_HEAD).setOwner(args[0]).build());
                    break;
                }
            }
            p.sendMessage(CityBuild.getPrefix() + "§7Du hast dir einen Kopf gegeben");
            if(!p.hasPermission("ub.command.skull.bypass")){
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

        return arrayList;
    }
}
