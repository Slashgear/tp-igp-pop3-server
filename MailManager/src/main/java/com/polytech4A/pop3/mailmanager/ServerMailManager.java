package com.polytech4A.pop3.mailmanager;

import java.util.ArrayList;

/**
 * Created by Dimitri on 07/03/2015.
 */
public class ServerMailManager extends MailManager{

    /**
     * Constructor of ServerMailManager
     */
    public ServerMailManager() {
        super();
        path ="Server/";
        initDirectory();
        users =getUsers();
    }

    @Override
    public boolean initUser(String login, String password){
        User user = new User(login,password,path);
        int index = users.indexOf(user);
        if (index>=0&&!users.get(index).isLocked()){
            user.lockUser();
            user.initMails();
            return true;
        }
        return false;
    }

    /**
     * Get the list of mails of a ServerMailManager's user
     * @param user : User
     * @return List of mails
     */
    public ArrayList<Mail> getMails (User user){
        int index = users.indexOf(user);
        if (index>=0&&users.get(index).isLocked()){

            ArrayList<Mail> mails = users.get(index).getMails();
            for (Mail mail : users.get(index).getMails()){
                users.get(index).deleteMail(mail);
            }
        }
        return null;
    }

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
}
