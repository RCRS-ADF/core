package adf.launcher;

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
}
