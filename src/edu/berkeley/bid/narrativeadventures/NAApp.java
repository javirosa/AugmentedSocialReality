package edu.berkeley.bid.narrativeadventures;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;

import android.app.Application;
import android.util.Log;
import edu.berkeley.bid.narrativeadventures.io.NarrativeStorage;
import edu.berkeley.bid.narrativeadventures.model.Narrative;

public class NAApp extends Application {
    
    ArrayList<Narrative> runningNarratives;
    ArrayList<Narrative> possibleNarratives;
    
    public NAApp()
    {
        super();
        runningNarratives = new ArrayList<Narrative>();
        possibleNarratives = new ArrayList<Narrative>();
    }
    
    /**
     * 
     * @param nar the narrative to be saved.
     * @throws IOException 
     */
    void saveNarrative(Narrative nar, File narLocation) throws IOException
    {
        narLocation= new File(narLocation,nar.getId());
        if ( ! narLocation.createNewFile() ) {
            Log.d("GSON","file exists: " + narLocation.toString() );
        }
        FileWriter fw = new FileWriter(narLocation);
        fw.write(NarrativeStorage.toJson(nar));
        fw.close();
    }
    
    /**
     * Stores all current narratives to disk
     * @throws IOException
     */
    void saveNarratives(File possibleNarsDir, File runningNarsDir) throws IOException
    {
        for (Narrative nar: possibleNarratives) {
            saveNarrative(nar, possibleNarsDir);
        }
        for (Narrative nar: runningNarratives) {
            saveNarrative(nar, runningNarsDir);
        }
    }
    
    /**
     * Overwrites current set of stored narratives from file.
     * Intended to be called at application initialization. 
     * @throws IOException 
     */
    void loadNarratives(File possibleNarsDir, File runningNarsDir) throws IOException
    {
        possibleNarratives.clear();
        runningNarratives.clear();
        
        for (File f : possibleNarsDir.listFiles()) {
            FileReader fr = new FileReader(f);
            CharBuffer cb = CharBuffer.allocate((int)f.length());
            fr.read(cb);
            possibleNarratives.add( NarrativeStorage.fromJson(cb.toString()) );
        }
        for (File f : runningNarsDir.listFiles()) {
            FileReader fr = new FileReader(f);
            CharBuffer cb = CharBuffer.allocate((int)f.length());
            fr.read(cb);
            runningNarratives.add( NarrativeStorage.fromJson(cb.toString()) );
        }
    }
}
