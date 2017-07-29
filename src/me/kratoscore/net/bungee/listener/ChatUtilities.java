package me.kratoscore.net.bungee.listener;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

public class ChatUtilities implements Listener {

    @EventHandler
    public void Chat(ChatEvent event) {
        String message = event.getMessage();
        if ((!message.endsWith("!")) && (!message.endsWith("?")) && (!message.endsWith("."))
                && (!message.startsWith("/") && (KratosCore.getInstance().getConfig()
                .getBoolean("ChatOptions.point-after-sentence")))) {
            event.setMessage(message + ".");
        }

        if (KratosCore.getInstance().getConfig().getBoolean("ChatOptions.first-letter-uppercase")) {
            if (!event.getMessage().toLowerCase().startsWith("http")) {
                String msg = event.getMessage().trim();
                event.setMessage(("" + msg.charAt(0)).toUpperCase() + msg.substring(1));
            }
        }

        if (KratosCore.getInstance().getConfig().getBoolean("ChatOptions.only-letters-and-numbers.enabled")) {
            String msg = event.getMessage();
            Configuration cs = KratosCore.getInstance().getConfig()
                    .getSection("ChatOptions.only-letters-and-numbers.replace");
            for (String s : cs.getKeys()) {
                String replacement = cs.getString(s);
                msg = msg.replaceAll(s, replacement);
            }
            String mess = msg;
            msg = msg.replaceAll("\\P{Print}", "");
            if (mess.equals(msg)) {
                return;
            }
            if (msg.isEmpty()) {
                event.setCancelled(true);
            }
            event.setMessage(msg);
            ProxiedPlayer p = (ProxiedPlayer) event.getSender();
            p.sendMessage(Utils.format(KratosCore.getInstance().getConfig()
                    .getString("ChatOptions.only-letters-and-numbers.message")));
        }
    }
}
