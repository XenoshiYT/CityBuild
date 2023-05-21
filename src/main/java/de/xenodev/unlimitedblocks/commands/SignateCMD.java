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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SignateCMD implements CommandExecutor, TabCompleter {

    private ArrayList<Player> commandCooldown = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!p.hasPermission("ub.command.signate")){
                p.sendMessage(CityBuild.getPrefix() + "§7Dir fehlt die Permission: §6" + "ub.command.signate");
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
            String itemLore = "";
            if(args.length == 0){
                p.sendMessage(CityBuild.getPrefix() + "§7Falsche Länge: §c" + args.length);
                return true;
            }
            for(int i = 0; i < args.length; i++){
                itemLore = itemLore + args[i] + " ";
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            itemMeta.setLore(Arrays.asList("", itemLore.replaceAll("&", "§"), "§7-------------------------------------------", "§7Signiert von §e§l" + p.getName() + " §7am §a§l" + simpleDateFormat.format(new Date())));
            itemStack.setItemMeta(itemMeta);
            p.sendMessage(CityBuild.getPrefix() + "§7Du hast das Item signiert");
            if(!p.hasPermission("ub.command.signate.bypass")){
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
