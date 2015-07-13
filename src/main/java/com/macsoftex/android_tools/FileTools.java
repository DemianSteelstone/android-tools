package com.macsoftex.android_tools;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by alex-v on 23.09.14.
 */
public class FileTools
{
    public static String getStringFromInputStream(InputStream is)
    {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;

        try
        {
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null)
            {
                if (sb.toString().length() != 0)
                    sb.append("\n");

                sb.append(line);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public static byte[] getBytesFromInputStream(InputStream inputStream)
    {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        try
        {
            byte[] buffer = new byte[ 1024 ];

            int len = 0;
            while ((len = inputStream.read(buffer)) != -1)
            {
                byteBuffer.write(buffer, 0, len);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return byteBuffer.toByteArray();
    }

    public static String loadTextFileFromAsset(String path, Context ctx)
    {
        String str = null;

        try
        {
            AssetManager am = ctx.getAssets();
            InputStream is = am.open(path);
            str = getStringFromInputStream( is );
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return str;
    }

    public static String loadTextFileFromRaw(int id, Context ctx)
    {
        String str = null;

        try
        {
            InputStream is = ctx.getResources().openRawResource(id);
            str = getStringFromInputStream(is);
        }
        catch (Resources.NotFoundException e)
        {
            e.printStackTrace();
        }

        return str;
    }

    public static void saveTextFile(File file, String text)
    {
        if (file==null || text==null)
            return;

        FileOutputStream f = null;

        try
        {
            f = new FileOutputStream( file );
            f.write( text.getBytes() );
            f.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (f != null)
            {
                try
                {
                    f.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String loadTextFile( File file )
    {
        String text = null;

        try
        {
            FileInputStream f = new FileInputStream( file );
            text = getStringFromInputStream( f );
            f.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return text;
    }

    public static void saveTextFileToInternalStorage(final String fileName, final String text, Context ctx)
    {
        File file = ctx.getFileStreamPath( fileName );
        saveTextFile(file, text);
    }

    public static String loadTextFileFromInternalStorage(String fileName, Context ctx)
    {
        File file = ctx.getFileStreamPath( fileName );
        return loadTextFile( file );
    }

    public static void saveObjectToInternalStorage(String fileName, Object object, Context ctx)
    {
        try
        {
            FileOutputStream f = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);

            ObjectOutputStream obj = new ObjectOutputStream( f );
            obj.writeObject( object );
            obj.flush();
            obj.close();

            f.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Object loadObjectFromInternalStorage(String fileName, Context ctx)
    {
        Object returnObject = null;

        try
        {
            FileInputStream f = ctx.openFileInput(fileName);

            ObjectInputStream obj = new ObjectInputStream(f);
            returnObject = obj.readObject();
            obj.close();

            f.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return returnObject;
    }

    public static String getValidFileName(String fileName)
    {
        return fileName.replaceAll("\\W+", "_").trim();
    }

    public static String getFileNameWithoutExtension(String fileName)
    {
        final int pos = fileName.lastIndexOf('.');

        if (pos > 0)
            return fileName.substring(0, pos);

        return fileName;
    }

    public static boolean copyFile(File src, File dst)
    {
        return copyFile(src, dst, true);
    }

    public static boolean copyFile(File src, File dst, boolean overwrite)
    {
        if ( !src.isFile() )
            return false;

        if ( dst.exists() )
        {
            if ( overwrite )
                dst.delete();

            if ( dst.exists() )
                return false;
        }

        try
        {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);

            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return dst.exists();
    }

    public static boolean deleteFile(File file)
    {
        if ( file.isDirectory() )
        {
            String[] children = file.list();

            for (String child : children)
                deleteFile( new File(file, child) );
        }
        else
        {
            file.delete();
        }

        return file.exists();
    }
}
