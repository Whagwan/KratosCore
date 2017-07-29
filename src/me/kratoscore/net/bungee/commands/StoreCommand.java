package me.kratoscore.net.bungee.commands;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class StoreCommand extends DBCommand {

    public StoreCommand() {
        super("store");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        TextComponent click = new TextComponent(ChatColor.translateAlternateColorCodes('&', KratosCore
                .getInstance().getConfig().getString("Store.Text").replace("%player%", sender.getName())));
        click.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', KratosCore.getInstance()
                                .getConfig().getString("Store.Hover").replace("%player%", sender.getName())))
                                .create()));
        click.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                KratosCore.getInstance().getConfig().getString("Store.Site")));
        sender.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("Store.Header")));

        for (String links : KratosCore.getInstance().getConfig().getStringList("Store.Message")) {
            sender.sendMessage(Utils.format(links.replace("%player%", sender.getName())));
        }
        sender.sendMessage(click);
        sender.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("Store.Footer")));
    }
}
