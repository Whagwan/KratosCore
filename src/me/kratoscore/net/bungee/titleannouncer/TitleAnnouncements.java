package me.kratoscore.net.bungee.titleannouncer;

import java.io.File;

import me.kratoscore.net.bungee.managers.FileManager;

public class TitleAnnouncements {
    private static String path = File.separator + "plugins" + File.separator + "KratosCore" + File.separator
            + "titleannouncer.yml";
    public static FileManager titleannouncements = new FileManager(path);

    public static void reloadAnnouncements() {
        titleannouncements = null;
        titleannouncements = new FileManager(path);
        TitleAnnouncer.reloadAnnouncements();
    }
}