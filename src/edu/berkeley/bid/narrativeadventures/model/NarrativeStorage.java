package edu.berkeley.bid.narrativeadventures.model;

import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NarrativeStorage 
{
    
    static GsonBuilder gsonCfg;
    static URL server;
    static {
        gsonCfg = new GsonBuilder();
        gsonCfg .serializeNulls()
                .setPrettyPrinting();
    }
    
    Narrative fromJson(String json)
    {
        Gson gson = gsonCfg.create();
        return gson.fromJson(json, Narrative.class);
    }
    
    String toJson(Narrative nar) 
    {    
        Gson gson = gsonCfg.create();
        return gson.toJson(nar);
    }
}
