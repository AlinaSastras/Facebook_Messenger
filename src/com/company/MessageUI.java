package com.company;


import com.company.a_model.Message;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MessageUI {
    static Scanner ais = new Scanner(System.in).useDelimiter("\n");

    public static void messages(){
       //I'll need some kind of user dao to be able to print out the users

        mainPoint();

    }

    public static void mainPoint(){
        System.out.println("Main messenger menu:");
        System.out.println("option 1 = see users -not ready ; option 2 = see chats ; option -1 = back -not ready ; exit =0");
        int selection=getIntFromUnwillingUser();


        while (selection<4)
        {
            switch(selection){
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    //see users method
                    break;

                case 2:
                    listChats(1);// a getUser in the bracket
                    chooseChatToInteract();
                    break;

                case -1:
                    // back to main ui
                    break;
                default:
                    System.err.println("Based on my personal intuition your iq is over 112, please try again");
                    mainPoint();
            }
            selection = getIntFromUnwillingUser(); // add this
        }
        System.err.println("Based on my personal intuition your iq is over 112, please try again");
        mainPoint();
    }

    public static int getIntFromUnwillingUser(){
            int i=0;
            boolean done=false;
            String isi= ais.nextLine();
            while (!isi.matches("-?\\d+")){
                if(!isi.matches("-?\\d+")){
                    System.out.println("please give valid number option");
                }
                isi= ais.nextLine();
            }
            i = Integer.parseInt(isi);
            return i;
    }

    public static void chooseChatToInteract(){
        System.out.println("option 1 = choose chat ; option -1 = back to chats ; exit =0");
        int selection = getIntFromUnwillingUser();

        while (selection<4)
        {
            switch (selection) {
                case 0 -> System.exit(0);
                case 1 -> {
                    int reciever = chooseUser();
                    inChatPoint(reciever, selectFile(1, reciever));
                }
                case -1 -> chatsPoint();
                default -> {
                    System.err.println("Based on my personal intuition your iq is over 112, please try again");
                    chooseChatToInteract();
                }
            }
            selection = getIntFromUnwillingUser();
        }
        System.err.println("Based on my personal intuition your iq is over 112, please try again");
        chooseChatToInteract();
    }

    public static void chatsPoint(){
        System.out.println("Chats Point");
        System.out.println("option 1 = open chat ; option -1 = back to chats ; exit =0");
        int selection2 = getIntFromUnwillingUser();
        while (selection2<4)
        {
            switch (selection2) {
                case 0 -> System.exit(0);
                case 1 -> {
                    System.out.println("choose the id of the user with chat you want to open");
                    System.out.println("that's 2 or 3 right now");
                    int userNr = chooseUser();
                    String filename_path = selectFile(1, userNr);
                    inChatPoint(userNr, filename_path);
                }
                case -1 -> mainPoint();
                default -> {
                    System.err.println("Based on my personal intuition your iq is over 112, please try again");
                    chatsPoint();
                }

                }
            selection2 = getIntFromUnwillingUser();
        }
        System.err.println("Based on my personal intuition your iq is over 112, please try again");
        chatsPoint();
    }

    public static void inChatPoint(int receiverNr,String filename_path){
        listMessages(filename_path);
        System.out.println("option 1 = write message ; option 2 = reload chat ; option 3 = forward message ;");
        System.out.println("option 4 = reply to message ; option -1 = back to chats point ; exit =0");
        int selection3 = getIntFromUnwillingUser();
        while (selection3<5)
        {
            switch (selection3) {
                case 0 -> System.exit(0);
                case 1 -> {
                    System.out.println("now introduce the message you want to send");
                    String mesaj = ais.nextLine();// Ii ceva eroare aici ca daca nu scriu chestia asta redundanta, nu merge; :)))))
                    mesaj = ais.nextLine();
                    Message mesaj1 = new Message(1, receiverNr, mesaj);// I should move this to the write message function
                    writeMessage(mesaj1, filename_path);
                    inChatPoint(receiverNr, filename_path);
                }
                case 2 -> {
                    listMessages(filename_path);
                    inChatPoint(receiverNr, filename_path);
                }
                case 3 -> {
                    System.out.println("choose the message you want to forward");
                    int message_id = ais.nextInt();
                    String messageToForward = getMessage(filename_path, message_id);
                    System.out.println("choose the user you want to send the message to");
                    int receiverOfForward = chooseUser();
                    forwardMessage(messageToForward, receiverOfForward);
                    System.out.println("you have been switched to the chat you forwarded the message to");
                    inChatPoint(receiverOfForward, selectFile(1, receiverOfForward));//use the get user here also
                }
                case 4 -> {
                    System.out.println("choose message you want to reply to");
                    int message_id=ais.nextInt();
                    System.out.println("now type in the text of the reply");
                    String replyMesaj= ais.nextLine();
                    replyMesaj=ais.nextLine();
                    replyToMessage(replyMesaj,message_id,filename_path,receiverNr);
                    inChatPoint(receiverNr, filename_path);
                }

                case -1 -> chatsPoint();
                default -> {
                    System.err.println("Based on my personal intuition your iq is over 112, please try again");
                    inChatPoint(receiverNr,filename_path);
                }
            }
            selection3 = getIntFromUnwillingUser();
        }
        System.err.println("Based on my personal intuition your iq is over 112, please try again");
        inChatPoint(receiverNr,filename_path);

    }

    public static void forwardMessage(String Mesaj,int toWho){
        String File=selectFile(1,toWho);
        Message messageToForward=new Message(1,toWho,"--Forwarded--"+Mesaj);
        writeMessage(messageToForward,File);
    }

    public static void replyToMessage(String Mesaj,int whichMessage, String filename_path, int recieverNr){
        Message mesajBaza = getMessageObject(filename_path,whichMessage);
        if(mesajBaza.getMessage().contains("_reply*")){
            mesajBaza.setMessage(mesajBaza.getMessage().replace("_reply*",""));
        }
        writeMessage(mesajBaza,filename_path);
        Message mesajReply = new Message(1,recieverNr,"_reply*"+Mesaj);
        writeMessage(mesajReply,filename_path);

    }

    public static int chooseUser(){
        listChats(1);//I'll need a get user
        //listUsers would be better
        System.out.println("please indicate the number of the user you want to send message to");
        int userNr = getIntFromUnwillingUser();
        while (userNr < 2 || userNr > 3){
            System.out.println("no such user, try again:");
            userNr = getIntFromUnwillingUser();
        }
        return userNr;
    }


    public static String getMessage(String path1, int message_id){
        try {
            FileInputStream fileIn = new FileInputStream(path1);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = null;
            try {
                while ((obj = in.readObject()) != null) {
                    if (obj instanceof Message) {
                        if(((Message) obj).getMessage_id()==message_id){
                            return ((Message) obj).getMessage();
                        }
                        //System.out.println(((Message) obj).toString());
                    }
                }
            } catch (EOFException e) {
                System.out.println("no messages to be forwarded in file\n");
            }
            in.close();
            fileIn.close();
        }catch(EOFException ef){
            System.out.println("nothing in file\n");
            return "";
        }catch (IOException i) {
            i.printStackTrace();
            return "";
        } catch (ClassNotFoundException c) {
            System.out.println("Message class not found");
            c.printStackTrace();
            return "";
        }
        return "";
    }

    public static Message getMessageObject(String path1, int message_id){
        try {
            FileInputStream fileIn = new FileInputStream(path1);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = null;
            try {
                while ((obj = in.readObject()) != null) {
                    if (obj instanceof Message) {
                        if(((Message) obj).getMessage_id()==message_id){
                            return (Message) obj;
                        }
                        //System.out.println(((Message) obj).toString());
                    }
                }
            } catch (EOFException e) {
                System.out.println("no messages to be forwarded in file\n");
            }
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("message not found");
        return null;
    }


    public static void createFile(String path1){
        try {
            File fileIn = new File(path1);
            fileIn.createNewFile();

        }catch (IOException fnfe){
            System.out.println("Exception in selectOrCreateFile");
        }
    }

    public static String selectFile(int user_id, int receiver_id){

        String filename_path1=String.format("surse/_%s_%s_chat.ser",user_id,receiver_id);
        String filename_path2=String.format("surse/_%s_%s_chat.ser",receiver_id,user_id);

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
        System.out.println("--------------------------------------------------------------------------------");
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
                System.out.println("--------------------------------------------------------------------------------");
                System.out.println("reached end of file\n");
            }
            in.close();
            fileIn.close();
        }catch(EOFException ef){
            System.out.println("nothing in file\n");
        }catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Message class not found");
            c.printStackTrace();
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
    }
}
