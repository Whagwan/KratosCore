package me.kratoscore.net.bungee.announcer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.tasks.GlobalAnnouncements;
import me.kratoscore.net.bungee.tasks.ServerAnnouncements;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class Announcer {
    public static ArrayList<ScheduledTask> announcementTasks = new ArrayList<>();
    static ProxyServer proxy = ProxyServer.getInstance();
    private static KratosCore instance = KratosCore.getInstance();

    public static void loadAnnouncements() {
        setDefaults();
        if (Announcements.announcements.getFile().getBoolean("Announcements.Enabled", true)) {
            List<String> global = Announcements.announcements.getFile().getStringList("Announcements.Global.Messages");
            if (!global.isEmpty()) {
                if (Announcements.announcements.getFile().getBoolean("Announcements.Global.Enabled", true)) {
                    int interval = Announcements.announcements.getFile().getInt("Announcements.Global.Interval", 0);
                    if (interval > 0) {
                        GlobalAnnouncements g = new GlobalAnnouncements();
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
                List<String> servermessages = Announcements.announcements.getFile()
                        .getStringList("Announcements." + server + ".Messages");
                if (!servermessages.isEmpty()) {
                    if (Announcements.announcements.getFile().getBoolean("Announcements." + server + ".Enabled",
                            false)) {
                        int interval = Announcements.announcements.getFile()
                                .getInt("Announcements." + server + ".Interval", 0);
                        if (interval > 0) {
                            ServerAnnouncements s = new ServerAnnouncements(proxy.getServerInfo(server));
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
        Collection<String> check = Announcements.announcements.getFile().getSection("Announcements").getKeys();
        if (!check.contains("Enabled")) {
            Announcements.announcements.getFile().set("Announcements.Enabled", true);
        }
        if (!check.contains("Global")) {
            Announcements.announcements.getFile().set("Announcements.Global.Enabled", true);
            Announcements.announcements.getFile().set("Announcements.Global.Interval", 150);
            List<String> l = new ArrayList<String>();
            l.add("&a&lWelcome to our network!");
            l.add("&aThis server is using KratosCore.");
            l.add("&aDon't forget to take a little look at our website!");
            Announcements.announcements.getFile().set("Announcements.Global.Messages", l);
        }
        for (String server : proxy.getServers().keySet()) {
            if (!check.contains(server)) {
                Announcements.announcements.getFile().set("Announcements." + server + ".Enabled", false);
                Announcements.announcements.getFile().set("Announcements." + server + ".Interval", 75);
                List<String> l = new ArrayList<String>();
                l.add("&aHello Everyone, &eWelcome to the &a" + server + " &eserver!");
                l.add("&aThis server is using KratosCore!");
                Announcements.announcements.getFile().set("Announcements." + server + ".Messages", l);
            }
        }
        Announcements.announcements.save();
    }

    public static void reloadAnnouncements() {
        for (ScheduledTask task : announcementTasks) {
            task.cancel();
        }
        announcementTasks.clear();
        loadAnnouncements();
    }
}