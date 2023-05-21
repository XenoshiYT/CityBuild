package de.xenodev.unlimitedblocks.commands;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.ItemBuilder;
import de.xenodev.unlimitedblocks.utils.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class SettingsCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            SettingsManager settingsManager = new SettingsManager(p);
            Inventory inventory = Bukkit.createInventory(p, 9*5, "§6§lSettings");

            for(int i = 0; i < inventory.getSize(); i++){
                inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
            }

            if(settingsManager.getSetting("allow-tpa")){
                inventory.setItem(0, new ItemBuilder(Material.ENDER_PEARL).setName("§6Allow TPA")
                        .setLore("§7Diese Einstellung ist §a§lAktiviert", "", "§7§oErlaube Personen dir TPA-Anfragen zu schicken")
                        .setFlag(ItemFlag.HIDE_ENCHANTS).setEnchantment(Enchantment.CHANNELING, 1).build());
            }else{
                inventory.setItem(0, new ItemBuilder(Material.ENDER_PEARL).setName("§6Allow TPA")
                        .setLore("§7Diese Einstellung ist §c§lDeaktiviert", "", "§7§oErlaube Personen dir TPA-Anfragen zu schicken")
                        .build());
            }

            if(settingsManager.getSetting("allow-socialspy")){
                inventory.setItem(0, new ItemBuilder(Material.ENDER_EYE).setName("§6Allow Social Spy")
                        .setLore("§7Diese Einstellung ist §a§lAktiviert", "", "§7§oSchaue dir die MSG-Nachrichten von anderen an")
                        .setFlag(ItemFlag.HIDE_ENCHANTS).setEnchantment(Enchantment.CHANNELING, 1).build());
            }else{
                inventory.setItem(0, new ItemBuilder(Material.ENDER_EYE).setName("§6Allow Social Spy")
                        .setLore("§7Diese Einstellung ist §c§lDeaktiviert", "", "§7§oSchaue dir die MSG-Nachrichten von anderen an")
                        .build());
            }

            if(settingsManager.getSetting("allow-msg")){
                inventory.setItem(0, new ItemBuilder(Material.ENDER_EYE).setName("§6Allow MSG")
                        .setLore("§7Diese Einstellung ist §a§lAktiviert", "", "§7§oErlaube Personen dir MSG-Nachrichten zu schreiben")
                        .setFlag(ItemFlag.HIDE_ENCHANTS).setEnchantment(Enchantment.CHANNELING, 1).build());
            }else{
                inventory.setItem(0, new ItemBuilder(Material.ENDER_EYE).setName("§6Allow MSG")
                        .setLore("§7Diese Einstellung ist §c§lDeaktiviert", "", "§7§oErlaube Personen dir MSG-Nachrichten zu schreiben")
                        .build());
            }

            inventory.setItem(31, new ItemBuilder(Material.BARRIER).setName("§cClose").build());

            p.openInventory(inventory);

        } else {
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