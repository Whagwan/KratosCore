package me.kratoscore.net.bungee.listener;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        final ProxiedPlayer p = event.getPlayer();
        if (KratosCore.getInstance().getUser(p) != null) {
            return;
        }
        Runnable r = new Runnable() {

            @Override
            public void run() {
                KratosCore.getInstance().users.add(new BungeeUser(p));
            }
        };
        BungeeCord.getInstance().getScheduler().runAsync(KratosCore.getInstance(), r);
    }

}
