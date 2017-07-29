package me.kratoscore.net.bungee.announcer;


import java.io.File;

import me.kratoscore.net.bungee.managers.FileManager;

public class Announcements {

    private static String path = File.separator + "plugins" + File.separator + "KratosCore" + File.separator
            + "announcer.yml";
    public static FileManager announcements = new FileManager(path);

    public static void reloadAnnouncements() {
        announcements = null;
        announcements = new FileManager(path);
        Announcer.reloadAnnouncements();
    }
}