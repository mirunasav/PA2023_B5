package org.example.homework;

import org.example.compulsory.Project;
import org.example.Student;

import java.util.List;

public class Problem {
    List <org.example.Student> studentList;
    List <org.example.compulsory.Project> projectList;

    public Problem(List<Student> studentList, List<Project> projectList) {
        this.studentList = studentList;
        this.projectList = projectList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("""
                Problem:\s
                 studentii sunt :\s
                """);
        for (Student student : studentList)
            string.append(student.getName()).append("\n ");
        string.append("Proiectele sunt:\n ");
        projectList.forEach(project -> string.append(project.getName()).append("\n"));
        return string.toString();
    }
}
