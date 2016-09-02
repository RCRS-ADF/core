package adf.launcher;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class LaunchSupporter
{
    private final String OPTION_COMPILE = "-compile";
    private final String OPTION_JAVAHOME = "-javahome";
    private final String OPTION_CHECK = "-check";
    private final String OPTION_AUTOCLASSPATH = "-autocp";
    private final String OPTION_AUTOLOADERCLASS = "-autolc";
    private final String DIRECTORY_LIBRARY = "library";
    private final String DIRECTORY_SRC = "src";
    private final String DIRECTORY_BUILD = "build";
    private final String CLASSNAME_LOADERPARENT = "adf.component.AbstractLoader";

    private int countAgentCheckWarning;

    public LaunchSupporter()
    {
        countAgentCheckWarning = 0;
    }

    public void delegate(List<String> args)
    {
        boolean worked = false;
        String compilerJavaHome = null;

        alias(args, "-auto", "-autocp", "-autolc");
        alias(args, "-local", "-h", "localhost");
        alias(args, "-all", "-t", "-1,-1,-1,-1,-1,-1");
        alias(args, "-precompute", "-pre", "true");
        alias(args, "-debug", "-d", "true");
        alias(args, "-develop", "-dev", "true");
        alias(args, "-checkagent", "-autocp", "-check");

        if (args.contains(OPTION_JAVAHOME))
        {
            int index = args.indexOf(OPTION_JAVAHOME) + 1;
            if (index < args.size())
            {
                compilerJavaHome = args.get(index);
                args.remove(index);
            }
            args.remove(OPTION_JAVAHOME);
        }

        if (args.contains(OPTION_COMPILE))
        {
            args.remove(OPTION_COMPILE);
            compileAgent(compilerJavaHome);
            args.add(OPTION_AUTOCLASSPATH);
            args.add(OPTION_CHECK);
            worked = true;
        }

        if (args.contains(OPTION_AUTOCLASSPATH))
        {
            args.remove(OPTION_AUTOCLASSPATH);
            autoLoadDefaultClassPath();
        }

        if (args.contains(OPTION_CHECK))
        {
            args.remove(OPTION_CHECK);
            checkAgentClass();
            worked = true;
        }

        if (args.contains(OPTION_AUTOLOADERCLASS))
        {
            args.remove(OPTION_AUTOLOADERCLASS);
            autoLoadDefaultLoaderClass(args);
        }

        if (args.size() <= 0)
        {
            if (!worked)
            { printOptionList(); }
            System.exit(0);
        }
    }

    private void printOptionList()
    {
        System.out.println("Options:");
        System.out.println("-t [FB],[FS],[PF],[PO],[AT],[AC]\tnumber of agents");
        System.out.println("-fb [FB]\t\t\t\tnumber of FireBrigade");
        System.out.println("-fs [FS]\t\t\t\tnumber of FireStation");
        System.out.println("-pf [PF]\t\t\t\tnumber of PoliceForce");
        System.out.println("-po [PO]\t\t\t\tnumber of PoliceOffice");
        System.out.println("-at [AT]\t\t\t\tnumber of AmbulanceTeam");
        System.out.println("-ac [AC]\t\t\t\tnumber of AmbulanceCentre");
        System.out.println("-s [HOST]:[PORT]\t\t\t\tRCRS server host and port");
        System.out.println("-h [HOST]\t\t\t\tRCRS server host (port:7000)");
        System.out.println("-pre [0|1]\t\t\t\tPrecompute flag");
        System.out.println("-mc [FILE]\t\t\t\tModuleConfig file name");
        System.out.println("-dev [0|1]\t\t\t\tDevelop flag");
        System.out.println("-dd [JSON]\t\t\t\tDevelopData JSON");
        System.out.println("-df [JSON]\t\t\t\tDevelopData JSON file");
        System.out.println("-compile\t\t\t\trun compile");
        System.out.println("-javahome [JAVA_HOME]\t\t\tcompiler java-home");
        System.out.println("-autocp\t\t\t\t\tauto load class path form " + DIRECTORY_LIBRARY);
        System.out.println("-autolc\t\t\t\t\tauto load loader class form " + DIRECTORY_BUILD);
        System.out.println("-d [0|1]\t\t\t\tDebug flag");
        System.out.println("-check\t\t\t\t\tsimple agent class check");
        System.out.println("-auto\t\t\t\t\t[alias] -autocp -autolc");
        System.out.println("-all\t\t\t\t\t[alias] -t -1,-1,-1,-1,-1,-1");
        System.out.println("-local\t\t\t\t\t[alias] -h localhost");
        System.out.println("-precompute\t\t\t\t[alias] -pre true");
        System.out.println("-debug\t\t\t\t\t[alias] -d true");
        System.out.println("-develop\t\t\t\t[alias] -dev true");
        System.out.println("-checkagent\t\t\t\t[alias] -autocp -check");
        System.out.println();
    }

    private void alias(List<String> args, String option, String... original)
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

    private void autoLoadDefaultLoaderClass(List<String> args)
    {
        args.add(0, getLoaderClass(DIRECTORY_BUILD));
    }

    private void autoLoadDefaultClassPath()
    {
        addClassPath(DIRECTORY_BUILD);

        String[] classPathArray = getClassPath(DIRECTORY_LIBRARY).split(System.getProperty("path.separator"), 0);
        for (String classPath : classPathArray)
        {
            if (classPath.length() > 0)
            { addClassPath(classPath); }
        }
    }

    private void addClassPath(String path)
    {
        URLClassLoader systemLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<?> systemClass = URLClassLoader.class;
        try
        {
            Method method = systemClass.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(systemLoader, new File(path).toURI().toURL());
        } catch (NoSuchMethodException | IllegalAccessException | MalformedURLException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void compileAgent(String javaHome)
    {
        ConsoleOutput.start("Agent compile");
        String workDir = System.getProperty("user.dir");
        ConsoleOutput.info("Working Directory: " + workDir);
        String javac = "javac";
        if (javaHome != null)
        {
            javaHome = Pattern.compile(File.separator + "$").matcher(javaHome).replaceFirst("");
            javac = javaHome + File.separator + "bin" + File.separator + javac;
        }
        ConsoleOutput.info("Compiler javac: " + javac);
        String library = workDir + File.separator + DIRECTORY_LIBRARY;
        String src = workDir + File.separator + DIRECTORY_SRC;
        String build = workDir + File.separator + DIRECTORY_BUILD;

        File libraryDir = new File(library);
        File srcDir = new File(src);
        if (!(libraryDir.isDirectory() && srcDir.isDirectory()))
        {
            ConsoleOutput.error("Does not have the required directory");
            System.exit(-1);
        }

        File buildDir = new File(build);
        deleteFile(buildDir);
        if (!(buildDir.mkdir()))
        {
            ConsoleOutput.error("Make build directory failed");
            System.exit(-1);
        }

        List<String> cmdArray = new ArrayList<>();
        cmdArray.add(javac);
        cmdArray.add("-cp");
        cmdArray.add(getClassPath(library));
        cmdArray.add("-d");
        cmdArray.add(".." + File.separator + DIRECTORY_BUILD + File.separator);
        cmdArray.addAll(getJavaFilesText(src));

        ProcessBuilder processBuilder = new ProcessBuilder(cmdArray);
        processBuilder.directory(srcDir);
        processBuilder.redirectErrorStream(true);

        try
        {
            Process process = processBuilder.start();
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null)
            { sb.append(line).append(System.getProperty("line.separator")); }
            System.out.print(sb.toString());
            br.close();

            if (process.waitFor() != 0)
            {
                ConsoleOutput.error("Compile failed");
                System.exit(process.exitValue());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        ConsoleOutput.out(ConsoleOutput.State.FINISH, "Agent compile");
    }

    private void checkAgentClass()
    {
        countAgentCheckWarning = 0;
        ConsoleOutput.start("Agent class check");
        checkAgentClass(DIRECTORY_BUILD, DIRECTORY_BUILD);
        ConsoleOutput.finish("Agent class check (" + countAgentCheckWarning +
                " warning" + (countAgentCheckWarning > 1 ? 's' : "") + ")");
    }

    private void checkAgentClass(String base, String path)
    {
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null)
        {
            Arrays.sort(files, (a, b) -> (int) (b.lastModified() - a.lastModified()));
            for (File file : files)
            {
                if (file.isFile())
                {
                    String filePath = file.getPath();
                    if (filePath.endsWith(".class") && !filePath.contains("$"))
                    {
                        String loaderClass = filePath.substring(base.length() + 1, filePath.length() - 6).replace(File.separator, ".");
                        try
                        {
                            Class clazz = ClassLoader.getSystemClassLoader().loadClass(loaderClass);

                            ArrayList<String> methodList = new ArrayList<>();
                            for (Method method : clazz.getDeclaredMethods())
                            {
                                if (Modifier.isPublic(method.getModifiers()))
                                {
                                    String sign = method.getReturnType().getName() + ":" + method.getName() + ":";
                                    for (Class paramClass : method.getParameterTypes())
                                    { sign += paramClass.getName() + ","; }
                                    methodList.add(sign);
                                }
                            }

                            clazz = clazz.getSuperclass();
                            while (!(clazz.equals(java.lang.Object.class)))
                            {
                                for (Method method : clazz.getDeclaredMethods())
                                {
                                    if (!(Modifier.isPrivate(method.getModifiers())))
                                    {
                                        String sign = method.getReturnType().getName() + ":" + method.getName() + ":";
                                        for (Class paramClass : method.getParameterTypes())
                                        { sign += paramClass.getName() + ","; }
                                        methodList.remove(sign);
                                    }
                                }
                                clazz = clazz.getSuperclass();
                            }

                            if (methodList.size() > 0)
                            {
                                ConsoleOutput.warn("Exist violating public method : " + loaderClass);
                                countAgentCheckWarning += methodList.size();
                                for (String methodSign : methodList)
                                {
                                    String methodData[] = methodSign.split(":", 3);
                                    String returnType[] = methodData[0].split("\\.");
                                    String paramType[] = methodData[2].split(",");
                                    System.out.print("\t" + returnType[returnType.length -1] + " " + methodData[1] + "(");
                                    boolean isFirst = true;
                                    for (String name : paramType)
                                    {
                                        if (isFirst)
                                        { isFirst = false; }
                                        else
                                        { System.out.print(", "); }
                                        String splitedName[] = name.split("\\.");
                                        System.out.print(splitedName[splitedName.length -1]);
                                    }
                                    System.out.println(")");
                                }
                            }

                            for (Field field : ClassLoader.getSystemClassLoader().loadClass(loaderClass).getDeclaredFields())
                            {
                                if (Modifier.isStatic(field.getModifiers()) && !(Modifier.isFinal(field.getModifiers())))
                                {
                                    ConsoleOutput.warn("Exist variable static field : " + loaderClass + "." + field.getName());
                                    countAgentCheckWarning++;
                                }
                                else if (Modifier.isPublic(field.getModifiers()))
                                {
                                    ConsoleOutput.warn("Exist public field : " + loaderClass + "." + field.getName());
                                    countAgentCheckWarning++;
                                }
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                { checkAgentClass(base, file.getPath()); }
            }
        }
    }

    private String getLoaderClass(String base)
    {
        return getLoaderClass(base, base);
    }

    private String getLoaderClass(String base, String path)
    {
        String loaderClass;
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null)
        {
            Arrays.sort(files, (a, b) -> (int) (b.lastModified() - a.lastModified()));
            for (File file : files)
            {
                if (file.isFile())
                {
                    String filePath = file.getPath();
                    if (filePath.endsWith(".class") && !filePath.contains("$"))
                    {
                        loaderClass = filePath.substring(base.length() + 1, filePath.length() - 6).replace(File.separator, ".");
                        try
                        {
                            if (ClassLoader.getSystemClassLoader().loadClass(loaderClass).getSuperclass().getName().equals(CLASSNAME_LOADERPARENT))
                            { return loaderClass; }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        loaderClass = "";

        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    loaderClass = getLoaderClass(base, file.getPath());
                    if (!loaderClass.equals(""))
                    { return loaderClass; }
                }
            }
        }

        return loaderClass;
    }

    private String getClassPath(String path)
    {
        String classPath = "";
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile())
                {
                    String filePath = file.getPath();
                    if (filePath.endsWith(".jar") && !filePath.endsWith("-sources.jar"))
                    { classPath += filePath + System.getProperty("path.separator"); }
                }
                else if (file.isDirectory())
                { classPath += getClassPath(file.getPath()); }
            }
        }

        return classPath;
    }

    private List<String> getJavaFilesText(String path)
    {
        List<String> javaFilesText = new ArrayList<>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile())
                {
                    String filePath = file.getPath();
                    if (filePath.endsWith(".java"))
                    { javaFilesText.add(filePath); }
                } else if (file.isDirectory())
                { javaFilesText.addAll(getJavaFilesText(file.getPath())); }
            }
        }

        return javaFilesText;
    }

    private void deleteFile(File file)
    {
        if (!file.exists())
        { return; }

        if (file.isFile())
        {
            if (!(file.delete()))
            { ConsoleOutput.error("Delete file failed"); }
        }
        else if (file.isDirectory())
        {
            File[] files = file.listFiles();
            if (files != null)
            {
                for (File file1 : files)
                { deleteFile(file1); }
            }
            if (!(file.delete()))
            { ConsoleOutput.error("Delete file failed"); }
        }
    }
}
