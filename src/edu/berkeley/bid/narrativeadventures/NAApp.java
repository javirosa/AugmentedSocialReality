package edu.berkeley.bid.narrativeadventures;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;

import android.app.Application;
import edu.berkeley.bid.narrativeadventures.io.NarrativeStorage;
import edu.berkeley.bid.narrativeadventures.model.Narrative;
import edu.berkeley.bid.narrativeadventures.model.Problem;

public class NAApp extends Application 
{

    ArrayList<Problem> runningProblems;
    ArrayList<Narrative> possibleNarratives;

    public NAApp() 
    {
        super();
        runningProblems = new ArrayList<Problem>();
        possibleNarratives = new ArrayList<Narrative>();
    }

    /**
     * Stores all current narratives to disk
     */
    boolean saveState(File possibleNarsDir, File runningProbsDir) 
    {
        boolean corruption = false;
        for (Narrative nar : possibleNarratives) {
            if (!NarrativeStorage.saveNarrative(nar, possibleNarsDir))
                corruption = true;
        }
        for (Problem problem : runningProblems) {
            if (!NarrativeStorage.saveProblem(problem, runningProbsDir))
                corruption = true;
        }
        return !corruption;
    }

    /**
     * Overwrites current set of stored narratives from file. Intended to be
     * called at application initialization.
     */
    boolean loadState(File possibleNarsDir, File runningProbsDir) 
    {
        boolean corruption = false;
        possibleNarratives.clear();
        runningProblems.clear();
        for (File f : possibleNarsDir.listFiles()) {
            Narrative nar = NarrativeStorage.loadNarrative(f);
            if (nar == null)
                corruption = true;
            else 
                possibleNarratives.add(nar);
        }
        for (File f : runningProbsDir.listFiles()) {
            Problem prob = NarrativeStorage.loadProblem(f);
            if (prob == null) 
                corruption = true;
            else
                runningProblems.add(prob);
        }
        return !corruption;
    }
}
