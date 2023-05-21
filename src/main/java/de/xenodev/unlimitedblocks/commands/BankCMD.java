package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.EconomyManager;
import de.xenodev.unlimitedblocks.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class BankCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 1){
                OfflinePlayer target = Bukkit.getPlayerExact(args[0]);
                EconomyManager economyManager = new EconomyManager(target);
                p.sendMessage(CityBuild.getPrefix() + "§6" + target.getName() + " §7hat ein Kontostand von §e" + economyManager.getBank() + "§7€");
            }else{
                EconomyManager economyManager = new EconomyManager(p);
                Inventory inventory = Bukkit.createInventory(p, 9*5, "§6§lBank-System");

                for(int i = 0; i < inventory.getSize(); i++){
                    inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                }

                inventory.setItem(11, new ItemBuilder(Material.CHEST).setName("§6Auszahlen").build());
                inventory.setItem(13, new ItemBuilder(Material.CHEST).setName("§6Einzahlen").build());
                inventory.setItem(15, new ItemBuilder(Material.NETHER_STAR).setName("§6Kontoinformationen")
                        .setLore("§e§lWillkommen in der UnlimitedBank", "", "§7Dies sind deine Informationen:",
                                "§8- §7Kontostand: §e" + economyManager.getBank(),
                                "§8- §7Bargeld: §e" + economyManager.getMoney(),
                                "§8- §7Zinssatz: §e0." + economyManager.getBankLevel() + "%").build());
                inventory.setItem(31, new ItemBuilder(Material.BARRIER).setName("§cClose").build());

                p.openInventory(inventory);
            }
        }else{
            sender.sendMessage(CityBuild.getPrefix() + "§cDu musst ein Spieler sein");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();

        if(args.length == 1){
            arrayList = EconomyManager.getBanks();
        }

        return arrayList;
    }
}
