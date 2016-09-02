package adf.launcher;

import adf.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static adf.Main.VERSION_CODE;

public class ConsoleOutput
{
    private static final int titleLength = 6;

    public static enum State {INFO, WARN, ERROR, NOTICE, START, FINISH, END};

    public static void out(State state, String out)
    {
        out(state.name(), out);
    }

    public static void out(String title, String out)
    {
        System.out.print('[');
        System.out.print(title);
        for (int i = title.length(); i < titleLength; i++)
        { System.out.print(' '); }
        System.out.print("] ");
        System.out.println(out);
    }

    public static void info(String out)
    { out(State.INFO, out); }

    public static void warn(String out)
    { out(State.WARN, out); }

    public static void error(String out)
    { out(State.ERROR, out); }

    public static void notice(String out)
    { out(State.NOTICE, out); }

    public static void start(String out)
    { out(State.START, out); }

    public static void finish(String out)
    { out(State.FINISH, out); }

    public static void end(String out)
    { out(State.END, out); }

    public static void version()
    { System.out.println("[ RCRS ADF Version " + VERSION_CODE + " (build " + getTimestamp() + ") ]\n"); }

    private static String getTimestamp()
    {
        String buildTimestamp = "null";

        Class clazz = Main.class;
        String className = clazz.getSimpleName() + ".class";
        String classPath = clazz.getResource(className).toString();
        if (!classPath.startsWith("jar"))
        { return buildTimestamp; }

        String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + File.separator + "META-INF" + File.separator + "MANIFEST.MF";
        try
        {
            Manifest manifest = new Manifest(new URL(manifestPath).openStream());
            Attributes attributes = manifest.getMainAttributes();
            buildTimestamp = attributes.getValue("Build-Timestamp");
        }
        catch (IOException e) { }

        return buildTimestamp;
    }
}
