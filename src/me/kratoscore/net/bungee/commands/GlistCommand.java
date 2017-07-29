package me.kratoscore.net.bungee.commands;

import java.util.ArrayList;
import java.util.Collections;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

public class GlistCommand extends DBCommand {

    private static KratosCore instance = KratosCore.getInstance();

    public GlistCommand() {
        super("glist", instance.getConfig().getString("GList.Aliase"));
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args){
        String color = instance.getConfig().getString("GList.PlayerListColor");
        if(!instance.getConfig().getBoolean("GList.Custom_GList")){
            for (ServerInfo server : ProxyServer.getInstance().getServers().values()){
                ArrayList<String> list = Lists.newArrayList();

                for (ProxiedPlayer pl : server.getPlayers()) {
                    list.add(pl.getDisplayName());
                }
                Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
                sender.sendMessage(Utils.format(instance.getConfig().getString("GList.Format").replace("%server%", server.getName())
                        .replace("%players%", String.valueOf(server.getPlayers().size())).replace("%playerlist%", Joiner.on(color + ", " + color).join(list))));
            }
            sender.sendMessage(Utils.format(instance.getConfig().getString("GList.Total").replace("%totalnum%", String.valueOf(ProxyServer.getInstance().getPlayers().size()))));
        } else {
            Configuration cs = instance.getConfig().getSection("GList.Servers");
            for(String key : cs.getKeys()){
                Integer serverPlayers = 0;
                ArrayList<String> list = Lists.newArrayList();

                String value = cs.getString(key);
                if(value.contains(",")){
                    for(String calculate : value.split(",")){
                        ServerInfo server = ProxyServer.getInstance().getServerInfo(calculate);
                        if(server == null){
                            continue;
                        }
                        serverPlayers += server.getPlayers().size();
                        for(ProxiedPlayer pl : server.getPlayers()){
                            list.add(pl.getDisplayName());
                        }
                    }
                } else {
                    ServerInfo server = ProxyServer.getInstance().getServerInfo(value);
                    if(server != null){
                        serverPlayers += server.getPlayers().size();
                        for(ProxiedPlayer pl : server.getPlayers()){
                            list.add(pl.getDisplayName());
                        }
                    }
                }
                Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
                sender.sendMessage(Utils.format(instance.getConfig().getString("GList.Format").replace("%server%", key)
                        .replace("%playerlist%", Joiner.on(color + ", " + color).join(list)).replace("%players%", String.valueOf(serverPlayers.toString()))));
            }
            sender.sendMessage(Utils.format(instance.getConfig().getString("GList.Total").replace("%totalnum%", String.valueOf(ProxyServer.getInstance().getPlayers().size()))));
        }
    }
}