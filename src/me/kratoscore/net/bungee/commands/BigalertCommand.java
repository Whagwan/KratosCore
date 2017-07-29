package me.kratoscore.net.bungee.commands;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;
import com.google.common.base.Joiner;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.config.Configuration;

public class BigalertCommand extends DBCommand {

    public BigalertCommand() {
        super("bigalert");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        this.onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args){
        if(args.length == 0){
            sender.sendMessage(Utils.format("&cPlease enter a message to alert."));
            return;
        }
        Configuration config = KratosCore.getInstance().getConfig();
        Boolean chat = config.getBoolean("BigAlert.Chat.Enabled"), title = config.getBoolean("BigAlert.Title.Enabled"), actionbar = config.getBoolean("BigAlert.ActionBar.Enabled");

        if(args.length == 2 && args[0].equalsIgnoreCase("config")){
            if(!KratosCore.getInstance().getConfig().contains("BigAlert.Messages." + args[1])){
                sender.sendMessage(Utils.format("&cThis message is does not exist in the config."));
                return;
            }
            String message = KratosCore.getInstance().getConfig().getString("BigAlert.Messages." + args[1]);
            if(chat){
                chat(message);
            }
            if(actionbar){
                bar(message);
            }
            if(title){
                String[] titles = message.split("%n");
                String tit = titles[0];
                String stit = (titles.length == 2 ? titles[1] : null);
                Integer in = config.getInt("BigAlert.Title.FadeIn"), stay = config.getInt("BigAlert.Title.Stay"), out = config.getInt("BigAlert.Title.FadeOut");

                title(in, stay, out, tit, stit);
            }
            return;
        }

        String message = Joiner.on(" ").join(args);
        if(chat){
            chat(message);
        }
        if(actionbar){
            bar(message);
        }
        if(title){
            String[] titles = message.split("%n");
            String tit = titles[0];
            String stit = (titles.length == 2 ? titles[1] : null);
            Integer in = config.getInt("BigAlert.Title.FadeIn"), stay = config.getInt("BigAlert.Title.Stay"), out = config.getInt("BigAlert.Title.FadeOut");

            title(in, stay, out, tit, stit);
        }
    }

    void chat(String message){
        for(BungeeUser user : KratosCore.getInstance().getUsers()){
            user.sendMessage(KratosCore.getInstance().getConfig().getString("Prefix") + message.replace("%p%", user.getName()).replaceAll("%n", " "));
        }
    }

    void bar(String message){
        for(BungeeUser user : KratosCore.getInstance().getUsers()){
            user.sendBar(message);
        }
    }

    void title(Integer in, Integer stay, Integer out, String title, String subtitle){
        for(BungeeUser user : KratosCore.getInstance().getUsers()){
            user.sendTitle(in, stay, out, title, subtitle);
        }
    }
}
