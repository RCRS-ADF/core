package adf.launcher;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaunchSupporter
{
    private static final String OPTION_COMPILE = "-compile";
    private static final String OPTION_AUTOCLASSPATH = "-autocp";
    private static final String OPTION_AUTOLOADERCLASS = "-autolc";
    private static final String DIRECTORY_LIBRARY = "library";
    private static final String DIRECTORY_SRC = "src";
    private static final String DIRECTORY_BUILD = "build";
    private static final String CLASSNAME_LOADERPARENT = "adf.component.AbstractLoader";

    public static void delegate(List<String> args)
    {
        alias(args, "-auto", "-autocp", "-autolc");
        alias(args, "-local", "-h:localhost");
        alias(args, "-all", "-t:-1:-1:-1:-1:-1:-1");
        alias(args, "-precompute", "-pre:true");
        alias(args, "-debug", "-d:true");


        if (args.contains(OPTION_COMPILE))
        {
            args.remove(OPTION_COMPILE);
            compileAgent();
        }

        if (args.contains(OPTION_AUTOCLASSPATH))
        {
            args.remove(OPTION_AUTOCLASSPATH);
            autoLoadDefaultClassPath();
        }

        if (args.contains(OPTION_AUTOLOADERCLASS))
        {
            args.remove(OPTION_AUTOLOADERCLASS);
            autoLoadDefaultLoaderClass(args);
        }

        if (args.size() <= 0)
        {
            printOptionList();
            System.exit(0);
        }
    }

    private static void printOptionList()
    {
        System.out.println("Options:");
        System.out.println("-t:[FB]:[FS]:[PF]:[PO]:[AT]:[AC]\tnumber of agents");
        System.out.println("-fb:[FB]\t\t\t\tnumber of FireBrigade");
        System.out.println("-fs:[FS]\t\t\t\tnumber of FireStation");
        System.out.println("-pf:[PF]\t\t\t\tnumber of PoliceForce");
        System.out.println("-po:[PO]\t\t\t\tnumber of PoliceOffice");
        System.out.println("-at:[AT]\t\t\t\tnumber of AmbulanceTeam");
        System.out.println("-ac:[AC]\t\t\t\tnumber of AmbulanceCentre");
        System.out.println("-s:[HOST]\t\t\t\tRCRS server host and port");
        System.out.println("-h:[HOST]\t\t\t\tRCRS server host (port:7000)");
        System.out.println("-pre:[0|1]\t\t\t\tPrecompute flag");
        System.out.println("-mc:[FILE]\t\t\t\tModuleConfig file name");
        System.out.println("-compile\t\t\t\trun compile");
        System.out.println("-autocp\t\t\t\t\tauto load class path form " + DIRECTORY_LIBRARY);
        System.out.println("-autolc\t\t\t\t\tauto load loader class form " + DIRECTORY_BUILD);
        System.out.println("-d:[0|1]\t\t\t\tDebug flag");
        System.out.println("-auto\t\t\t\t\t[alias] -autocp -autolc");
        System.out.println("-all\t\t\t\t\t[alias] -t:-1:-1:-1:-1:-1:-1");
        System.out.println("-local\t\t\t\t\t[alias] -h:localhost");
        System.out.println("-precompute\t\t\t\t[alias] -pre:true");
        System.out.println("-debug\t\t\t\t\t[alias] -d:true");
        System.out.println();
    }

    private static void alias(List<String>args, String option, String... original)
    {
        if (args.contains(option))
        {
            List<String> temp = new ArrayList<>();
            for (String org : original)
            {
                if (!args.contains(org))
                { temp.add(org); }
            }
            args.addAll(temp);
            args.remove(option);
        }
    }

    private static void autoLoadDefaultLoaderClass(List<String> args)
    {
        args.add(0, getLoaderClass(DIRECTORY_BUILD));
    }

    private static void autoLoadDefaultClassPath()
    {
        addClassPath(DIRECTORY_BUILD);

        String[] classPathArray = getClassPath(DIRECTORY_LIBRARY).split(System.getProperty("path.separator"), 0);
        for (String classPath : classPathArray)
        {
            if (classPath.length() > 0)
            { addClassPath(classPath); }
        }
    }

    private static void addClassPath(String path)
    {
        URLClassLoader systemLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<?> systemClass = URLClassLoader.class;
        try {
            Method method = systemClass.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(systemLoader, new File(path).toURI().toURL());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void compileAgent()
    {
        String workDir = System.getProperty("user.dir");
        System.out.println("Working Directory: " + workDir);
        String library = workDir + File.separator + DIRECTORY_LIBRARY;
        String src = workDir + File.separator + DIRECTORY_SRC;
        String build = workDir + File.separator + DIRECTORY_BUILD;

        File libraryDir = new File(library);
        File srcDir = new File(src);
        if (!(libraryDir.isDirectory() && srcDir.isDirectory()))
        {
            System.out.println("[ERROR ] Does not have the required directory.");
            System.exit(-1);
        }

        File buildDir = new File(build);
        deleteFile(buildDir);
        buildDir.mkdir();

        List<String> cmdArray = new ArrayList<>();
        cmdArray.add("javac");
        cmdArray.add("-cp");
        cmdArray.add(getClassPath(library));
        cmdArray.add("-d");
        cmdArray.add(".." + File.separator + "build" + File.separator);
        cmdArray.addAll(getJavaFilesText(src));

        ProcessBuilder processBuilder = new ProcessBuilder(cmdArray);
        processBuilder.directory(srcDir);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line + System.getProperty("line.separator"));
            }
            System.out.println(sb.toString());
            br.close();

            if (process.exitValue()!= 0)
            { System.exit(process.exitValue()); }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[FINISH] Agent compiled.");
    }

    private static String getLoaderClass(String base)
    {
        return getLoaderClass(base, base);
    }

    private static String getLoaderClass(String base, String path)
    {
        String loaderClass = "";
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            Arrays.sort(files, (a, b)->(int)(b.lastModified() - a.lastModified()));
            for (File file : files) {
                if (file.isFile()) {
                    String filePath = file.getPath();
                    if (filePath.endsWith(".class") && !filePath.contains("$")) {
                        loaderClass = filePath.substring(base.length() + 1, filePath.length() - 6).replace(File.separator, ".");
                        try {
                            if (ClassLoader.getSystemClassLoader().loadClass(loaderClass).getSuperclass().getName().equals(CLASSNAME_LOADERPARENT)) {
                                return loaderClass;
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        loaderClass = "";

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    loaderClass = getLoaderClass(base, file.getPath());
                    if (!loaderClass.equals("")) {
                        return loaderClass;
                    }
                }
            }
        }

        return loaderClass;
    }

    private static String getClassPath(String path) {
        String classPath = "";
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String filePath = file.getPath();
                    if (filePath.endsWith(".jar") && !filePath.endsWith("-sources.jar")) {
                        classPath += filePath + System.getProperty("path.separator");
                    }
                } else if (file.isDirectory()) {
                    classPath += getClassPath(file.getPath());
                }
            }
        }

        return classPath;
    }

    private static List<String> getJavaFilesText(String path) {
        List<String> javaFilesText = new ArrayList<>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String filePath = file.getPath();
                    if (filePath.endsWith(".java")) {
                        javaFilesText.add(filePath);
                    }
                } else if (file.isDirectory()) {
                    javaFilesText.addAll(getJavaFilesText(file.getPath()));
                }
            }
        }

        return javaFilesText;
    }

    private static void deleteFile(File file)
    {
        if(!file.exists())
        { return; }

        if(file.isFile()) {
            file.delete();
        } else if(file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    deleteFile(file1);
                }
            }
            file.delete();
        }
    }
}
