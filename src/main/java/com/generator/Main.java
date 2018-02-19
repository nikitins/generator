package com.generator;

import com.sun.org.apache.regexp.internal.RE;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Main {
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws IOException {
        int recordCount;
        int port = 7777;

        try {
            recordCount = Integer.valueOf(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect record number: '" + args[0] + "'");
            return;
        }

        try {
            if(args.length > 1) {
                port = Integer.valueOf(args[1]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Incorrect port: '" + args[1] + "'");
            return;
        }

        Socket socket = new Socket("127.0.0.1", port);

        DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        for (int i = 0; i < recordCount; i++) {
            Record record = generateRecord();
            os.writeBytes(record.toString());
        }
        os.flush();
        os.close();
    }

    private static Record generateRecord() {
        Record record = new Record();
        record.setProductName("Product_" + random.nextInt(100));
        record.setCategory("Category_" + random.nextInt(100));
        record.setPrice(random.nextInt(1000) + 1);
        record.setIPAddress((random.nextInt(30) + 1) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256));
        String time = String.format("%02d", random.nextInt(24)) + ":" +
                String.format("%02d", random.nextInt(60));

        String date = "02." + String.format("%02d", random.nextInt(7) + 1) + ".2018";
        record.setDateTime(time + " " + date);
        return record;
    }
}
