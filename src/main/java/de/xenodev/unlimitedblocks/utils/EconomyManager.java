package de.xenodev.unlimitedblocks.utils;

import de.xenodev.unlimitedblocks.CityBuild;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class EconomyManager {

    private static File file = new File("plugins/" + CityBuild.getInstance().getName() + "/Bank", "save.yml");
    private static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    private Economy economy = CityBuild.getEconomy();
    private OfflinePlayer p;

    public EconomyManager(OfflinePlayer p){
        this.p = p;
    }

    public EconomyManager(){
        this(null);
    }

    public Double getMoney(){
        return economy.getBalance(p);
    }

    public void addMoney(Double amount){
        economy.depositPlayer(p, amount);
    }

    public void removeMoney(Double amount){
        economy.withdrawPlayer(p, amount);
    }

    public void setMoney(Double amount){
        removeMoney(getMoney());
        addMoney(amount);
    }

    public Double getBank(){
        return cfg.getDouble(p.getUniqueId() + ".Amount");
    }

    public void addBank(Double amount){
        cfg.set(p.getUniqueId() + ".Amount", getBank() + amount);
        removeMoney(amount);
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void removeBank(Double amount){
        cfg.set(p.getUniqueId() + ".Amount", getBank() - amount);
        addMoney(amount);
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public static ArrayList<String> getBanks(){
        ArrayList<String> arrayList = new ArrayList<>();

        for(String uuids : cfg.getKeys(false)){
            arrayList.add(UUIDFetcher.getName(UUID.fromString(uuids)));
        }

        return arrayList;
    }

    public Integer getBankLevel(){
        return cfg.getInt(p.getUniqueId() + ".Level");
    }

    public void addBankLevel() {
        cfg.set(p.getUniqueId() + ".Level", getBankLevel() + 1);
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void createBank(){
        if(!cfg.contains(p.getUniqueId().toString())){
            cfg.set(p.getUniqueId() + ".Amount", 0);
            cfg.set(p.getUniqueId() + ".Level", 1);
            try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
        }
    }

    public Double getBankZins(){
        Double zinsen;
        if(getBank() != 0) {
            Double zinssatz = 0. + getBankLevel();
            Integer zinsamount = 100;
            zinsen = (getBank() / zinsamount) * zinssatz;
        }else{
            zinsen = 0.0;
        }
        return zinsen;
    }

    public Double getOfflineZins(){
        return cfg.getDouble(p.getUniqueId() + ".Offline");
    }

    public void addOfflineZins(Double amount){
        cfg.set(p.getUniqueId() + ".Offline", getOfflineZins() + amount);
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void resetOfflineZins(){
        cfg.set(p.getUniqueId() + ".Offline", 0);
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }
}
