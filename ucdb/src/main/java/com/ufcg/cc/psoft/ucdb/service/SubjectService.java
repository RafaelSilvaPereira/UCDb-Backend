package com.ufcg.cc.psoft.ucdb.service;


import com.ufcg.cc.psoft.ucdb.dao.StudentDAO;
import com.ufcg.cc.psoft.ucdb.dao.SubjectDAO;
import com.ufcg.cc.psoft.ucdb.model.Comment;
import com.ufcg.cc.psoft.ucdb.model.Student;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.view.CommentView;
import com.ufcg.cc.psoft.ucdb.view.GenericSubjectProfile;
import com.ufcg.cc.psoft.ucdb.view.SubjectProfile;
import com.ufcg.cc.psoft.util.Util;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class SubjectService {

    private final SubjectDAO subjectDAO;

    @Autowired
    private final StudentDAO studentDAO;

    private final Util util;

    public SubjectService(SubjectDAO subjectDAO, StudentDAO studentDAO) {
        this.subjectDAO = subjectDAO;
        this.studentDAO = studentDAO;
        this.util = new Util();
    }

    public Subject create(Subject subject) { return subjectDAO.save(subject);}

    public void delete(long id) { subjectDAO.delete(id);}

    public GenericSubjectProfile findById(long id) {
        Subject superficialClone = subjectDAO.findById(id).superficialClone();
        return getSubjectProfile(superficialClone);
    }

    public List<GenericSubjectProfile> findAll(String sorterStrategy) {
        List<Subject> subjectDAOAll = subjectDAO.findAll();
        List<@NotNull SubjectProfile> collect = new ArrayList<>();
        for (Subject subject : subjectDAOAll) {
            SubjectProfile subjectProfile = getSubjectProfile(subject.superficialClone());
            collect.add(subjectProfile);
        }
        return getAllSubjectProfile(sorterStrategy, collect);

    }

    private List<GenericSubjectProfile> getAllSubjectProfile(String sorterStrategy, List<SubjectProfile> collect) {
        List answer = null;
        List<SubjectProfile> list;
        switch (sorterStrategy) {
            case "likes":
                list = sortByLikes(collect);
                break;
            case "dislikes":
                list = sortByDislikes(collect);
                break;
            case "comments":
                list = sorterByComments(collect);
                break;
            case "proportion":
                list = sortByProportion(collect);
                break;
            default:
                list = collect;
        }
        answer = list.stream().map(this::modifyRepresentation).collect(Collectors.toList());
        return answer;
    }

    private GenericSubjectProfile modifyRepresentation(SubjectProfile subjectProfile) {
        return subjectProfile.toGenericRepresentation();
    }

    private List sortByProportion(List<SubjectProfile> list) {

        Stream<@NotNull SubjectProfile> sorted = list.stream()
                .sorted(((SubjectProfile o1, SubjectProfile o2) -> (int) Math.round(o2.getProportion() - o1.getProportion())));
        List<@NotNull SubjectProfile> profiles = sorted.collect(Collectors.toList());
        return profiles;
    }

    private List sorterByComments(List<SubjectProfile> collect) {
        return collect.stream().sorted(((o1, o2) -> o2.getComments().size() - o1.getComments().size())).collect(Collectors.toList());
    }

    private List sortByDislikes(List<SubjectProfile> collect) {
        return collect.stream().sorted(((o1, o2) -> o2.getDislikes() - o1.getDislikes())).collect(Collectors.toList());
    }

    private List sortByLikes(List<SubjectProfile> collect) {
        return collect.stream().sorted(((o1, o2) -> o2.getLikes() - o1.getLikes())).collect(Collectors.toList());
    }

    public List findBySubstring(String substring) {

        List<Subject> subjectDAOSubstring = subjectDAO.
                findBySubstring(this.util.reconvertValidUrlToOriginalString(substring));
        final Stream<Subject> stream = subjectDAOSubstring.stream();
        return stream.map(this::getGenericSubjectProfile)
                .collect(Collectors.toList());
    }

    public void saveAllSubjects() throws IOException, ParseException {
        JSONArray jsonArray;
        JSONParser jsonParser = new JSONParser();
        FileReader archive = new FileReader("src/main/java/com/ufcg/cc/psoft/util/disciplina.json");

        jsonArray = (JSONArray) jsonParser.parse(archive);
        jsonArray.stream().forEach(object -> {
            JSONObject jsonobjectvalue = (JSONObject) object;
            String name = (String) jsonobjectvalue.get("nome");
            this.create(new Subject(name));
        });
    }

//    public void deleteAll() { subjectDAO.deleteAll();}

    public void like(String token, long id) {
        Student student = this.util.getStudent(token, studentDAO);
        Subject subject = this.subjectDAO.findById(id);


        student.getEnjoiyed().add(subject);
        subject.getStudentLiked().add(student);
        undislike(student, subject);
        updateDataBase(student, subject);

    }

    public void dislike(String token, long id) {
        Student student = this.util.getStudent(token, studentDAO);
        Subject subject = this.subjectDAO.findById(id);


        subject.getStudentDisliked().add(student);
        student.getDisliked().add(subject);

        unlike(student, subject);

        updateDataBase(student, subject);

    }

    public void unlike(String token, long id) {
        Student student = this.util.getStudent(token, studentDAO);
        Subject subject = this.subjectDAO.findById(id);

        unlike(student, subject);

        updateDataBase(student, subject);
    }



    public void undislike(String token, long id) {
        Student student = this.util.getStudent(token, studentDAO);
        Subject subject = this.subjectDAO.findById(id);

        undislike(student, subject);
        updateDataBase(student, subject);
    }

    private void undislike(Student student, Subject subject) {
        subject.getStudentDisliked().remove(student);
        student.getDisliked().remove(subject);
    }

    private void unlike(Student student, Subject subject) {
        subject.getStudentLiked().remove(student);
        student.getEnjoiyed().remove(subject);
    }

    private void updateDataBase(Student student, Subject subject) {
        studentDAO.save(student); // update student
        subjectDAO.save(subject);
    }


    private Subject getSubject(JSONObject request, String value) {
        String subjectId = (String) request.get(value);
        final long id = Long.parseLong(subjectId);
        return subjectDAO.findById(id);
    }

    @NotNull
    private SubjectProfile getSubjectProfile(Subject superficialClone) {
        final long cloneId = superficialClone.getId();
        final String cloneName = superficialClone.getName();
        final int cloneDislikes = superficialClone.getStudentLiked().size();
        final int cloneLikes = superficialClone.getStudentDisliked().size();

        final List<Comment> subjectComments = superficialClone.getSubjectComments();
        return new SubjectProfile(cloneId, cloneName, cloneLikes, cloneDislikes,
                setViewComments(subjectComments));
    }

    private GenericSubjectProfile getGenericSubjectProfile(Subject superficialClone) {
        final long cloneId = superficialClone.getId();
        final String cloneName = superficialClone.getName();
        return new GenericSubjectProfile(cloneId, cloneName);
    }

    private Collection<CommentView> setViewComments(Collection<Comment> subjectComments) {
        List<CommentView> list = new ArrayList<>();
        for (Comment c : subjectComments) {
            if (c.isVisble()) {
                CommentView commentView = new CommentView(c.getStudent().getFirstName(),
                        c.getStudent().getSecondName(),
                        c.getComment(),
                        setViewComments(c.getSubcomments()),
                        c.getId(),
                        c.getDate(),
                        c.getHour()
                );
                list.add(commentView);
            }
        }
        return list;
    }

}
