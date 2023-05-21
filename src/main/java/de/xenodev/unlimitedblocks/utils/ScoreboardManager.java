package de.xenodev.unlimitedblocks.utils;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.xenodev.unlimitedblocks.CityBuild;
import de.xenodev.unlimitedblocks.MySQL.TimeAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreboardManager {

    private static Integer waitScore = 30;
    private static Boolean waitBoolean = false;

    public static void createScoreboard(Player p){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("ubsb", Criteria.DUMMY, "§7§l» §e§lUnlimitedBlocks §7§l«");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        EconomyManager economyManager = new EconomyManager(p);
        String moneyAmount = String.format("%.2f", economyManager.getMoney());
        IPermissionUser permuser = CloudNetDriver.getInstance().getPermissionManagement().getUser(p.getUniqueId());
        IPermissionGroup permgroup = CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(permuser);
        TimeAPI timeAPI = new TimeAPI();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        objective.getScore(updateTeam(scoreboard, "time", "   §2§l" + simpleDateFormat.format(new Date()), "", ChatColor.AQUA)).setScore(11);
        objective.getScore("§7§o").setScore(10);
        objective.getScore("§8§l» §7Rank").setScore(9);
        objective.getScore(updateTeam(scoreboard, "rank",permgroup.getDisplay().replace("&", "§") + "§l" + permgroup.getName(), "", ChatColor.YELLOW)).setScore(8);
        objective.getScore("§9§o").setScore(7);
        objective.getScore(updateTeam(scoreboard, "titlecurrent", "§8§l» §7Money", "", ChatColor.GREEN)).setScore(6);
        objective.getScore(updateTeam(scoreboard, "money", "§e" + moneyAmount + "€", "", ChatColor.BLUE)).setScore(5);
        objective.getScore("§1§o").setScore(4);
        objective.getScore("§8§l» §7Spielzeit").setScore(3);
        objective.getScore(updateTeam(scoreboard, "playtime", "§6" + timeAPI.getTime(p), "", ChatColor.RED)).setScore(2);
        objective.getScore("§3§o").setScore(1);

        p.setScoreboard(scoreboard);
    }

    public static void updateScoreboard(){
        new BukkitRunnable() {

            @Override
            public void run() {
                for(Player all : Bukkit.getOnlinePlayers()){
                    Scoreboard scoreboard = all.getScoreboard();
                    Objective objective = scoreboard.getObjective("ubsb");

                    EconomyManager economyManager = new EconomyManager(all);
                    String moneyAmount = String.format("%.2f", economyManager.getMoney());
                    String bankAmount = String.format("%.2f", economyManager.getBank());

                    IPermissionUser permuser = CloudNetDriver.getInstance().getPermissionManagement().getUser(all.getUniqueId());
                    IPermissionGroup permgroup = CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(permuser);
                    TimeAPI timeAPI = new TimeAPI();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                    objective.getScore(updateTeam(scoreboard, "time", "   §2§l" + simpleDateFormat.format(new Date()), "", ChatColor.AQUA)).setScore(11);
                    objective.getScore(updateTeam(scoreboard, "rank", permgroup.getDisplay().replace("&", "§") + "§l" + permgroup.getName(), "", ChatColor.YELLOW)).setScore(8);
                    objective.getScore(updateTeam(scoreboard, "playtime", "§6" + timeAPI.getTime(all), "", ChatColor.RED)).setScore(2);

                    if(waitBoolean == false){
                        objective.getScore(updateTeam(scoreboard, "titlecurrent", "§8§l» §7Bank", "", ChatColor.GREEN)).setScore(6);
                        objective.getScore(updateTeam(scoreboard, "bank", "§e" + bankAmount + "€", "", ChatColor.BLUE)).setScore(5);
                    }else{
                        objective.getScore(updateTeam(scoreboard, "titlecurrent", "§8§l» §7Money", "", ChatColor.GREEN)).setScore(6);
                        objective.getScore(updateTeam(scoreboard, "money", "§e" + moneyAmount + "€", "", ChatColor.BLUE)).setScore(5);
                    }
                    if(waitScore > 0){
                        waitScore--;
                    }
                    if(waitScore == 0){
                        if(waitBoolean == false){
                            waitBoolean = true;
                        }else{
                            waitBoolean = false;
                        }
                        waitScore = 30;
                    }
                }
            }

        }.runTaskTimerAsynchronously(CityBuild.getInstance(), 0, 20L);
    }

    public static String updateTeam(Scoreboard board, String Team, String prefix, String suffix, ChatColor entry) {
        org.bukkit.scoreboard.Team team = board.getTeam(Team);
        if(team == null) {
            team = board.registerNewTeam(Team);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.addEntry(entry.toString());

        return entry.toString();
    }

}
