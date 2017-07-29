package me.kratoscore.net.bungee.punishment.commands;

import java.util.Arrays;
import java.util.UUID;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.events.WarnEvent;
import me.kratoscore.net.bungee.punishment.Punishments;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.PlayerInfo;
import me.kratoscore.net.bungee.utils.PluginMessageChannel;
import me.kratoscore.net.bungee.utils.UUIDFetcher;
import me.kratoscore.net.bungee.utils.Utils;
import com.google.common.base.Joiner;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class WarnCommand extends DBCommand {

    public WarnCommand() {
        super("warn");
    }

    public static void executeWarnCommand(CommandSender sender, String[] args) {

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            executeWarnCommand(sender, args);
            return;
        }
        if (KratosCore.getInstance().getConfig().getBoolean("Bukkit-Permissions")) {
            PluginMessageChannel.sendPermissionCheckPluginMessage("hasPermission", "kratoscore.warn", "warn", args,
                    ((ProxiedPlayer) sender));
            return;
        }
        if (sender.hasPermission("kratoscore.warn") || sender.hasPermission("kratoscore.*")) {
            executeWarnCommand(sender, args);
        } else {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Messages.NoPermission")) {
                sender.sendMessage(Utils.format(s));
            }
        }
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Boolean useUUIDs = KratosCore.getInstance().getConfigData().UUIDSTORAGE;
        if (args.length < 2) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Warn.Messages.WrongArgs")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        UUID uuid = UUIDFetcher.getUUIDOf(args[0]);
        if (uuid == null && useUUIDs) {
            return;
        }
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
        String reason = Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length));
        if (p == null) {
            for (String s : Punishments.punishments.getFile()
                    .getStringList("Punishments.Warn.Messages.OfflinePlayer")) {
                sender.sendMessage(Utils.format(s));
            }
            return;

        }
        for (String s : Punishments.punishments.getFile().getStringList("Punishments.Warn.Messages.WarnMessage")) {
            p.sendMessage(Utils.format(s.replace("%player%", sender.getName()).replace("%reason%", reason)));
        }
        WarnEvent event = new WarnEvent(sender.getName(), p.getName(), reason);
        BungeeCord.getInstance().getPluginManager().callEvent(event);

        PlayerInfo pinfo = new PlayerInfo(useUUIDs ? uuid.toString() : args[0]);
        pinfo.addWarn();
        for (String s : Punishments.punishments.getFile().getStringList("Punishments.Mute.Messages.Warned")) {
            sender.sendMessage(Utils.format(s.replace("%player%", args[0]).replace("%reason%", reason)));
        }
    }
}