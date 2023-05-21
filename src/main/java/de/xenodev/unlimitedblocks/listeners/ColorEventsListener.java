package de.xenodev.unlimitedblocks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.LinkedList;
import java.util.List;

public class ColorEventsListener implements Listener {

    @EventHandler
    public void handleSignChange(SignChangeEvent e){
        Player p = e.getPlayer();
        if(p.hasPermission("ub.event.sign.color")){
            String[] lines = e.getLines();
            for(int i = 0; i <= 3; i++){
                e.setLine(i, lines[i].replaceAll("&", "§"));
            }
        }
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent e) {
        Player p = e.getPlayer();
        if(p.hasPermission("ub.event.book.color")) {
            BookMeta bookMeta = e.getNewBookMeta();
            List<String> list = new LinkedList<>(bookMeta.getPages());
            list.replaceAll(page -> page.replace('&', '§'));
            bookMeta.setPages(list);
            e.setNewBookMeta(bookMeta);
        }
    }

}
