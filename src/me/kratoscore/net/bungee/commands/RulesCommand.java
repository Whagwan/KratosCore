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

public class RulesCommand extends DBCommand {

    public RulesCommand() {
        super("rules");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        TextComponent click = new TextComponent(ChatColor.translateAlternateColorCodes('&', KratosCore
                .getInstance().getConfig().getString("Rules.Text").replace("%player%", sender.getName())));
        click.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', KratosCore.getInstance()
                                .getConfig().getString("Rules.Hover").replace("%player%", sender.getName())))
                                .create()));
        click.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                KratosCore.getInstance().getConfig().getString("Rules.Site")));

        sender.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("Rules.Header")));
        for (String rules : KratosCore.getInstance().getConfig().getStringList("Rules.Rules")) {
            sender.sendMessage(Utils.format(rules.replace("%player%", sender.getName())));
        }
        sender.sendMessage(click);
        sender.sendMessage(Utils.format(KratosCore.getInstance().getConfig().getString("Rules.Footer")));
    }
}
