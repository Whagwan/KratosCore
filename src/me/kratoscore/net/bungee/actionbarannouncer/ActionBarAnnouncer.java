package me.kratoscore.net.bungee.actionbarannouncer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.tasks.GlobalActionBarAnnouncements;
import me.kratoscore.net.bungee.tasks.ServerActionBarAnnouncements;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class ActionBarAnnouncer {
    public static ArrayList<ScheduledTask> announcementTasks = new ArrayList<>();
    static ProxyServer proxy = ProxyServer.getInstance();
    private static KratosCore instance = KratosCore.getInstance();

    public static void loadAnnouncements() {
        setDefaults();
        if (ActionBarAnnouncements.barannouncements.getFile().getBoolean("Announcements.Enabled", true)) {
            List<String> global = ActionBarAnnouncements.barannouncements.getFile()
                    .getStringList("Announcements.Global.Messages");
            if (!global.isEmpty()) {
                if (ActionBarAnnouncements.barannouncements.getFile().getBoolean("Announcements.Global.Enabled",
                        true)) {
                    int interval = ActionBarAnnouncements.barannouncements.getFile()
                            .getInt("Announcements.Global.Interval", 0);
                    if (interval > 0) {
                        GlobalActionBarAnnouncements g = new GlobalActionBarAnnouncements();
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
                List<String> servermessages = ActionBarAnnouncements.barannouncements.getFile()
                        .getStringList("Announcements." + server + ".Messages");
                if (!servermessages.isEmpty()) {
                    if (ActionBarAnnouncements.barannouncements.getFile()
                            .getBoolean("Announcements." + server + ".Enabled", false)) {
                        int interval = ActionBarAnnouncements.barannouncements.getFile()
                                .getInt("Announcements." + server + ".Interval", 0);
                        if (interval > 0) {

                            ServerActionBarAnnouncements s = new ServerActionBarAnnouncements(
                                    proxy.getServerInfo(server));
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
        Collection<String> check = ActionBarAnnouncements.barannouncements.getFile().getSection("Announcements")
                .getKeys();
        if (!check.contains("Enabled")) {
            ActionBarAnnouncements.barannouncements.getFile().set("Announcements.Enabled", true);
        }
        if (!check.contains("Global")) {
            ActionBarAnnouncements.barannouncements.getFile().set("Announcements.Global.Enabled", true);
            ActionBarAnnouncements.barannouncements.getFile().set("Announcements.Global.Interval", 150);

            List<String> l = new ArrayList<String>();
            l.add("&a&lWelcome to our network!");
            l.add("&aThis server is using &eKratosCore.");
            ActionBarAnnouncements.barannouncements.getFile().set("Announcements.Global.Messages", l);
        }
        for (String server : proxy.getServers().keySet()) {
            if (!check.contains(server)) {
                ActionBarAnnouncements.barannouncements.getFile().set("Announcements." + server + ".Enabled", false);
                ActionBarAnnouncements.barannouncements.getFile().set("Announcements." + server + ".Interval", 75);

                List<String> l = new ArrayList<String>();
                l.add("&aHello %p%, &eWelcome to the &a" + server + " &eserver!");
                l.add("&aThis server is using KratosCore!");
                ActionBarAnnouncements.barannouncements.getFile().set("Announcements." + server + ".Messages", l);
            }
        }
        ActionBarAnnouncements.barannouncements.save();
    }

    public static void reloadAnnouncements() {
        for (ScheduledTask task : announcementTasks) {
            task.cancel();
        }
        announcementTasks.clear();
        loadAnnouncements();
    }
}
