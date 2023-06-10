package de.xenodev.unlimitedblocks.utils;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KitManager {

    private static File kitFile = new File("plugins/" + CityBuild.getInstance().getName() + "/Kit", "kits.yml");
    private static YamlConfiguration kitCfg = YamlConfiguration.loadConfiguration(kitFile);

    private static File cooldownFile = new File("plugins/" + CityBuild.getInstance().getName() + "/Kit", "cooldowns.yml");
    private static YamlConfiguration cooldownCfg = YamlConfiguration.loadConfiguration(cooldownFile);

    public static void setKit(Player p, String kitName){
        kitCfg.set(kitName.toUpperCase(), null);
        ArrayList<ItemStack> arrayList = new ArrayList<>();
        ItemStack[] itemStacks = p.getInventory().getContents();
        for(int i = 0; i < itemStacks.length; i++){
            ItemStack itemStack = itemStacks[i];
            if(itemStack != null){
                if(!itemStack.getType().equals(Material.AIR) && itemStack.getAmount() != 0){
                    arrayList.add(itemStack);
                }
            }
        }
        kitCfg.set(kitName.toUpperCase(), arrayList);
        try { kitCfg.save(kitFile); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public static void getKit(Player p, String kitName){
        Inventory inventory = Bukkit.createInventory(null, 9*3, "§8§l» §e§l" + kitName.toUpperCase() + "§7§l-Kit §8§l«");
        ArrayList<ItemStack> itemStacks = (ArrayList<ItemStack>) kitCfg.getList(kitName.toUpperCase());
        for(ItemStack itemStack : itemStacks){
            inventory.addItem(itemStack);
        }
        p.openInventory(inventory);
    }

    public static Boolean existsKit(String kitName){
        if(kitCfg.contains(kitName.toUpperCase()) && kitFile.exists()){
            return true;
        }else{
            return false;
        }
    }

    public static Integer getCooldown(Player p, String kitName){
        if(cooldownCfg.contains(p.getUniqueId() + "." + kitName.toUpperCase())){
            return cooldownCfg.getInt(p.getUniqueId() + "." + kitName.toUpperCase());
        }else{
            return 0;
        }
    }

    public static void setCooldown(Player p, String kitName, Integer cooldownTime){
        cooldownCfg.set(p.getUniqueId() + "." + kitName.toUpperCase(), cooldownTime);
        try { cooldownCfg.save(cooldownFile); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public static void removeCooldown(Player p, String kitName){
        setCooldown(p, kitName, getCooldown(p, kitName) - 1);
    }

    public static void setupCooldown(){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(Player all : Bukkit.getOnlinePlayers()){
                    if(getCooldown(all, "Spieler") >= 0){
                        removeCooldown(all, "Spieler");
                    }
                    if(getCooldown(all, "Ultra") >= 0){
                        removeCooldown(all, "Ultra");
                    }
                    if(getCooldown(all, "Platinum") >= 0){
                        removeCooldown(all, "Platinum");
                    }
                    if(getCooldown(all, "Omega") >= 0){
                        removeCooldown(all, "Omega");
                    }
                }
            }
        }.runTaskTimerAsynchronously(CityBuild.getInstance(), 0, 20);
    }

    public static String formateTime(Integer time){
        Integer seconds = 0, minutes = 0, hours = 0;

        while (time != 0){
            time--;
            seconds++;
            if(seconds >= 60){
                seconds = 0;
                minutes++;
            }
            if(minutes >= 60){
                minutes = 0;
                hours++;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(check(hours))
                .append(":")
                .append(check(minutes))
                .append(":")
                .append(check(seconds));
        return stringBuilder.toString();
    }

    private static String check(Integer time){
        return (time >= 10) ? ("" + time) : ("0" + time);
    }

}
