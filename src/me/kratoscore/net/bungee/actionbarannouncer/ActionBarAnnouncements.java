package me.kratoscore.net.bungee.actionbarannouncer;

import java.io.File;

import me.kratoscore.net.bungee.managers.FileManager;

public class ActionBarAnnouncements {
    private static String path = File.separator + "plugins" + File.separator + "KratosCore" + File.separator
            + "barannouncer.yml";
    public static FileManager barannouncements = new FileManager(path);

    public static void reloadAnnouncements() {
        barannouncements = null;
        barannouncements = new FileManager(path);
        ActionBarAnnouncer.reloadAnnouncements();
    }
}
