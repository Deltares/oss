package nl.deltares.tasks.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.tasks.AbstractDataRequest;

import java.io.*;
import java.nio.file.Files;
import java.util.Random;

public class DummyRequest extends AbstractDataRequest {

    private static final Log logger = LogFactoryUtil.getLog(DummyRequest.class);

    public DummyRequest(String id, long currentUserId) throws IOException {
        super(id, currentUserId);
    }

    @Override
    public void start() {

        if (getStatus() == STATUS.available) return;

        if (thread != null) throw new IllegalStateException("Thread already started!");
        status = STATUS.running;
        totalCount = 20;
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
                        processedCount++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    status = STATUS.available;
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    logger.warn("Error serializing csv content: %s", e);
                    status = STATUS.terminated;
                }
                if (status == STATUS.available) {
                    if (dataFile.exists()) Files.deleteIfExists(dataFile.toPath());
                    Files.move(tempFile.toPath(), dataFile.toPath());
                }
            } catch (IOException e) {
                errorMessage = e.getMessage();
                status = STATUS.terminated;
            }
            fireStateChanged();

        }, id);

        thread.start();

    }

    public void startNoThread(Writer w) {

        if (getStatus() == STATUS.available) return;

        status = STATUS.running;
        totalCount = 20;
        //Create local session because the servlet session will close after call to endpoint is completed
        try (PrintWriter writer = new PrintWriter(w)) {

            Random random = new Random();

            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 1024; j++) {
                    writer.write(48 + random.nextInt(10));

                }
                writer.flush();
                processedCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            status = STATUS.available;
        } catch (Exception e) {
            errorMessage = e.getMessage();
            logger.warn("Error serializing csv content: %s", e);
            status = STATUS.terminated;
        }


    }
}
