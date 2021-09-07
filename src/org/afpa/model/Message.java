package org.afpa.model;

import org.afpa.tool.TransCoder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
/**
 * File management system to encode and decode messages with TransCoder.
 *
 * @author Benjamin Vuagnoux
 */
public class Message {
    private Boolean encoded;
    private List<String> msgClear = new ArrayList<String>();
    private List<String> msgEncoded = new ArrayList<String>();
    private Path msgClearPath;
    private Path msgEncodedPath;
    private Path keyPath;
    private String key;
    private TransCoder transCoder;

    /**
     *
     * @param encoded: true if we want to decode a message, false to code.
     * @param msgClearPath: path of the message to code.
     * @param msgEncodedPath: path of the message to decode.
     * @param keyPath: path of the key.
     */
    public Message(Boolean encoded, String msgClearPath, String msgEncodedPath, String keyPath) {
        this.encoded = encoded;
        this.msgClearPath = Paths.get(System.getProperty("user.dir"), msgClearPath);
        this.msgEncodedPath = Paths.get(System.getProperty("user.dir"), msgEncodedPath);
        this.keyPath = Paths.get(System.getProperty("user.dir"), keyPath);
        initKey();
        transCoder = new TransCoder(key);
    }

    /**
     * Initialisation of the private parameter key.
     *
     * Error handling while handling files.
     */
    private void initKey() {
        try {
            key = Files.readString(keyPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Either encode a message from a file or decode a message from a file. Depending on the Boolean encoded.
     */
    public String readNwrite() {
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
            return "Votre message à bien été décodé, retrouvez le ici : " + msgClearPath;
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
            return "Votre message à bien été codé, retrouvez le ici : " + msgEncodedPath;
        }
    }

}



