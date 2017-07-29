package me.kratoscore.net.bungee.titleannouncer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.tasks.GlobalTitleAnnouncements;
import me.kratoscore.net.bungee.tasks.ServerTitleAnnouncements;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class TitleAnnouncer {
    public static ArrayList<ScheduledTask> announcementTasks = new ArrayList<>();
    static ProxyServer proxy = ProxyServer.getInstance();
    private static KratosCore instance = KratosCore.getInstance();

    public static void loadAnnouncements() {
        setDefaults();
        if (TitleAnnouncements.titleannouncements.getFile().getBoolean("Announcements.Enabled", true)) {
            List<String> global = TitleAnnouncements.titleannouncements.getFile()
                    .getStringList("Announcements.Global.Messages");
            if (!global.isEmpty()) {
                if (TitleAnnouncements.titleannouncements.getFile().getBoolean("Announcements.Global.Enabled", true)) {
                    int interval = TitleAnnouncements.titleannouncements.getFile()
                            .getInt("Announcements.Global.Interval", 0);
                    if (interval > 0) {
                        GlobalTitleAnnouncements g = new GlobalTitleAnnouncements();
                        for (String messages : global) {
                            g.addAnnouncement(messages);
                        }
                        ScheduledTask t = proxy.getScheduler().schedule(instance, g, interval, interval,
                                TimeUnit.SECONDS);
                        announcementTasks.add(t);
                    }
                }
            }
            for (String server : proxy.getServers().keySet()) {
                List<String> servermessages = TitleAnnouncements.titleannouncements.getFile()
                        .getStringList("Announcements." + server + ".Messages");
                if (!servermessages.isEmpty()) {
                    if (TitleAnnouncements.titleannouncements.getFile()
                            .getBoolean("Announcements." + server + ".Enabled", false)) {
                        int interval = TitleAnnouncements.titleannouncements.getFile()
                                .getInt("Announcements." + server + ".Interval", 0);
                        if (interval > 0) {
                            int fadeIn = TitleAnnouncements.titleannouncements.getFile()
                                    .getInt("Announcements." + server + ".FadeIn", 30);
                            int stay = TitleAnnouncements.titleannouncements.getFile()
                                    .getInt("Announcements." + server + ".Stay", 60);
                            int fadeOut = TitleAnnouncements.titleannouncements.getFile()
                                    .getInt("Announcements." + server + ".FadeOut", 30);

                            ServerTitleAnnouncements s = new ServerTitleAnnouncements(proxy.getServerInfo(server),
                                    fadeIn, stay, fadeOut);
                            for (String messages : servermessages) {
                                s.addAnnouncement(messages);
                            }
                            ScheduledTask t = proxy.getScheduler().schedule(instance, s, interval, interval,
                                    TimeUnit.SECONDS);
                            announcementTasks.add(t);
                        }
                    }
                }
            }
        }
    }

    private static void setDefaults() {
        Collection<String> check = TitleAnnouncements.titleannouncements.getFile().getSection("Announcements")
                .getKeys();
        if (!check.contains("Enabled")) {
            TitleAnnouncements.titleannouncements.getFile().set("Announcements.Enabled", true);
        }
        if (!check.contains("Global")) {
            TitleAnnouncements.titleannouncements.getFile().set("Announcements.Global.Enabled", true);
            TitleAnnouncements.titleannouncements.getFile().set("Announcements.Global.Interval", 150);

            TitleAnnouncements.titleannouncements.getFile().set("Announcements.Global.FadeIn", 30);
            TitleAnnouncements.titleannouncements.getFile().set("Announcements.Global.Stay", 60);
            TitleAnnouncements.titleannouncements.getFile().set("Announcements.Global.FadeOut", 30);

            List<String> l = new ArrayList<String>();
            l.add("&a&lWelcome to our network!");
            l.add("&aThis server is using%n&e&lKratosCore.");
            TitleAnnouncements.titleannouncements.getFile().set("Announcements.Global.Messages", l);
        }
        for (String server : proxy.getServers().keySet()) {
            if (!check.contains(server)) {
                TitleAnnouncements.titleannouncements.getFile().set("Announcements." + server + ".Enabled", false);
                TitleAnnouncements.titleannouncements.getFile().set("Announcements." + server + ".Interval", 75);

                TitleAnnouncements.titleannouncements.getFile().set("Announcements." + server + ".FadeIn", 30);
                TitleAnnouncements.titleannouncements.getFile().set("Announcements." + server + ".Stay", 60);
                TitleAnnouncements.titleannouncements.getFile().set("Announcements." + server + ".FadeOut", 30);

                List<String> l = new ArrayList<String>();
                l.add("&aHello %p%,%n&eWelcome to the &a" + server + " &eserver!");
                l.add("&aThis server is using KratosCore!");
                TitleAnnouncements.titleannouncements.getFile().set("Announcements." + server + ".Messages", l);
            }
        }
        TitleAnnouncements.titleannouncements.save();
    }

    public static void reloadAnnouncements() {
        for (ScheduledTask task : announcementTasks) {
            task.cancel();
        }
        announcementTasks.clear();
        loadAnnouncements();
    }
}