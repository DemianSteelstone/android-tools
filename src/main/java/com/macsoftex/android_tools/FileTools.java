package com.macsoftex.android_tools;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by alex-v on 23.09.14.
 */
public class FileTools {
    public static String getStringFromInputStream(InputStream inputStream) {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = bufferedReader.readLine()) != null) {
                if (stringBuilder.toString().length() != 0)
                    stringBuilder.append("\n");

                stringBuilder.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return stringBuilder.toString();
    }

    public static byte[] getBytesFromInputStream(InputStream inputStream) {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        try {
            byte[] buffer = new byte[1024];
            int len = 0;

            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteBuffer.toByteArray();
    }

    public static String loadTextFileFromAsset(Context context, String path) {
        String str = null;

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(path);
            str = getStringFromInputStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public static String loadTextFileFromRaw(Context context, int id) {
        String str = null;

        try {
            InputStream inputStream = context.getResources().openRawResource(id);
            str = getStringFromInputStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public static void saveTextFile(File file, String text) {
        if (file==null || text==null)
            return;

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String loadTextFile(File file) {
        String text = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            text = getStringFromInputStream(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return text;
    }

    public static String loadTextFileFromInternalStorage(Context context, final String fileName) {
        File file = context.getFileStreamPath(fileName);
        return loadTextFile(file);
    }

    public static void saveTextFileToInternalStorage(Context context, final String fileName, final String text) {
        File file = context.getFileStreamPath(fileName);
        saveTextFile(file, text);
    }

    public static Object loadObjectFromInternalStorage(Context context, String fileName) {
        Object object = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            object = objectInputStream.readObject();
            objectInputStream.close();

            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    public static boolean saveObjectToInternalStorage(Context context, String fileName, Object object) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();

            fileOutputStream.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getValidFileName(String fileName) {
        return fileName.replaceAll("\\W+", "_").trim();
    }

    public static String getFileNameWithoutExtension(String fileName) {
        final int pos = fileName.lastIndexOf('.');

        if (pos > 0)
            return fileName.substring(0, pos);

        return fileName;
    }

    public static String getExtensionFromFileName(String fileName) {
        final int pos = fileName.lastIndexOf('.');

        if (pos > 0)
            return fileName.substring(pos + 1);

        return null;
    }

    public static boolean copyFile(File fileFrom, File fileTo, boolean overwrite) {
        if (!fileFrom.exists() || !fileFrom.isFile())
            return false;

        if (fileTo.exists()) {
            if (overwrite)
                fileTo.delete();

            if (fileTo.exists())
                return false;
        }

        try {
            FileInputStream inputStream = new FileInputStream(fileFrom);
            FileOutputStream outputStream = new FileOutputStream(fileTo);

            byte[] buf = new byte[1024];
            int len;

            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }

            inputStream.close();

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return fileTo.exists();
    }

    public static boolean deleteFile(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();

            for (String child : children)
                deleteFile(new File(file, child));
        }

        file.delete();

        return file.exists();
    }
}
