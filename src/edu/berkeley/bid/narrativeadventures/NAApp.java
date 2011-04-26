package edu.berkeley.bid.narrativeadventures;

import java.util.ArrayList;

import android.app.Application;
import edu.berkeley.bid.narrativeadventures.model.Narrative;

public class NAApp extends Application {
    
    ArrayList<Narrative> runningNarratives;
    
    public NAApp()
    {
        super();
        runningNarratives = new ArrayList<Narrative>();
    }
}
