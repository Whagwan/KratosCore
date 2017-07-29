package me.kratoscore.net.api.placeholder;

import java.util.List;

import me.kratoscore.net.bungee.user.BungeeUser;
import com.google.common.collect.Lists;

public class PlaceHolderAPI {

    static PlaceHolderAPI instance = new PlaceHolderAPI();
    public List<PlaceHolder> placeholders = Lists.newArrayList();

    public static PlaceHolderAPI getInstance(){
        return instance;
    }

    public String formatMessage(BungeeUser user, String message){
        try {
            for(PlaceHolder placeholder : this.placeholders){
                message = placeholder.format(user, message);
            }
            return message;
        } catch(Exception e){
            return "";
        }
    }

    public void addPlaceHolder(String placeholder, PlaceHolderReplaceEventHandler replace){
        this.placeholders.add(new PlaceHolder(placeholder, replace));
    }

    public PlaceHolder getPlaceHolder(String placeholder){
        for(PlaceHolder ph : this.placeholders){
            if(ph.getPlaceHolder().toLowerCase().equals(placeholder.toLowerCase())){
                return ph;
            }
        }
        return null;
    }
}