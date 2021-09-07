package org.afpa.GUI;

import org.afpa.model.Message;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private Boolean encoded;
    private String msgClearPath = "message_clair.txt";
    private String msgEncodedPath = "message_code.txt";
    private String keyPath;
    private Message message;

    public void initMenu() {
        System.out.printf(
                "╔══════════════════════════════════════╗\n" +
                "║   System d'encodage et de décodage   ║\n" +
                "║             de messages              ║\n" +
                "╠══════════════════════════════════════╣\n" +
                "║       1) Décoder un message          ║\n" +
                "║       2) Encode un message           ║\n" +
                "║       3) Quitter                     ║\n" +
                "╚══════════════════════════════════════╝\n");

        // Validating user input
        int number;
        do {
            System.out.println("Veuillez entrer 1, 2 ou 3 : ");
            while (!sc.hasNextInt()) {
                System.out.println("Erreur : veuillez entrer un nombre (1, 2 ou 3)");
                sc.next();
            }
            number = sc.nextInt();
        } while ((number <= 0) || (number > 3));

        switch (number) {
            case 1:
                decodeMsg();
            case 2:
                encodeMsg();
            case 3:
                System.out.println("Merci et à bientôt.");
                break;
            default:
                System.out.println("Une erreur est survenue, le programme va se relancer");
                initMenu();
        }
    }

    private void decodeMsg() {
        //message needs to be decoded
        encoded = true;

        System.out.println("Entrez le nom du fichier à décoder (sans extension ex. message_a_decoder)");
        msgEncodedPath = sc.next() + ".txt";
        //checking if file exists
        if (!Files.exists(Paths.get(System.getProperty("user.dir"), msgEncodedPath))) {
            System.out.println("Votre fichier n'existe pas, veuillez reessayer");
            msgEncodedPath = sc.next() + ".txt";
        }

        System.out.println("Entrez le nom du fichier contenant la clé de décodage (sans extension ex. cle_secrette)");
        keyPath = sc.next() + ".txt";
        // Checking if file exists
        if (!Files.exists(Paths.get(System.getProperty("user.dir"), keyPath))) {
            System.out.println("Votre fichier n'existe pas, veuillez reessayer");
            keyPath = sc.next() + ".txt";
        }
        System.out.println("la clé a été enregistrée");
        System.out.println("====== Décodage =====");

        message = new Message(encoded, msgClearPath, msgEncodedPath, keyPath);
        message.readNwrite();
    }

    private void encodeMsg() {
        // message needs to be encoded
        encoded = false;

        System.out.println("Entrez le nom du fichier à encoder (sans extension ex. message_a_coder)");
        msgClearPath = sc.next() + ".txt";
        // Checking if file exists
        if (!Files.exists(Paths.get(System.getProperty("user.dir"), msgClearPath))) {
            System.out.println("Votre fichier n'existe pas, veuillez reessayer");
            msgClearPath = sc.next() + ".txt";
        }

        System.out.println("Entrez le nom du fichier contenant la clé de décodage (sans extension ex. cle_secrette)");
        keyPath = sc.next() + ".txt";
        // Checking if file exists
        if (!Files.exists(Paths.get(System.getProperty("user.dir"), keyPath))) {
            System.out.println("Votre fichier n'existe pas, veuillez reessayer");
            keyPath = sc.next() + ".txt";
        }

        System.out.println("la clé a été enregistrée");
        System.out.println("====== Décodage =====");

        message = new Message(encoded, msgClearPath, msgEncodedPath, keyPath);
        message.readNwrite();
    }

}
