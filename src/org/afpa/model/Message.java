package org.afpa.model;

import org.afpa.tool.TransCoder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private Boolean encoded;
    private List<String> msgClear = new ArrayList<String>();
    private List<String> msgEncoded = new ArrayList<String>();
    private Path msgClearPath;
    private Path msgEncodedPath;
    private Path keyPath;
    private String key;
    private TransCoder transCoder;

    public Message(Boolean encoded, String msgClearPath, String msgEncodedPath, String keyPath) {
        this.encoded = encoded;
        this.msgClearPath = Paths.get(System.getProperty("user.dir"), msgClearPath);
        this.msgEncodedPath = Paths.get(System.getProperty("user.dir"), msgEncodedPath);
        this.keyPath = Paths.get(System.getProperty("user.dir"), keyPath);
        initKey();
        transCoder = new TransCoder(key);
    }

    private void initKey() {
        try {
            key = Files.readString(keyPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readNwrite() {
        if (encoded) {
            try {
                msgEncoded = Files.readAllLines(msgEncodedPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String line : msgEncoded) {
                try {
                    Files.writeString(msgClearPath, transCoder.decodeMsg(line) + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Votre message à bien été décodé, retrouvez le ici : " + msgClearPath);
        } else {
            try {
                msgClear = Files.readAllLines(msgClearPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String line : msgClear) {
                try {
                    Files.writeString(msgEncodedPath, transCoder.encodeMsg(line) + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Votre message à bien été codé, retrouvez le ici : " + msgEncodedPath);
        }
    }

}



