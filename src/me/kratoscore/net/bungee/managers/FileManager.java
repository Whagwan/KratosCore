package me.kratoscore.net.bungee.managers;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class FileManager {

    private File file;
    private Configuration config;
    private String key;

    public FileManager(String path) {
        this.key = System.getProperty("user.dir") + path;
        createFile();
        file = new File(this.key);
        load();
    }

    public Configuration getFile() {
        return this.config;
    }

    public void load() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(this.key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFile() {
        File config = new File(key);
        if (!config.exists()) {
            config.getParentFile().mkdirs();
            try {
                config.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
