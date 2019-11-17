package com.destiny.chatapptutorialandroid.Model;

public class User {
    String uid;
    String email,password,name;

    public User(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "User("+
                "uid='"+uid+ '\''+
                ", name='"+name+'\''+
                ", email='"+email+'\''+
                ')';
    }
}
