package me.kratoscore.net.bungee.listener;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class AntiSwear implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void Anticurse(ChatEvent event) {
        ProxiedPlayer p = (ProxiedPlayer) event.getSender();
        Boolean foundSwear = false;

        String startMessage = event.getMessage();

        String msg = startMessage.toLowerCase();
        BaseComponent[] antiswear = Utils
                .format(KratosCore.getInstance().getConfig().getString("AntiSwear.Message"));
        if (KratosCore.getInstance().getConfig().getBoolean("AntiSwear.Enabled")) {
            if (event.getMessage().startsWith("/ban") || event.getMessage().startsWith("/banip")
                    || event.getMessage().startsWith("/checkban") || event.getMessage().startsWith("/kick")
                    || event.getMessage().startsWith("/mute") || event.getMessage().startsWith("/pinfo")
                    || event.getMessage().startsWith("/playerinfo") || event.getMessage().startsWith("/tempban")
                    || event.getMessage().startsWith("/unban") || event.getMessage().startsWith("/tempmute")
                    || event.getMessage().startsWith("/warn")) {
                return;
            }
            for (String word : KratosCore.getInstance().getConfig().getStringList("AntiSwear.Blocked")) {
                if (KratosCore.getInstance().getConfig().getBoolean("AntiSwear.Advanced")) {
                    if ((msg.contains(word.toLowerCase())) || (msg.replace(" ", "").replace("*", "").replace("?", "")
                            .replace(".", "").replace(",", "").replace(";", "").replace(":", "").replace("/", "")
                            .replace("-", "").replace("_", "").contains(word.toLowerCase()))) {
                        String replacewith = KratosCore.getInstance().getConfig()
                                .getString("AntiSwear.Replace_With");
                        if (!replacewith.isEmpty()) {
                            event.setMessage(event.getMessage().replace(word, replacewith));
                            p.sendMessage(antiswear);
                            foundSwear = true;
                        } else {
                            event.setCancelled(true);
                            p.sendMessage(antiswear);
                            foundSwear = true;
                        }
                    }
                    continue;
                } else {
                    String replacewith = KratosCore.getInstance().getConfig().getString("AntiSwear.Replace_With");
                    if (!event.getMessage().contains(" ")) {
                        if (msg.toLowerCase().equalsIgnoreCase(word.toLowerCase())) {
                            if (!replacewith.isEmpty()) {
                                event.setMessage(
                                        event.getMessage().toLowerCase().replace(word.toLowerCase(), replacewith));
                                p.sendMessage(antiswear);
                                foundSwear = true;
                            } else {
                                event.setCancelled(true);
                                p.sendMessage(antiswear);
                                foundSwear = true;
                            }
                        }
                        continue;
                    }
                    Boolean b = false;
                    for (int i = 0; i < (msg.split(" ").length); i++) {
                        String words = msg.split(" ")[i];
                        if (words.toLowerCase().equalsIgnoreCase(word.toLowerCase())) {
                            b = true;
                            foundSwear = true;
                            break;
                        }
                    }
                    if (b) {
                        if (!replacewith.isEmpty()) {
                            event.setMessage(event.getMessage().toLowerCase().replace(word.toLowerCase(), replacewith));
                        } else {
                            event.setCancelled(true);
                        }
                        p.sendMessage(antiswear);
                    }
                }
            }
        }
        if (foundSwear) {
            for (BungeeUser user : KratosCore.getInstance().getStaff()) {
                for (String s : KratosCore.getInstance().getConfig().getStringList("AntiSwear.StaffMessage")) {
                    user.sendMessage(s.replace("%name%", p.getName())
                            .replace("%server%", p.getServer().getInfo().getName()).replace("%message%", startMessage));
                }
            }
        }
    }
}