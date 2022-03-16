package com.company;


import com.company.a_model.Message;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MessageUI {
    public static void messages(){
        Scanner ais = new Scanner(System.in).useDelimiter("\n");
        //I'll need some kind of user dao to be able to print out the users
        System.out.println("\nwe have user 2 and 3, we'll suppose you are user 1 until further notice");

        listChats(1);//I'll need a get user


        System.out.println("please indicate the number of the user you want to send message to:");
        int userNr = ais.nextInt();
        while (userNr < 2 || userNr > 3){
            System.out.println("no such user, try again:");
            userNr = ais.nextInt();
        }
        //String filename_path=String.format("Messages/%s_%schat.ser",userNr,1);//will use something like get_user() to fill the second thing
        String filename_path= selectFile(userNr,1);

        listMessages(filename_path);

        System.out.println("now introduce the message you want to send");
        String mesaj=ais.nextLine();// Ii ceva eroare aici ca daca nu scriu chestia asta redundanta, nu merge; :)))))
        mesaj=ais.nextLine();
        Message mesaj1 = new Message(1, userNr, mesaj);// I should move this to the write message function

        writeMessage(mesaj1,filename_path);

        listMessages(filename_path);
    }


    public static void createFile(String path1){
        try {
            File fileIn = new File(path1);
            fileIn.createNewFile();

        }catch (IOException fnfe){
            System.out.println("Exception in selectOrCreateFile");
        }
    }

    public static String selectFile(int user_id, int reciever_id){

        String filename_path1=String.format("Messages/_%s_%s_chat.ser",user_id,reciever_id);
        String filename_path2=String.format("Messages/_%s_%s_chat.ser",reciever_id,user_id);

        Path path1 = Paths.get(filename_path1);
        Path path2 = Paths.get(filename_path2);
        if(Files.exists(path1)){
            return filename_path1;
        }else if(Files.exists(path2)){
            return filename_path2;
        }
        createFile(filename_path1);
        return filename_path1;

    }

    public static void listChats(int user_id){
        System.out.println("Current chats:");
        File f = new File("C:/Users/corin/Downloads/Facebook-Team/Facebook-Team/Messages");

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.contains("_"+"1");// get userid instead of just 1 and the asking sequence will be _1_
            }
        };

        String[] pathnames = f.list(filter);

        for (String pathname : pathnames) {
            // Print the names of files and directories
            System.out.println(pathname);
        }
    }

    public static void listMessages(String path1){
        System.out.println("here are the messages so far");
        try {
            FileInputStream fileIn = new FileInputStream(path1);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = null;
            try {
                while ((obj = in.readObject()) != null) {
                    if (obj instanceof Message) {
                        System.out.println(((Message) obj).toString());
                    }
                }
            } catch (EOFException e) {
                System.out.println("reached end of file\n");
            }
            in.close();
            fileIn.close();
        }catch(EOFException ef){
            System.out.println("nothing in file\n");
            return;
        }catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Message class not found");
            c.printStackTrace();
            return;
        }
    }

    public static void writeMessage(Message mesaj1, String path1){
        try {
            FileOutputStream fileOut = new FileOutputStream(path1,true);

            File fi = new File(path1);

            if (fi.length() == 0) {
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(mesaj1);
                out.close();
            }

            // There is content in file to be write on
            else {

                MyObjectOutputStream oos = null;
                oos = new MyObjectOutputStream(fileOut);
                oos.writeObject(mesaj1);

                // Closing the FileOutputStream object
                // to release memory resources
                oos.close();
            }


            fileOut.close();
            //System.out.printf("Serialized data is saved in %s\n",path1);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

}

class MyObjectOutputStream extends ObjectOutputStream {

    // Constructor of this class
    // 1. Default
    MyObjectOutputStream() throws IOException
    {

        // Super keyword refers to parent class instance
        super();
    }

    // Constructor of this class
    // 1. Parameterized constructor
    MyObjectOutputStream(OutputStream o) throws IOException
    {
        super(o);
    }

    // Method of this class
    public void writeStreamHeader() throws IOException
    {
        return;
    }
}
