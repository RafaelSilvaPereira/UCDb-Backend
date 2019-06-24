package com.ufcg.cc.psoft.exceptions;

import javax.servlet.ServletException;

public class NotCorrespondingUserLogin extends ServletException {
    public NotCorrespondingUserLogin(String user_not_found) {
        super(user_not_found);
    }
}
