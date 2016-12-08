package adf.launcher;

import java.io.*;
import java.net.*;

public class CoreUpdater
{
    private final String CORE_URL = "https://raw.githubusercontent.com/RCRS-ADF/core/jar/build/libs/adf-core.jar";
    private final String CORESRC_URL = "https://raw.githubusercontent.com/RCRS-ADF/core/jar/build/libs/adf-core-sources.jar";
    private final String SOURCES_DIR = "sources";
    private final String SOURCES_FILE = "adf-core-sources.jar";
    private final String ETAG_FILE = ".etag";

    public CoreUpdater()
    {
    }

    public boolean updateCore()
    {
        File coreFile = new File(System.getProperty("java.class.path"));
        String corePath = coreFile.getAbsolutePath();
        String sourcePath = coreFile.getParentFile().getAbsolutePath() + File.separator + SOURCES_DIR + File.separator + SOURCES_FILE;

        try
        {
            ConsoleOutput.start("Download jars");
            download(CORE_URL, corePath);
            download(CORESRC_URL, sourcePath);
            ConsoleOutput.finish("Download jars");
        }
        catch (Exception e)
        {
            ConsoleOutput.error("Download jars failed");
            return false;
        }

        return true;
    }

    public boolean checkUpdate()
    {
        try {
            URL url = new URL(CORE_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setAllowUserInteraction(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("GET");
            connection.connect();

            int httpStatusCode = connection.getResponseCode();
            if(httpStatusCode != HttpURLConnection.HTTP_OK)
            {
                return false;
            }

            String etag = connection.getHeaderField("ETag");
            String currentEtag = "";
            try{
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(getEtagPath())));
                currentEtag = bufferedReader.readLine();
            } catch (FileNotFoundException e) { }

            if (!(etag.equals(currentEtag)))
            {
                ConsoleOutput.info("adf-core update available");
                return true;
            }
        } catch (Exception e) { }

        return false;
    }

    private void updateEtag()
    {
        try {
            URL url = new URL(CORE_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setAllowUserInteraction(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("GET");
            connection.connect();

            int httpStatusCode = connection.getResponseCode();
            if(httpStatusCode != HttpURLConnection.HTTP_OK)
            {
                return;
            }

            String etag = connection.getHeaderField("ETag");
            try{
                FileWriter fileWriter = new FileWriter(new File(getEtagPath()));
                fileWriter.write(etag);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) { }
    }

    private void download(String urlStr, String fileStr) throws Exception
    {
        URL url = new URL(urlStr);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setAllowUserInteraction(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("GET");
        connection.connect();

        int httpStatusCode = connection.getResponseCode();
        if(httpStatusCode != HttpURLConnection.HTTP_OK)
        {
            throw new Exception();
        }

        InputStream dataInStream = new DataInputStream( connection.getInputStream());
        OutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileStr)));

        byte[] bytes = new byte[4096];
        int readByte = 0;
        while (-1 != (readByte = dataInStream.read(bytes)))
        {
            dataOutStream.write(bytes, 0, readByte);
        }

        dataInStream.close();
        dataOutStream.close();
    }

    private String getEtagPath()
    {
        return (new File(System.getProperty("java.class.path"))).getParentFile().getAbsolutePath() + File.separator + ETAG_FILE;
    }
}
