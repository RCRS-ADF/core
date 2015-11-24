package adf;

import adf.launcher.AgentLauncher;

public class Main {
    public static void main(String... args) {
        try {
            AgentLauncher connector = new AgentLauncher(args);
            connector.start();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("[ERROR ] Loader not found.");
        }
    }
}