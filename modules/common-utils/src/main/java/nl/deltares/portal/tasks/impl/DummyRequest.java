package nl.deltares.portal.tasks.impl;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.portal.tasks.DataRequest;
import nl.deltares.portal.tasks.DataRequestManager;

import java.io.*;
import java.nio.file.Files;
import java.util.Random;

import static nl.deltares.portal.tasks.DataRequest.STATUS.*;

public class DummyRequest implements DataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DummyRequest.class);
    private STATUS status = pending;
    private final String id;
    private int deletionCount;
    private final File logFile;

    private String errorMessage;

    private Thread thread;
    private File tempDir;

    private DataRequestManager manager;

    public DummyRequest(String id) throws IOException {
        this.id = id;
        this.logFile = new File(getExportDir(), id + ".log");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void dispose() {

        status = terminated;
        if (thread != null){
            thread.interrupt();
            try {
                thread.join(5000);
            } catch (InterruptedException e) {
                //
            }
        }

        if (logFile != null && logFile.exists()) {
            try {
                Files.deleteIfExists(logFile.toPath());
            } catch (IOException e) {
                logger.warn(String.format("Cannot delete file %s: %s", logFile.getAbsolutePath(), e.getMessage()));
            }
        }
        if (manager != null) manager.fireStateChanged(this);

    }

    @Override
    public void start() {

        if (getStatus() == available) return;

        if (thread != null) throw new IllegalStateException("Thread already started!");
        status = running;
        thread = new Thread(() -> {

            try {
                File tempFile = new File(getExportDir(), id + ".tmp");
                if (tempFile.exists()) Files.deleteIfExists(tempFile.toPath());

                //Create local session because the servlet session will close after call to endpoint is completed
                try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

                    Random random = new Random();

                    for (int i = 0; i < 20; i++) {
                        for (int j = 0; j < 1024; j++) {
                            writer.write(48 + random.nextInt(10));

                        }
                        writer.flush();
                        deletionCount++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    status = available;
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    logger.warn("Error serializing csv content: %s", e);
                    status = terminated;
                }
                if (status == available) {
                    if (logFile.exists()) Files.deleteIfExists(logFile.toPath());
                    Files.move(tempFile.toPath(), logFile.toPath());
                }
            } catch (IOException e) {
                errorMessage = e.getMessage();
                status = terminated;
            }
            if (manager != null) manager.fireStateChanged(this);

        }, id);

        thread.start();

    }

    public void startNoThread(Writer w) {

        if (getStatus() == available) return;

        status = running;

        //Create local session because the servlet session will close after call to endpoint is completed
        try (PrintWriter writer = new PrintWriter(w)) {

            Random random = new Random();

            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 1024; j++) {
                    writer.write(48 + random.nextInt(10));

                }
                writer.flush();
                deletionCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            status = available;
        } catch (Exception e) {
            errorMessage = e.getMessage();
            logger.warn("Error serializing csv content: %s", e);
            status = terminated;
        }


    }
    @Override
    public STATUS getStatus() {
        return status;
    }

    @Override
    public File getDataFile() {
        if (status != available) throw new IllegalStateException("Data not available! Check if state is 'available'!");
        return logFile;
    }

    @Override
    public String getErrorMessage() {
        JSONObject statusJson = JSONFactoryUtil.createJSONObject();
        statusJson.put("id", id);
        statusJson.put("request", DummyRequest.class.getSimpleName());
        statusJson.put("status", status.toString());
        statusJson.put("message", errorMessage);
        return statusJson.toJSONString();
    }

    @Override
    public String getStatusMessage() {
        JSONObject statusJson = JSONFactoryUtil.createJSONObject();
        statusJson.put("id", id);
        statusJson.put("request", DummyRequest.class.getSimpleName());
        statusJson.put("status", status.toString());
        statusJson.put("progress", deletionCount);
        statusJson.put("message", status.toString());
        statusJson.put("total", 20);
        return statusJson.toJSONString();
    }

    @Override
    public void setDataRequestManager(DataRequestManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean isCached() {
        return false;
    }

    private File getExportDir() throws IOException {
        if (tempDir != null) return tempDir;
        String property = System.getProperty("java.io.tmpdir");
        if (property == null){
            throw new IOException("Missing system property: java.io.tmpdir");
        }
        tempDir = new File(property, "rooij_e");
        if (!tempDir.exists()) Files.createDirectory(tempDir.toPath());
        return tempDir;
    }
}
