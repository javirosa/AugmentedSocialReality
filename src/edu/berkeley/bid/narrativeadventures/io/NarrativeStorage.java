package edu.berkeley.bid.narrativeadventures.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.CharBuffer;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.berkeley.bid.narrativeadventures.model.Ided;
import edu.berkeley.bid.narrativeadventures.model.Narrative;
import edu.berkeley.bid.narrativeadventures.model.Problem;

public class NarrativeStorage 
{
    static GsonBuilder gsonCfg;
    static Gson gson;
    static URL server;
    static {
        gsonCfg = new GsonBuilder();
        gsonCfg .serializeNulls()
                .setPrettyPrinting();
        gson = gsonCfg.create();
    }
    
    public static boolean saveObject(File location, Ided object)
    {
        try {
            location = new File(location,object.getId());
            if ( ! location.createNewFile() ) {
                Log.d("GSON","file exists: " + location.toString() );
            }
            FileWriter fw = new FileWriter(location);
            fw.write(gson.toJson(object));
            fw.close();
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T loadObject(File f, T object)
    {
        FileReader fr;
        CharBuffer cb;
        try {
            cb = CharBuffer.allocate((int)f.length());
            fr = new FileReader(f);
            fr.read(cb);
            fr.close();
            return (T) gson.fromJson(new String(cb.array()),object.getClass());
        } catch (FileNotFoundException fne) {
            return null;
        } catch (IOException ioe) {
            return null;
        }
    }
    /**
     * @param nar the narrative to be saved.
     */
    public static boolean saveNarrative(Narrative nar, File narLocation) 
    {
        narLocation = new File(narLocation,nar.getId());
        try {
            if ( ! narLocation.createNewFile() ) {
                Log.d("GSON","file exists: " + narLocation.toString() );
            }
            FileWriter fw = new FileWriter(narLocation);
            fw.write(gson.toJson(nar));
            fw.close();
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }
    /**
     * @param f problem file to load
     * @return null if read fails
     */
    public static Narrative loadNarrative(File f) 
    {
        FileReader fr;
        CharBuffer cb;
        try {
            cb = CharBuffer.allocate((int)f.length());
            fr = new FileReader(f);
            fr.read(cb);
            fr.close();
        } catch (FileNotFoundException fne) {
            return null;
        } catch (IOException ioe) {
            return null;
        }
        return gson.fromJson(new String(cb.array()),Narrative.class);
    }
    
    /**
     * 
     * @param problem the problem to be saved.
     */
    public static boolean saveProblem(Problem problem, File probLocation) 
    {
        try {
            probLocation= new File(probLocation,problem.getId());
            if ( ! probLocation.createNewFile() ) {
                Log.d("GSON","file exists: " + probLocation.toString() );
            }
            FileWriter fw = new FileWriter(probLocation);
            fw.write(gson.toJson(problem));
            fw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    /**
     * @param f problem file to load
     * @return null if read fails
     */
    public static Problem loadProblem(File f) 
    {
        FileReader fr;
        CharBuffer cb;
        try {
            cb = CharBuffer.allocate((int)f.length());
            fr = new FileReader(f);
            fr.read(cb);
            fr.close();
        } catch (FileNotFoundException fne) {
            return null;
        } catch (IOException ioe) {
            return null;
        }
        return gson.fromJson(new String(cb.array()), Problem.class);
    }
}
