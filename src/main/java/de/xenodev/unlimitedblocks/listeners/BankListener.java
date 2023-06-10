package de.xenodev.unlimitedblocks.listeners;

import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.utils.EconomyManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class BankListener implements Listener {

    private ArrayList<Player> addBank = new ArrayList<>();
    private ArrayList<Player> removeBank = new ArrayList<>();

    @EventHandler
    public void handleBankGUI(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equalsIgnoreCase("§6§lBank-System")){
            e.setCancelled(true);
            if(e.getCurrentItem() == null) return;
            if(e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) return;
            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Auszahlen")){
                if(!removeBank.contains(p) && !addBank.contains(p)) {
                    removeBank.add(p);
                    p.sendMessage(CityBuild.getPrefix() + "§c§lSchreibe deinen Auszahlbetrag in den Chat");
                    p.closeInventory();
                }else{
                    p.sendMessage(CityBuild.getPrefix() + "§7du zahlst bereits was aus");
                }
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Einzahlen")){
                if(!removeBank.contains(p) && !addBank.contains(p)) {
                    addBank.add(p);
                    p.sendMessage(CityBuild.getPrefix() + "§c§lSchreibe deinen Einzahlbetrag in den Chat");
                    p.closeInventory();
                }else{
                    p.sendMessage(CityBuild.getPrefix() + "§7du zahlst bereits was ein");
                }
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")){
                p.closeInventory();
            }
        }
    }

    @EventHandler
    public void handleBankChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        EconomyManager economyManager = new EconomyManager(p);
        Integer amount = 0;
        if(addBank.contains(p)){
            e.setCancelled(true);
            try{
                amount = Integer.valueOf(e.getMessage());
            }catch (NumberFormatException e1){
                p.sendMessage(CityBuild.getPrefix() + "§7Falsche Anzahl: §c" + e.getMessage() + " §8| §7Transfer abgebrochen");
                addBank.remove(p);
                return;
            }
            if(economyManager.getMoney() >= amount) {
                economyManager.addBank(amount.doubleValue());
                economyManager.removeMoney(amount.doubleValue());
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast §e" + amount + "€ §7auf die Bank gezahlt");
                addBank.remove(p);
            }else{
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast nicht genügend Geld in Bar");
                addBank.remove(p);
            }
        }else if(removeBank.contains(p)){
            e.setCancelled(true);
            try{
                amount = Integer.valueOf(e.getMessage());
            }catch (NumberFormatException e1){
                p.sendMessage(CityBuild.getPrefix() + "§7Falsche Anzahl: §c" + e.getMessage() + " §8| §7Transfer abgebrochen");
                return;
            }
            if(economyManager.getBank() >= amount) {
                economyManager.removeBank(amount.doubleValue());
                economyManager.addMoney(amount.doubleValue());
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast §e" + amount + "€ §7von der Bank abgeholt");
                removeBank.remove(p);
            }else{
                p.sendMessage(CityBuild.getPrefix() + "§7Du hast nicht genügend Geld auf der Bank");
                removeBank.remove(p);
            }
        }
    }

}
