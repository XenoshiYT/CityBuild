package de.xenodev.unlimitedblocks;

import de.xenodev.unlimitedblocks.commands.*;
import de.xenodev.unlimitedblocks.listeners.*;
import de.xenodev.unlimitedblocks.utils.*;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class CityBuild extends JavaPlugin {

    private static CityBuild instance;
    private static Economy economy;
    private static Chat chat;
    private static Permission permission;
    private static VanishManager vanishManager;
    private static String prefix = "§e§lUnlimitedBlocks §8| ";

    @Override
    public void onEnable() {
        instance = this;
        vanishManager = new VanishManager(this);
        if(!setupEconomy()){
            getServer().getConsoleSender().sendMessage(prefix + "§7Das Plugin §6§lVault §7wurde nicht gefunden! \n §7Das Plugin wird §c§ldeaktiviert");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();

        if(!new File("plugins/" + getName(), "config.yml").exists()){
            saveDefaultConfig();
        }

        events(getServer().getPluginManager());
        commands();
        setupZins();
        AutomessageManager.startAutomessage();
        ScoreboardManager.updateScoreboard();
    }

    @Override
    public void onDisable() {

    }

    private void commands(){
        getCommand("bank").setExecutor(new BankCMD());
        getCommand("broadcast").setExecutor(new BroadcastCMD());
        getCommand("clearchat").setExecutor(new ClearchatCMD());
        getCommand("enderchest").setExecutor(new EnderchestCMD());
        getCommand("feed").setExecutor(new FeedCMD());
        getCommand("fly").setExecutor(new FlyCMD());
        getCommand("gamemode").setExecutor(new GamemodeCMD());
        getCommand("heal").setExecutor(new HealCMD());
        getCommand("invsee").setExecutor(new InvseeCMD());
        getCommand("ping").setExecutor(new PingCMD());
        getCommand("rename").setExecutor(new RenameCMD());
        getCommand("repair").setExecutor(new RepairCMD());
        getCommand("reward").setExecutor(new RewardCMD());
        getCommand("signate").setExecutor(new SignateCMD());
        getCommand("skull").setExecutor(new SkullCMD());
        getCommand("spawn").setExecutor(new SpawnCMD());
        getCommand("tp").setTabCompleter(new TeleportCMD());
        getCommand("tphere").setTabCompleter(new TeleportCMD());
        getCommand("tpall").setTabCompleter(new TeleportCMD());
        getCommand("time").setExecutor(new TimeCMD());
        getCommand("vanish").setExecutor(new VanishCMD());
        getCommand("warp").setExecutor(new WarpCMD());
        getCommand("weather").setExecutor(new WeatherCMD());
        getCommand("workbench").setExecutor(new WorkbenchCMD());
        getCommand("playerinfo").setExecutor(new PlayerinfoCMD());
        getCommand("tpa").setExecutor(new TpaCMD());
        getCommand("tpaccept").setExecutor(new TpaCMD());
        getCommand("tpdeny").setExecutor(new TpaCMD());
        getCommand("msg").setExecutor(new MsgCMD());
        getCommand("r").setExecutor(new MsgCMD());
        getCommand("settings").setExecutor(new SettingsCMD());
    }

    private void tabcomplets(){
        getCommand("bank").setTabCompleter(new BankCMD());
        getCommand("broadcast").setTabCompleter(new BroadcastCMD());
        getCommand("clearchat").setTabCompleter(new ClearchatCMD());
        getCommand("enderchest").setTabCompleter(new EnderchestCMD());
        getCommand("feed").setTabCompleter(new FeedCMD());
        getCommand("fly").setTabCompleter(new FlyCMD());
        getCommand("gamemode").setTabCompleter(new GamemodeCMD());
        getCommand("heal").setTabCompleter(new HealCMD());
        getCommand("invsee").setTabCompleter(new InvseeCMD());
        getCommand("ping").setTabCompleter(new PingCMD());
        getCommand("rename").setTabCompleter(new RenameCMD());
        getCommand("repair").setTabCompleter(new RepairCMD());
        getCommand("reward").setTabCompleter(new RewardCMD());
        getCommand("signate").setTabCompleter(new SignateCMD());
        getCommand("skull").setTabCompleter(new SkullCMD());
        getCommand("spawn").setTabCompleter(new SpawnCMD());
        getCommand("tp").setTabCompleter(new TeleportCMD());
        getCommand("tphere").setTabCompleter(new TeleportCMD());
        getCommand("tpall").setTabCompleter(new TeleportCMD());
        getCommand("time").setTabCompleter(new TimeCMD());
        getCommand("vanish").setTabCompleter(new VanishCMD());
        getCommand("warp").setTabCompleter(new WarpCMD());
        getCommand("weather").setTabCompleter(new WeatherCMD());
        getCommand("playerinfo").setTabCompleter(new PlayerinfoCMD());
        getCommand("tpa").setTabCompleter(new TpaCMD());
        getCommand("tpaccept").setTabCompleter(new TpaCMD());
        getCommand("tpdeny").setTabCompleter(new TpaCMD());
        getCommand("msg").setTabCompleter(new MsgCMD());
        getCommand("r").setTabCompleter(new MsgCMD());
        getCommand("settings").setTabCompleter(new SettingsCMD());
    }

    private void events(PluginManager pluginManager){
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new ColorEventsListener(), this);
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new LoginListener(), this);
        pluginManager.registerEvents(new PingListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new BankListener(), this);
        pluginManager.registerEvents(new SettingsListener(), this);
    }

    public static CityBuild getInstance() {
        return instance;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static VanishManager getVanishManager() {
        return vanishManager;
    }

    public static String getPrefix() {
        return prefix;
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();
        return permission != null;
    }

    private void setupZins(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                for(String stringName : EconomyManager.getBanks()){
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(stringName));
                    EconomyManager economyManager = new EconomyManager(offlinePlayer);
                    Double zinsAmount = economyManager.getBankZins();
                    if(offlinePlayer.isOnline()){
                        economyManager.addBank(zinsAmount * 2);
                        offlinePlayer.getPlayer().sendMessage(getPrefix() + "§7Neuer Zinssatz in Höhe von: §e" + String.format("%.2f", (zinsAmount * 2)) + "€");
                    }else{
                        economyManager.addBank(zinsAmount);
                        economyManager.addOfflineZins(zinsAmount);
                    }
                }
            }
        }, 20L, 20L);
    }
}