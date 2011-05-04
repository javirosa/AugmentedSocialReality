package edu.berkeley.bid.narrativeadventures.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

/**
 * Returns png icons from the icons folder in assets
 * @author javirosa
 *
 */

public class IconStorage {

    public static final String iconDir = "icons";

    public static ArrayList<byte[]> loadIcons() 
    {
        AssetManager am = Resources.getSystem().getAssets();
        ArrayList<byte []> icons = new ArrayList<byte[]>();
        String[] iconFileNames = new String[]{};
        
        try {
            iconFileNames = am.list(iconDir);
        } catch (IOException ioe) {
        }
        for (String s: iconFileNames) {
            try {
                InputStream is = am.open(iconDir+File.separatorChar+s);
                byte[] ba = toBytes(is);
                if (ba.length > 0) {
                    icons.add(ba);
                }
            } catch (IOException e) {
            }
        }
        return icons;
    }
    
    public static Bitmap toBitmap(byte[] png) 
    {
        Bitmap bmp = BitmapFactory.decodeByteArray(png, 0, 0);
        return bmp;
    }
    
    public static byte[] toBytes(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    
    public static byte[] toBytes(InputStream is)
    {
        Bitmap bmp = BitmapFactory.decodeStream(is);
        if (bmp != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }
        return new byte[]{};
    }
}
