package me.kratoscore.net.api.placeholder;

import me.kratoscore.net.bungee.user.BungeeUser;

public class PlaceHolderReplaceEvent {

    BungeeUser user;
    PlaceHolder placeholder;

    public PlaceHolderReplaceEvent(BungeeUser user, PlaceHolder placeholder) {
        this.user = user;
        this.placeholder = placeholder;
    }

    public BungeeUser getUser() {
        return user;
    }

    public PlaceHolder getPlaceHolder() {
        return placeholder;
    }
}
