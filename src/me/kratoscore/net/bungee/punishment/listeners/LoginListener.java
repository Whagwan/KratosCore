package me.kratoscore.net.bungee.punishment.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.punishment.BanAPI;
import me.kratoscore.net.bungee.punishment.Punishments;
import me.kratoscore.net.bungee.utils.PlayerInfo;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onLogin(final LoginEvent event) {
        PlayerInfo info;
        if (KratosCore.getInstance().getConfigData().UUIDSTORAGE) {
            info = new PlayerInfo(event.getConnection().getUniqueId().toString());
        } else {
            info = new PlayerInfo(event.getConnection().getName());
        }
        if (!info.isInTable()) {
            info.putPlayerInTable(Utils.getAddress(event.getConnection().getAddress()), 0, 0, 0, 0);
        }

        String IP = Utils.getAddress(event.getConnection().getAddress());
        if (BanAPI.isIPBanned(IP)) {
            String reason = "";
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.BanIP.Messages.KickMessage")) {
                reason = reason + "\n" + s;
            }

            event.setCancelled(true);
            event.setCancelReason(reason.replace("%banner%", BanAPI.getIPBannedBy(IP)).replace("%unbantime%", "Never")
                    .replace("%reason%", BanAPI.getIPReason(IP)).replaceAll("&", "§"));
            return;
        }
        if (KratosCore.getInstance().getConfigData().UUIDSTORAGE) {
            if (BanAPI.isBanned(event.getConnection().getUniqueId().toString())) {
                Long time = BanAPI.getBanTime(event.getConnection().getUniqueId().toString());
                if (time < System.currentTimeMillis() && time != -1) {
                    BanAPI.removeBan(event.getConnection().getUniqueId().toString());
                    return;
                }
                if (time != -1) {
                    String reason = "";
                    for (String s : Punishments.punishments.getFile()
                            .getStringList("Punishments.Tempban.Messages.KickMessage")) {
                        reason = reason + "\n" + s;
                        SimpleDateFormat df2 = new SimpleDateFormat("kk:mm dd/MM/yyyy");
                        String date = df2.format(new Date(time));

                        event.setCancelled(true);
                        event.setCancelReason(reason
                                .replace("%banner%", BanAPI.getBannedBy(event.getConnection().getUniqueId().toString()))
                                .replace("%unbantime%", (time == -1L ? "Never" : date))
                                .replace("%reason%", BanAPI.getReason(event.getConnection().getUniqueId().toString()))
                                .replaceAll("&", "§"));
                    }
                    return;
                }
                String reason = "";
                for (String s : Punishments.punishments.getFile()
                        .getStringList("Punishments.Ban.Messages.KickMessage")) {
                    reason = reason + "\n" + s;
                }
                SimpleDateFormat df2 = new SimpleDateFormat("kk:mm dd/MM/yyyy");
                String date = df2.format(new Date(time));

                event.setCancelled(true);
                event.setCancelReason(
                        reason.replace("%banner%", BanAPI.getBannedBy(event.getConnection().getUniqueId().toString()))
                                .replace("%unbantime%", (time == -1L ? "Never" : date))
                                .replace("%reason%", BanAPI.getReason(event.getConnection().getUniqueId().toString()))
                                .replaceAll("&", "§"));
            }
        }
        if (BanAPI.isBanned(event.getConnection().getName())) {
            Long time = BanAPI.getBanTime(event.getConnection().getName());
            if (time < System.currentTimeMillis() && time != -1) {
                BanAPI.removeBan(event.getConnection().getName());
                return;
            }
            if (time != -1) {
                String reason = "";
                for (String s : Punishments.punishments.getFile()
                        .getStringList("Punishments.Tempban.Messages.KickMessage")) {
                    reason = reason + "\n" + s;
                    SimpleDateFormat df2 = new SimpleDateFormat("kk:mm dd/MM/yyyy");
                    String date = df2.format(new Date(time));

                    event.setCancelled(true);
                    event.setCancelReason(
                            reason.replace("%banner%", BanAPI.getBannedBy(event.getConnection().getName()))
                                    .replace("%unbantime%", (time == -1L ? "Never" : date))
                                    .replace("%reason%", BanAPI.getReason(event.getConnection().getName()))
                                    .replaceAll("&", "§"));
                }
                return;
            }
            String reason = "";
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Ban.Messages.KickMessage")) {
                reason = reason + "\n" + s;
            }
            SimpleDateFormat df2 = new SimpleDateFormat("kk:mm dd/MM/yyyy");
            String date = df2.format(new Date(time));

            event.setCancelled(true);
            event.setCancelReason(reason.replace("%banner%", BanAPI.getBannedBy(event.getConnection().getName()))
                    .replace("%unbantime%", (time == -1L ? "Never" : date))
                    .replace("%reason%", BanAPI.getReason(event.getConnection().getName())).replaceAll("&", "§"));
        }
    }
}