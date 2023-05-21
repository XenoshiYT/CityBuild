package de.xenodev.unlimitedblocks.listeners;

import de.xenodev.unlimitedblocks.utils.SettingsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SettingsListener implements Listener {

    @EventHandler
    public void handleSattingGUI(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        SettingsManager settingsManager = new SettingsManager(p);

        if(e.getView().getTitle().equalsIgnoreCase("§6§lSettings")){
            e.setCancelled(true);
            if(e.getCurrentItem() == null) return;
            if(e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) return;
            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Allow TPA")){
                settingsManager.updateSetting("allow-tpa");
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Allow Social Spy")){
                settingsManager.updateSetting("allow-socialspy");
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Allow MSG")){
                settingsManager.updateSetting("allow-msg");
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")){
                p.closeInventory();
            }
        }
    }
}
