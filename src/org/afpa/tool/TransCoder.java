package org.afpa.tool;

import org.apache.commons.lang3.StringUtils;
import org.germain.tool.ManaBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/**
 * This class decrypt the key and uses it either encode or decode a message.
 *
 * @author Benjamin Vuagnoux
 */
public class TransCoder {

    private HashMap<String, Character> encodeMap = new HashMap<String, Character>();
    private HashMap<Character, String> decodeMap = new HashMap<Character, String>();

    /**
     * Contructor used to initialize both Hashmaps.
     *
     * @param cryptKey: the key krypted with manabox
     */
    public TransCoder(String cryptKey) {
        generateMaps(ManaBox.decrypt(cryptKey));
    }

    public HashMap<String, Character> getEncodeMap() {
        return encodeMap;
    }

    public HashMap<Character, String> getDecodeMap() {
        return decodeMap;
    }

    /**
     * Transform a clear string into a coded string.
     *
     * @param toEncode: clear String that we want to encode.
     * @return the String in its encoded version.
     */
    public String encodeMsg(String toEncode) {
        ArrayList<String> encodedMessage = new ArrayList<String>();

        for (char cString : StringUtils.stripAccents(toEncode).toCharArray()) {
            for (String key : encodeMap.keySet()) {
                if (cString == encodeMap.get(key)) {
                    encodedMessage.add(key);
                }
            }
        }
        return String.join("", encodedMessage);
    }

    /**
     * Transform a coded string into a clear one.
     *
     * @param toDecode: coded String that we want to decode.
     * @return the String in its clear version
     */
    public String decodeMsg(String toDecode) {
        ArrayList<String> decodedMsg = new ArrayList<String>();

        for (String s : splitCode(toDecode)) {
            for (char c : decodeMap.keySet()) {
                if (Objects.equals(s, decodeMap.get(c))) {
                    decodedMsg.add(String.valueOf(c));
                }
            }
        }

        return String.join("", decodedMsg);
    }

    /**
     * Used in decodeMsg(), split a coded string into an array of pairs.
     *
     * @param toDecode: encoded String that we want to split into pairs.
     * @return an array of String (pairs of characters)
     */
    private ArrayList<String> splitCode(String toDecode) {
        ArrayList<String> splitCode = new ArrayList<String>();

        for (int i = 0; i < toDecode.length(); i += 2) {
            splitCode.add(String.valueOf(toDecode.toCharArray()[i]) + String.valueOf(toDecode.toCharArray()[i + 1]));
        }

        return splitCode;
    }

    /**
     * Generate 2 private Hashmap properties to either encode and decode messages.
     *
     * @param decryptedKey: the key decrypted with manabox.
     */
    public void generateMaps(String decryptedKey) {
        char[] keyDecryptedArr = decryptedKey.toCharArray();

        int count = 0;
        for (char firstLetter = 'A'; firstLetter <= 'Z'; firstLetter++) {
            for (char secondLetter = 'A'; secondLetter <= 'Z'; secondLetter++) {
                if (count >= decryptedKey.length()) {
                    break;
                } else {
                    encodeMap.put(String.valueOf(firstLetter) + String.valueOf(secondLetter), keyDecryptedArr[count] );
                    decodeMap.put(keyDecryptedArr[count], firstLetter + "" + secondLetter);
                    count++;
                }
            }
        }
    }
}
