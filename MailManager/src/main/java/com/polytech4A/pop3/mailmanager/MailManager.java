package com.polytech4A.pop3.mailmanager;

import com.polytech4A.pop3.mailmanager.Exceptions.MailManagerException;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Dimitri on 03/03/2015.
 */
public abstract class MailManager {

    /**
     * Path to the MailManager's directory
     */
    protected String path;

    /**
     * List of users of the MailManager
     */
    protected ArrayList<User> users;

    /**
     * Initialize the MailManager's directory
     */
    protected void initDirectory(){
        File userFolder = new File(path),
                userMailFolder = new File(path+"Mails/"),
                userLoginsFile = new File(path+"Mails/logins.txt");

        if (!userFolder.exists()) {
            try{
                userFolder.mkdir();
                userMailFolder.mkdir();
                userLoginsFile.createNewFile();
            } catch(SecurityException se){
                MailManagerException ex = new MailManagerException("MailManager.initDirectory : Could not create folders at "+path);
            } catch (IOException e) {
                MailManagerException ex = new MailManagerException("MailManager.initDirectory : Could not create logins.txt at "+path+"Mails/");
            }
        }
        path+="Mails/";
    }

    /**
     * Constructor of the MailManager
     */
    protected MailManager (){
        users = new ArrayList<User>();
        path = "";
    }

    /**
     * Initialize a MailManager's user
     * @param login : String of the user's login
     * @param password : String of the user's password
     * @return true if the User can be initialized
     */
    protected abstract boolean initUser (String login, String password);

    /**
     * Check if a MailManager's user is locked
     * @param user
     * @return true if the user is locked
     */
    public boolean isLockedUser(User user){
        int index = users.indexOf(user);
        if (index>=0&&!users.get(index).isLocked()){
            return true;
        }
        return false;
    }

    /**
     * Unlock a MailManager's user if possible
     * @param user
     */
    public void unlockUser(User user){
        int index = users.indexOf(user);
        if (index>=0&&users.get(index).isLocked()){
            users.get(index).unlockUser();
        }
    }

    /**
     * Get the list of Users in a directory
     * @return list of Users
     */
    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<User>();
        try{
            InputStream ips=new FileInputStream(path+"logins.txt");
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String line;
            String[] identification;

            while ((line=br.readLine())!=null){
                identification=line.split(" ");
                users.add(new User(identification[0],identification[1], path));
            }
            br.close();
        }
        catch (IOException e) {
            MailManagerException ex = new MailManagerException("User.getUser : Can't open file : "+path+"logins.txt");
            System.out.println(ex.getMessage());
        }
        return users;
    }
}
