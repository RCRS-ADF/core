package adf;

import adf.launcher.AgentLauncher;
import adf.launcher.LaunchSupporter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class Main
{
    public static final String VERSION_CODE = "2.1.0";

    public static void main(String... args)
    {
        System.out.println("[ RCRS ADF Version " + VERSION_CODE + " (build " + getTimestamp() + ") ]\n");
        List<String> launcherArguments = new ArrayList<>();
        launcherArguments.addAll(Arrays.asList(args));
        LaunchSupporter.delegate(launcherArguments);

        try {
            AgentLauncher connector = new AgentLauncher((String[]) launcherArguments.toArray(new String[0]));
            connector.start();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("[ERROR ] Loader is not found.");
            e.printStackTrace();
        }
    }

    private static String getTimestamp()
    {
        String buildTimestamp = "null";

        Class clazz = Main.class;
        String className = clazz.getSimpleName() + ".class";
        String classPath = clazz.getResource(className).toString();
        if (!classPath.startsWith("jar"))
        { return buildTimestamp; }

        String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";
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
