package com.cg.service;

class Student {
    String studentId;
    String studentName;

    Student(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public boolean equals(Object s) {
        if (this == s) return true;

        if (s == null || getClass() != s.getClass())
            return false;

        Student other = (Student) s;

        return studentId.equals(other.studentId) &&
               studentName.equals(other.studentName);
    }
}
public class StudentMain {
	public static void main(String[] args) {
        Student s1 = new Student("sameer123", "siddiqui");
        Student s2 = new Student("sameer123", "siddiqui");

        System.out.println("Using == : " + (s1 == s2));
        System.out.println("Using equals op : " + s1.equals(s2));
    }

}