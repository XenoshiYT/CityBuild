package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class RenameCMD implements CommandExecutor, TabCompleter {

    private ArrayList<Player> commandCooldown = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!p.hasPermission("ub.command.rename")){
                p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.rename");
                return true;
            }
            if(commandCooldown.contains(p)){
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast noch Cooldown auf diesem Command");
                return true;
            }
            ItemStack itemStack = p.getInventory().getItemInMainHand();
            if(itemStack == null){
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast kein Item in der Hand");
                return true;
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            String itemName = "";
            if(args.length == 0){
                p.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
                return true;
            }
            for(int i = 0; i < args.length; i++){
                itemName = itemName + args[i] + " ";
            }
            itemMeta.setDisplayName(itemName.replaceAll("&", "§"));
            itemStack.setItemMeta(itemMeta);
            p.sendMessage(CityBuild.getPrefix() + "§7Du hast das Item umgenannt");
            if(!p.hasPermission("ub.command.rename.bypass")){
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
