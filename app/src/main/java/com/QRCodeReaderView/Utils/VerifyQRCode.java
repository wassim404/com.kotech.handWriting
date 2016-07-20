package com.QRCodeReaderView.Utils;

/**
 * Created by CK on 03/02/2015.
 */
public class VerifyQRCode {

    static String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String SECRETKEY = "Kotech-NjoScribe-Premium".toUpperCase();


    public static boolean VerifySerial(String Key) {

        String ConstParts[] = new String[(SECRETKEY.split("-")).length];
        ConstParts = SECRETKEY.split("-");

        String Keyparts[] = new String[(Key.substring(1).split("-")).length];
        Keyparts = Key.substring(1).split("-");
        boolean Checking = Keyparts.length == ConstParts.length;
        if (Checking) {
            for (int i = 0; i < Keyparts.length - 1; i++) {
                Checking = Checking && (Keyparts[i].length() == ConstParts[i].length() + 1);
                if (Checking) {
                    for (int j = 0; j < Keyparts[i].length() - 1; j++) {

                        if (!CHARSET.contains(String.valueOf(Keyparts[i].charAt(j)))) {
                            return false;
                        }
                    }
                }
            }
            if (!Checking) {
                return false;
            }

            for (int i = 0; i < Keyparts.length - 1; i++) {
                String cPart = "";

                for (int j = Keyparts[i].length() - 1; j > 0; j--) {

                    int integer = ((CHARSET.indexOf(Keyparts[i].charAt(j))) - CHARSET.indexOf(Keyparts[i].charAt(j - 1)) + CHARSET.length()) % CHARSET.length();
                    char CHARSETPART = CHARSET.charAt(integer);
                    cPart = CHARSETPART + cPart;
                }
                if (!cPart.equals(ConstParts[i])) {
                    return false;
                }

            }
            return true;
        }
        return false;
    }
}