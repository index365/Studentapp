package cn.itcast.studentapp;
public class Student {
    private Long id;
    private String name;
    private String studentNumber;
    private String phone;
    private String major;

    // Getter和Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public Student() {}
    public Student(String name, String studentNumber, String phone, String major) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.phone = phone;
        this.major = major;
        this.id = null;
    }
}