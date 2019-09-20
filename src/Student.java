public class Student {
    private String name;
    private String degree;
    private int year;

    public Student(String name, String degree, int year) {
        this.name = name;
        this.degree = degree;
        this.year = year;
    }

    public Student() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
