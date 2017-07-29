package me.kratoscore.net.bungee.party;

import java.util.ArrayList;
import java.util.List;

import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyEvents implements Listener {

    List<ProxiedPlayer> justLogged = new ArrayList<ProxiedPlayer>();

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        justLogged.add(event.getPlayer());
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent e) {
        if (justLogged.contains(e.getPlayer())) {
            justLogged.remove(e.getPlayer());
            return;
        }
        if (Party.party.getFile().getBoolean("Party.Server-Switch", true)) {
            ProxiedPlayer p = e.getPlayer();
            PartyManager.warpParty(p);
        }
    }

    @EventHandler
    public void leave(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();
        if (PartyManager.partyowner.contains(p.getName())) {
            PartyManager.leave(p);
        }
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        if ((e.getSender() instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) e.getSender();
            if (e.getMessage().startsWith("@")) {
                String msg = e.getMessage();
                if ((PartyManager.inparty.containsKey(p.getName()))
                        || (PartyManager.partyowner.contains(p.getName()))) {
                    PartyManager.chat(p, msg.replaceFirst("@", ""));
                    e.setCancelled(true);
                } else {
                    e.setCancelled(true);
                    p.sendMessage(Utils.format(
                            Party.party.getFile().getString("Party.Messages.NotInParty", "&cYou are not in a party!")));
                }
            } else {
                return;
            }
        } else {
            return;
        }
    }
}
