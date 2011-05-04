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
    public static ArrayList<byte[]> loadIcons() {
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
                Bitmap bmp = BitmapFactory.decodeStream(is);
                if (bmp != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(CompressFormat.PNG, 100, baos);
                    icons.add(baos.toByteArray());
                }
            } catch (IOException e) {
            }
        }
        return icons;
    }
}
