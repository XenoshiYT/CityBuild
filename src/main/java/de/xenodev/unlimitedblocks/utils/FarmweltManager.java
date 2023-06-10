package de.xenodev.unlimitedblocks.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import de.xenodev.unlimitedblocks.CityBuild;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FarmweltManager {

    public static void setupTimer(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if(getTime() == 0) {
                    resetWorld();
                }else{
                    removeTime(1);
                }
            }
        }.runTaskTimerAsynchronously(CityBuild.getInstance(), 0, 20);
    }

    public static void resetWorld(){
        for (Player all : Bukkit.getOnlinePlayers()) {
            World world = Bukkit.getWorld("farmwelt");
            LocationManager locationManager = new LocationManager("Spawn");
            for (Player worldPlayer : world.getPlayers()) {
                locationManager.teleportLocation(worldPlayer);
            }
            all.sendMessage(CityBuild.getPrefix() + "§7Die Farmwelt wird resetet...");
            Bukkit.unloadWorld(world, false);
            try{
                File worldFile = new File(Bukkit.getWorldContainer(), "farmwelt");
                FileUtils.deleteDirectory(worldFile);
            }catch (IOException e){
                e.printStackTrace();
            }

            WorldCreator worldCreator = new WorldCreator("farmwelt");
            worldCreator.environment(World.Environment.NORMAL);
            worldCreator.type(WorldType.NORMAL);
            worldCreator.generateStructures(true);
            worldCreator.createWorld();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            setupSpawn();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }.runTaskLater(CityBuild.getInstance(), 20*3);
            setTime(604800);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
        }
    }


    public static void setupSpawn() throws IOException {
        File file = new File("plugins//FastAsyncWorldEdit//schematics", "Farmwelt.schem");
        ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
        ClipboardReader clipboardReader = clipboardFormat.getReader(new FileInputStream(file));
        Clipboard clipboard = clipboardReader.read();

        com.sk89q.worldedit.world.World genWorld = BukkitAdapter.adapt(Bukkit.getWorld("farmwelt"));
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(genWorld, -1);

        Location location = new Location(Bukkit.getWorld("farmwelt"), 0, 0, 0);
        Block block = Bukkit.getWorld("farmwelt").getHighestBlockAt(location);
        Integer x = block.getX();
        Integer y = 120;
        Integer z = block.getZ();

        Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(BlockVector3.at(x, y, z)).ignoreAirBlocks(false).build();
        try {
            Operations.complete(operation);
            editSession.flushSession();
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
        Bukkit.broadcastMessage(CityBuild.getPrefix() + "§7Die Farmwelt wurde generiert...");
    }

    private static Integer getTime(){
        return CityBuild.getInstance().getConfig().getInt("resettime");
    }

    private static void setTime(Integer time){
        CityBuild.getInstance().getConfig().set("resettime", time);
        CityBuild.getInstance().saveConfig();
    }

    private static void removeTime(Integer time){
        setTime(getTime() - time);
    }
}
