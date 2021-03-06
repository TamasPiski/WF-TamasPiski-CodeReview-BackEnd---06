package sample;

public class Teacher {

    public int id;
    public String lastName;
    public String firstName;
    public String email;
    public DatabaseConnectable db;

    public Teacher(int id, String lastName, String firstName, String email, DatabaseConnectable db) {
        this.setId(id);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setEmail(email);
        this.db = db;
    }

    public void lofasz() {
        db.runQuery("");
    }

    @Override
    public String toString(){
        return this.lastName + " " + this.firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
