package edu.berkeley.bid.narrativeadventures.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NarrativeToFromGSON {
    
    Narrative fromJson(String json)
    {
        return null;
    }
    
    String toJson(Narrative nar) 
    {
        GsonBuilder gsonCfg = new GsonBuilder();
        gsonCfg.serializeNulls();
        
        Gson gson = gsonCfg.create();
        
        return gson.toJson(nar);
    }
}
