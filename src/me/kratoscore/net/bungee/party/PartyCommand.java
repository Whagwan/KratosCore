package me.kratoscore.net.bungee.party;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyCommand extends DBCommand {

    public PartyCommand() {
        super("party");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        if (args.length == 0) {
            for (String s : Party.party.getFile().getStringList("Party.Messages.Help")) {
                user.sendMessage(s);
            }
            return;
        }
        if (args.length == 0) {
            for (String s : Party.party.getFile().getStringList("Party.Messages.Help")) {
                user.sendMessage(s);
            }
            return;
        }

        if (args.length == 1) {
            if (args[0].contains("create")) {
                PartyManager.createParty(user.getPlayer());
                return;
            }
            if (args[0].contains("list")) {
                PartyManager.partyList(user.getPlayer());
                return;
            }
            if (args[0].contains("leave")) {
                PartyManager.leave(user.getPlayer());
                return;
            }
            if (args[0].contains("accept")) {
                PartyManager.accept(user.getPlayer());
                return;
            }
            if (args[0].contains("help")) {
                for (String s : Party.party.getFile().getStringList("Party.Messages.Help")) {
                    user.sendMessage(s);
                }
                return;
            }
            if (args[0].contains("warp")) {
                PartyManager.warpParty(user.getPlayer());
                return;
            }
        }
        if (args.length == 2) {
            if (args[0].contains("invite")) {
                if (args.length > 1) {
                    for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
                        if (x.getName().equalsIgnoreCase(args[1])) {
                            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
                            PartyManager.invite(user.getPlayer(), p);
                            return;
                        }
                    }
                    user.sendMessage(Party.party.getFile().getString("Party.Messages.OfflinePlayer",
                            "&cThat player is not online!"));
                    return;
                } else {
                    user.sendMessage(Party.party.getFile().getString("Party.Messages.WrongArgs",
                            "&cPlease use /party help for more info."));
                }
            }
            if (args[0].contains("admin")) {
                if (user.getName() == args[1]) {
                    user.sendMessage(Party.party.getFile().getString("Party.Messages.AdminError",
                            "&cYou cannot make yourself admin!"));
                    return;
                } else {
                    PartyManager.addAdmin(user.getPlayer(), args[1]);
                    return;
                }
            }
            if (args[0].contains("kick")) {
                if (args.length > 1) {
                    for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
                        if (x.getName().equalsIgnoreCase(args[1])) {
                            PartyManager.kick(user.getPlayer(), x);
                            return;
                        }
                    }
                    user.sendMessage(Party.party.getFile().getString("Party.Messages.OfflinePlayer",
                            "&cThat player is not online!"));
                    return;
                } else {
                    user.sendMessage(Party.party.getFile().getString("Party.Messages.WrongArgs",
                            "&cPlease use /party help for more info."));
                    return;
                }
            }
        }
        if (args.length > 1) {
            if (args[0].contains("chat")) {
                String msg = "";
                for (int i = 1; i < args.length; i++) {
                    msg = msg + args[i] + " ";
                }
                PartyManager.chat(user.getPlayer(), msg);
                return;
            } else {
                for (String s : Party.party.getFile().getStringList("Party.Messages.Help")) {
                    user.sendMessage(s);
                }
                return;
            }
        }
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        sender.sendMessage(Utils.format(Party.party.getFile().getString("Party.Messages.ConsoleError",
                "&cThe console cannot use this Command!")));
    }
}