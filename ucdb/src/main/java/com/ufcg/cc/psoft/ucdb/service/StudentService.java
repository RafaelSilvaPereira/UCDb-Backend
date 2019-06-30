package com.ufcg.cc.psoft.ucdb.service;


import com.ufcg.cc.psoft.ucdb.dao.StudentDAO;
import com.ufcg.cc.psoft.ucdb.model.Student;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.view.StudentView;
import com.ufcg.cc.psoft.util.Util;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentDAO studentDAO;


    private final Util util;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
        this.util = new Util();
    }

    public StudentView create(Student student) {
        String email = student.getEmail();
        if (this.studentDAO.studentFindByEmail(email) != null){
            return null;
        } else {
            Student copy = studentDAO.save(student);
            return new StudentView(copy.getEmail(),copy.getFirstName(),copy.getSecondName());
        }

    }

    public StudentView findByLogin(String email, String password) {
        Student copy = studentDAO.findByLogin(email,password);
        return new StudentView(copy.getEmail(),copy.getFirstName(),copy.getSecondName());
    }

    public Boolean enjoyed(String token, long subjectID) {
        boolean answer = false;
        if (subjectID != 0) {
            final Student student = this.util.getStudent(token, this.studentDAO);
            for (Subject s : student.getEnjoiyed()) {
                if (s.getId() == subjectID) {
                    answer = true;
                    break;
                } else {
                    answer = false;
                }

            }
        }
        return answer;
    }

    public Boolean disliked(String token, long subjectID) {
        boolean answer = false;
        if (subjectID != 0) {
            final Student student = this.util.getStudent(token, this.studentDAO);
            for (Subject s : student.getDisliked()) {
                if (s.getId() == subjectID) {
                    answer = true;
                    break;
                } else {
                    answer = false;
                }

            }
        }
        return answer;
    }
    // TODO: Um dos findById não está funcionando corretamente  DAI EU TIVE QUE COMENTAR PARA TESTAR O RESTO

    public StudentView findStundent(String token) {
        Student find = this.util.getStudent(token, this.studentDAO);
        StudentView student = new StudentView(find.getEmail(),find.getFirstName(),find.getSecondName());
        return student;
    }
 }
