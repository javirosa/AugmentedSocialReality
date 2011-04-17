package edu.berkeley.bid.narrativeadventures.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NarrativeToFromGSON {
    
    static GsonBuilder gsonCfg;
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
