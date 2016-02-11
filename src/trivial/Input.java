/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trivial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DAW15
 */
public class Input {
    public static String input(String message){
        System.out.print(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean validAnswer = false;
        String value = "";
        while (!validAnswer){
            try{
                value = br.readLine(); validAnswer = true;
            }
            catch(Exception e){
                System.out.print("Por favor introduce un valor adecuado. \n>>>");
            }
        }
        return value;
    }
    public static byte byteInput(String message){
        System.out.print(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean validAnswer = false;
        byte value = 0;
        while (!validAnswer){
            try{
                value = Byte.parseByte(br.readLine()); validAnswer = true;
            }
            catch(Exception e){
                System.out.print("Por favor introduce un valor adecuado. \n>>>");
            }
        }
        return value;
    }
    public static String readUTFByRandomAccess(long position, String file){
        String text = "";
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.seek(position);
            text = raf.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        return text;
    }
    public static boolean readBooleanByRandomAccess(long position, String file){
        boolean bool = false;
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.seek(position);
            bool = raf.readBoolean();
        } catch (IOException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        return bool;
    }
    public static int readIntegerByRandomAccess(long position, String file){
        int number = 0;
        try{
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.seek(position);
            number = raf.readInt();
        }
        catch (IOException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        return number;
    }
}
