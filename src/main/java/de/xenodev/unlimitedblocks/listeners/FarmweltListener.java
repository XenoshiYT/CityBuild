package de.xenodev.unlimitedblocks.listeners;

import de.xenodev.unlimitedblocks.CityBuild;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class FarmweltListener implements Listener {

    @EventHandler
    public void handleFarmSign(SignChangeEvent e){
        Player p = e.getPlayer();
        if(p.hasPermission("ub.event.sign.farmwelt")){
            if(e.getLine(0).equalsIgnoreCase("[Farmwelt]")){
                e.setLine(0, "§c§k§l------------");
                e.setLine(1, "§2§lFarmwelt");
                e.setLine(2, "§7§oKlicke für TP");
                e.setLine(3, "§c§k§l------------");
            }
        }
    }

    @EventHandler
    public void handleInteractFarmSign(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getState() instanceof Sign) {
            String signString = ((Sign) e.getClickedBlock().getState()).getLine(1);
            if(signString.equalsIgnoreCase("§2§lFarmwelt")) {
                if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    loadteleport(p);
                }
            }
        }
    }

    private void loadteleport(Player p){
        Integer maximumX = 1000;
        Integer maximumZ = 1000;
        Integer minimumX = 100;
        Integer minimumZ = 100;

        Integer randomX = new Random().nextInt(maximumX) / new Random().nextInt(minimumX);
        Integer randomZ = new Random().nextInt(maximumZ) / new Random().nextInt(minimumZ);

        Integer finishX = (randomX / 2) * ((maximumX - randomX) + (minimumX + randomX));
        Integer finishZ = (randomZ / 2) * ((maximumZ - randomZ) + (minimumZ + randomZ));

        Location loc = p.getWorld().getSpawnLocation();
        loc.setX(finishX);
        loc.setZ(finishZ);
        Block b = p.getWorld().getHighestBlockAt(loc);
        if(b.getType().equals(Material.WATER) || b.getType().equals(Material.LAVA)){
            loadteleport(p);
            return;
        }
        loc.setY(b.getLocation().getBlockY() + 2);
        p.teleport(loc);
    }

}
