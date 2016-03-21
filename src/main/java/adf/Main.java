package adf;

import adf.launcher.AgentLauncher;
import adf.launcher.LaunchSupporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
    public static final String VERSION_CODE = "2.0.5";
    public static void main(String... args)
    {
        System.out.println("[ RCRS ADK Framework Version " + VERSION_CODE + " ]\n");
        List<String> launcherArguments = new ArrayList<>();
        launcherArguments.addAll(Arrays.asList(args));
        LaunchSupporter.delegate(launcherArguments);

        try {
            AgentLauncher connector = new AgentLauncher((String[]) launcherArguments.toArray(new String[0]));
            connector.start();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("[ERROR ] Loader not found.");
            e.printStackTrace();
        }
    }
}
