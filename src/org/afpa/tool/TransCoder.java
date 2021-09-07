package org.afpa.tool;

import org.apache.commons.lang3.StringUtils;
import org.germain.tool.ManaBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TransCoder {

    private HashMap<String, Character> encodeMap = new HashMap<String, Character>();
    private HashMap<Character, String> decodeMap = new HashMap<Character, String>();

    public TransCoder(String cryptKey) {
        generateMaps(ManaBox.decrypt(cryptKey));
    }

    public HashMap<String, Character> getEncodeMap() {
        return encodeMap;
    }

    public HashMap<Character, String> getDecodeMap() {
        return decodeMap;
    }

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

    private ArrayList<String> splitCode(String toDecode) {
        ArrayList<String> splitCode = new ArrayList<String>();

        for (int i = 0; i < toDecode.length(); i += 2) {
            splitCode.add(String.valueOf(toDecode.toCharArray()[i]) + String.valueOf(toDecode.toCharArray()[i + 1]));
        }

        return splitCode;
    }

    public HashMap<String, Character> generateMaps(String decryptedKey) {
        char[] keyDecryptedArr = decryptedKey.toCharArray();
        HashMap<String, Character> encode = new HashMap<String, Character>();

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
        return encode;
    }

}
