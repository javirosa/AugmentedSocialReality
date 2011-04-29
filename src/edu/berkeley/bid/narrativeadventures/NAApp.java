package edu.berkeley.bid.narrativeadventures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.CharBuffer;
import java.util.ArrayList;

import android.app.Application;
import edu.berkeley.bid.narrativeadventures.io.NarrativeStorage;
import edu.berkeley.bid.narrativeadventures.model.Narrative;

public class NAApp extends Application {
    
    ArrayList<Narrative> runningNarratives;
    ArrayList<Narrative> possibleNarratives;
    
    File runningNarsDir;
    File possibleNarsDir;
    
    public NAApp()
    {
        super();
        runningNarratives = new ArrayList<Narrative>();
        possibleNarratives = new ArrayList<Narrative>();
        runningNarsDir = new File(getFilesDir(),"runningNaratives");
        possibleNarsDir = new File(getFilesDir(),"possibleNaratives");
        runningNarsDir.mkdirs();
        possibleNarsDir.mkdirs();
    }
    
    /**
     * 
     * @param nar the narrative to be saved.
     * @param possible true if the narrative meant to be saved as a prototype.
     * @throws IOException 
     */
    void saveNarrative(Narrative nar, boolean possible) throws IOException
    {
        File narFile = possibleNarsDir;
        if (!possible) 
        {
            narFile = runningNarsDir;
        }
        
        narFile = new File(narFile,nar.getId());
        FileWriter fw = new FileWriter(narFile);
        fw.write(NarrativeStorage.toJson(nar));
        fw.close();
    }
    
    /**
     * 
     * @throws IOException
     */
    void saveNarratives() throws IOException
    {
        for (Narrative nar: possibleNarratives) {
            saveNarrative(nar, true);
        }
        for (Narrative nar: runningNarratives) {
            saveNarrative(nar, false);
        }
    }
    
    /**
     * Overwrites current set of stored narratives from file.
     * Intended to be called at application initialization. 
     * @throws IOException 
     */
    void loadNarratives() throws IOException
    {
        possibleNarratives = new ArrayList<Narrative>();
        runningNarratives = new ArrayList<Narrative>();
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
