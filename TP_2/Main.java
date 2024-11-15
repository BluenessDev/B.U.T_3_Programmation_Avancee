package TP_2;

import java.io.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.lang.String;

public class Main {

    public static void main(String[] args) {
        NewAffichage TA = new NewAffichage("AAA");
        NewAffichage TB = new NewAffichage("BB");
        NewAffichage TC = new NewAffichage("CCCC");
        NewAffichage TD = new NewAffichage("DDD");

        TA.start();
        TC.start();
        TB.start();
        TD.start();
    }
}

