package com.ufcg.cc.psoft.exceptions;

import javax.servlet.ServletException;

public class NotCorrespondingStudentLogin extends ServletException {
    public NotCorrespondingStudentLogin(String user_not_found) {
        super(user_not_found);
    }
}
