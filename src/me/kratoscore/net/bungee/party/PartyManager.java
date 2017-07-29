package me.kratoscore.net.bungee.party;

import java.util.ArrayList;
import java.util.HashMap;

import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyManager {

    public static ArrayList<String> partyowner = new ArrayList<String>();
    public static ArrayList<String> partyadmin = new ArrayList<String>();
    public static HashMap<String, String> inparty = new HashMap<String, String>();
    public static HashMap<String, Long> invitetime = new HashMap<String, Long>();
    public static HashMap<String, String> invite = new HashMap<String, String>();

    public static void createParty(ProxiedPlayer user) {
        if (inparty.containsKey(user.getName())) {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.AlreadyInParty", "&cYou are already in a party!").replace("&", "§")));
            return;
        }
        if (partyowner.contains(user.getName())) {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.AlreadyInParty", "&cYou are already in a party!").replace("&", "§")));
            return;
        }
        partyowner.add(user.getName());

        user.sendMessage(Utils.format(Party.party.getFile()
                .getString("Party.Messages.PartyCreated", "&aYou have created a new party!").replace("&", "§")));
    }

    public static void addAdmin(ProxiedPlayer user, String t) {
        if ((partyowner.contains(user.getName()))) {
            if (t == null) {
                user.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.OfflinePlayer", "&cThat player is not online!").replace("&", "§")));
                return;
            }
            if (inparty.containsKey(t)) {
                if (partyadmin.contains(t)) {
                    partyadmin.remove(t);
                    user.sendMessage(Utils.format(Party.party.getFile()
                            .getString("Party.Messages.AdminRemove", "&c%player% is not a party admin anymore.")
                            .replace("%player%", t).replace("&", "§")));
                    return;
                } else {
                    user.sendMessage(Utils.format(Party.party.getFile()
                            .getString("Party.Messages.AdminAdd", "&a%player% is now a party admin!")
                            .replace("%player%", t).replace("&", "§")));
                    partyadmin.add(t);
                    return;
                }
            } else {
                user.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.NotInYourParty", "&cThat Player is not in your party!")
                        .replace("&", "§")));
                return;
            }
        } else {
            user.sendMessage(
                    Utils.format(Party.party.getFile()
                            .getString("Party.Messages.NotPartyOwner",
                                    "&cYou are not the party owner, so you cannot use this command!")
                            .replace("&", "§")));
        }
    }

    public static void partyList(ProxiedPlayer user) {
        if (partyowner.contains(user.getName())) {

            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.PartyList.Header", "&a========== &eParty Members &a==========")
                    .replace("&", "§")));
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.PartyList.Format",
                            "&a%player% &eis %onORoff% &8[&a%partyrank%&8] [&e%server%&8]")
                    .replace("&", "§").replace("%player%", user.getName())
                    .replace("%onORoff%", (user == null ? "offline" : "online")).replace("%partyrank%", "Party Owner")
                    .replace("%server%", user.getServer().getInfo().getName())));

            for (ProxiedPlayer y : ProxyServer.getInstance().getPlayers()) {
                if ((inparty.containsKey(y.getName())) && (inparty.get(y.getName()) == user.getName())) {

                    user.sendMessage(
                            Utils.format(Party.party.getFile()
                                    .getString("Party.Messages.PartyList.Format",
                                            "&a%player% &eis %onORoff% &8[&a%partyrank%]")
                                    .replace("&", "§").replace("%player%", y.getName())
                                    .replace("%onORoff%", (y == null ? "offline" : "online"))
                                    .replace("%partyrank%", (partyadmin.contains(y.getName()) ? "Admin" : "Member"))
                                    .replace("%server%", y.getServer().getInfo().getName())));
                }
            }

            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.PartyList.Footer", "&a========== &eParty Members &a==========")
                    .replace("&", "§")));

        }
        if (inparty.containsKey(user.getName())) {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.PartyList.Header", "&a========== &eParty Members &a==========")
                    .replace("&", "§")));

            String partyowner = inparty.get(user.getName());
            if (ProxyServer.getInstance().getPlayer(partyowner) != null) {
                ProxiedPlayer owner = ProxyServer.getInstance().getPlayer(partyowner);
                String servername = owner.getServer().getInfo().getName();

                user.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.PartyList.Format",
                                "&a%player% &eis %onORoff% &8[&a%partyrank%&8] [&e%server%&8]")
                        .replace("&", "§").replace("%player%", partyowner).replace("%onORoff%", "online")
                        .replace("%partyrank%", "Party Owner").replace("%server%", servername)));

            }

            for (ProxiedPlayer y : ProxyServer.getInstance().getPlayers()) {
                if ((inparty.containsKey(y.getName())) && (inparty.get(y.getName()) == inparty.get(user.getName()))) {
                    user.sendMessage(
                            Utils.format(Party.party.getFile()
                                    .getString("Party.Messages.PartyList.Format",
                                            "&a%player% &eis %onORoff% &8[&a%partyrank%]")
                                    .replace("&", "§").replace("%player%", y.getName())
                                    .replace("%onORoff%", (y == null ? "offline" : "online"))
                                    .replace("%partyrank%", (partyadmin.contains(y.getName()) ? "Admin" : "Member"))
                                    .replace("%server%", y.getServer().getInfo().getName())));
                }
            }
        }
        if ((!inparty.containsKey(user.getName())) && (!partyowner.contains(user.getName()))) {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.NotInParty", "&cYou are not in a party!").replace("&", "§")));
        }
    }

    public static void leave(ProxiedPlayer user) {
        if (inparty.containsKey(user.getName())) {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.LeftParty", "&cYou have left your party!").replace("&", "§")));
            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(inparty.get(user.getName()));
            if (p != null) {
                p.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.LeftPartyMessage", "&c%player% has left the party!")
                        .replace("&", "§").replace("%player%", user.getName())));
            }
            for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
                if (inparty.get(x.getName()) == inparty.get(user.getName())) {
                    x.sendMessage(Utils.format(Party.party.getFile()
                            .getString("Party.Messages.LeftPartyMessage", "&c%player% has left the party!")
                            .replace("&", "§").replace("%player%", user.getName())));
                }
            }
            inparty.remove(user.getName());
            if (partyadmin.contains(user.getName())) {
                partyadmin.remove(user.getName());
            }
            return;
        }
        if (partyowner.contains(user.getName())) {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.PartyRemoved", "&cYou have deleted your party!").replace("&", "§")));
            partyowner.remove(user.getName());
            for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
                if ((inparty.containsKey(x.getName())) && (inparty.get(x.getName()) == user.getName())) {
                    x.sendMessage(
                            Utils.format(Party.party.getFile()
                                    .getString("Party.Messages.PartyRemovedMessage",
                                            "&c%player% has deleted the party!")
                                    .replace("&", "§").replace("%player%", user.getName())));
                    inparty.remove(x.getName());
                }
            }
            return;
        }
        if ((!inparty.containsKey(user.getName())) && (!partyowner.contains(user.getName()))) {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.NotInParty", "&cYou are not in a party!").replace("&", "§")));
        }
    }

    public static void chat(ProxiedPlayer user, String text) {
        if (partyowner.contains(user.getName())) {
            user.sendMessage(Utils.format(
                    Party.party.getFile().getString("Party.Messages.PartyChatFormat", "&f%player% &6:: &f%message%")
                            .replace("&", "§").replace("%player%", user.getName()).replace("%message%", text)));
            for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
                if ((inparty.containsKey(x.getName())) && (inparty.get(x.getName()) == user.getName())) {
                    x.sendMessage(Utils.format(Party.party.getFile()
                            .getString("Party.Messages.PartyChatFormat", "&f%player% &6:: &f%message%")
                            .replace("&", "§").replace("%player%", user.getName()).replace("%message%", text)));
                }
            }
        }
        if (inparty.containsKey(user.getName())) {
            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(inparty.get(user.getName()));
            if (p != null) {
                p.sendMessage(Utils.format(
                        Party.party.getFile().getString("Party.Messages.PartyChatFormat", "&f%player% &6:: &f%message%")
                                .replace("&", "§").replace("%player%", user.getName()).replace("%message%", text)));
            }
            for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
                if ((inparty.containsKey(x.getName())) && (inparty.get(x.getName()) == inparty.get(user.getName()))) {
                    x.sendMessage(Utils.format(Party.party.getFile()
                            .getString("Party.Messages.PartyChatFormat", "&f%player% &6:: &f%message%")
                            .replace("&", "§").replace("%player%", user.getName()).replace("%message%", text)));
                }
            }
        }
        if ((!inparty.containsKey(user.getName())) && (!partyowner.contains(user.getName()))
                && (!partyadmin.contains(user.getName()))) {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.NotInParty", "&cYou are not in a party!").replace("&", "§")));
        }
    }

    public static void invite(ProxiedPlayer user, ProxiedPlayer z) {
        long time = System.currentTimeMillis();
        if (partyowner.contains(user.getName()) || partyadmin.contains(user.getName())) {
            Integer iparty = Integer.valueOf(0);
            for (ProxiedPlayer i : ProxyServer.getInstance().getPlayers()) {
                if ((inparty.containsKey(i.getName())) && (inparty.get(i.getName()) == user.getName())) {
                    iparty = Integer.valueOf(iparty.intValue() + 1);
                }
            }
            if (Party.party.getFile().getInt("Party.Party-Limit", 0) != 0
                    && iparty.intValue() > Party.party.getFile().getInt("Party.Party-Limit", 0)) {
                user.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.PartyFull", "&cYour party is full!").replace("&", "§")));
                return;
            }
            if (inparty.containsKey(z.getName())) {
                user.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.PlayerAlreadyInParty", "&cThat player is already in your party!")
                        .replace("&", "§")));
                return;
            }
            if (partyowner.contains(z.getName())) {
                user.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.PlayerHasOwnParty", "&cThat player already has his own party!")
                        .replace("&", "§")));
                return;
            }
            if ((!inparty.containsKey(z.getName()))
                    && (!partyowner.contains(z.getName()) && (!partyadmin.contains(z.getName())))) {
                invite.put(z.getName(), user.getName());
                invitetime.put(z.getName(), Long.valueOf(time));
                user.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.InvitedPlayer", "&aYou have invited &e%player% &ato your party!")
                        .replace("%player%", z.getName())));

                for (String s : Party.party.getFile().getStringList("Party.Messages.InviteReceive")) {
                    z.sendMessage(Utils.format(s.replace("&", "§").replace("%player%", user.getName())));
                }
            }
        } else if (inparty.containsKey(user.getName())) {

            user.sendMessage(
                    Utils.format(Party.party.getFile()
                            .getString("Party.Messages.NotPartyOwner",
                                    "&cYou are not the party owner, so you cannot use this command!")
                            .replace("&", "§")));

        } else if ((!inparty.containsKey(user.getName())) && (!partyowner.contains(user.getName()))) {

            createParty(user);
            invite(user, z);

        }
    }

    public static void accept(ProxiedPlayer user) {
        if ((partyowner.contains(user.getName()) | inparty.containsKey(user.getName())
                | partyadmin.contains(user.getName()))) {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.AlreadyInParty", "&cYou are already in a party!").replace("&", "§")));
        } else if (invite.containsKey(user.getName())) {
            Long time = Long.valueOf(System.currentTimeMillis());
            Long diff = Long
                    .valueOf(time.longValue() / 1000L - ((Long) invitetime.get(user.getName())).longValue() / 1000L);
            if (diff.longValue() > 60L) {
                user.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.AcceptedToLate", "&cThis invitation is already expired!")
                        .replace("&", "§")));
            } else {
                ProxiedPlayer p = ProxyServer.getInstance().getPlayer(invite.get(user.getName()));
                p.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.JoinedPartyMessage", "&e%player% &ahas joined the party!")
                        .replace("&", "§").replace("%player%", user.getName())));
                for (ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()) {
                    if (inparty.containsKey(pl.getName()) && (inparty.get(pl.getName()) == p.getName())) {
                        pl.sendMessage(
                                Utils.format(Party.party.getFile()
                                        .getString("Party.Messages.JoinedPartyMessage",
                                                "&e%player% &ahas joined the party!")
                                        .replace("&", "§").replace("%player%", user.getName())));
                    }
                }
                invite.remove(user.getName());
                invitetime.remove(user.getName());
                inparty.put(user.getName(), p.getName());
                user.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.JoinedParty", "&aYou have joined the party!").replace("&", "§")));
            }
        } else {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.NoInvites", "&cYou do not have any invites.").replace("&", "§")));
        }
    }

    public static void kick(ProxiedPlayer user, ProxiedPlayer kicked) {
        if (partyowner.contains(user.getName()) || partyadmin.contains(user.getName())) {
            if ((inparty.containsKey(kicked.getName())) && (inparty.get(kicked.getName()) == user.getName())) {
                inparty.remove(kicked.getName());
                kicked.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.Kicked", "&cYou have been kicked out of the party!")
                        .replace("&", "§")));
                user.sendMessage(
                        Utils.format(Party.party.getFile()
                                .getString("Party.Messages.KickedPlayer",
                                        "&cYou have kicked %player% out of the party!")
                                .replace("&", "§").replace("%player%", kicked.getName())));
                for (ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()) {
                    if ((inparty.containsKey(pl.getName())) && (inparty.get(pl.getName()) == user.getName())) {
                        pl.sendMessage(
                                Utils.format(Party.party.getFile()
                                        .getString("Party.Messages.KickedMessage",
                                                "&4%player% &cwas kicked out of the party!")
                                        .replace("&", "§").replace("%player%", kicked.getName())));
                    }
                }
            } else {
                user.sendMessage(Utils.format(Party.party.getFile()
                        .getString("Party.Messages.NotInYourParty", "&cThat Player is not in your party!")
                        .replace("&", "§")));
            }
        } else {
            user.sendMessage(
                    Utils.format(Party.party.getFile()
                            .getString("Party.Messages.NotPartyOwner",
                                    "&cYou are not the party owner, so you cannot use this command!")
                            .replace("&", "§")));
        }
    }

    public static void warpParty(ProxiedPlayer user) {
        if (partyowner.contains(user.getName())) {
            user.sendMessage(Utils.format(Party.party.getFile()
                    .getString("Party.Messages.ServerWarp", "&aThe party owner has sent you to his server!")
                    .replace("&", "§")));
            for (ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()) {
                if ((inparty.containsKey(pl.getName())) && (inparty.get(pl.getName()) == user.getName())) {
                    pl.sendMessage(Utils.format(Party.party.getFile()
                            .getString("Party.Messages.ServerWarp", "&aThe party owner has sent you to his server!")
                            .replace("&", "§")));
                    pl.connect(user.getServer().getInfo());
                }
            }
        } else {
            user.sendMessage(
                    Utils.format(Party.party.getFile()
                            .getString("Party.Messages.NotPartyOwner",
                                    "&cYou are not the party owner, so you cannot use this command!")
                            .replace("&", "§")));
        }
    }
}
