public class Author {
    private String name;
    private String nationality;
    private int yearOfBirth;

    public Author(String pName, String pNationality, int pYearOfBirth) {
        name = pName;
        nationality = pNationality;
        yearOfBirth = pYearOfBirth;
    }

    /* Getters */
    public String getName() { return name; }
    public String getNationality() { return nationality; }
    public int getYearOfBirth() { return yearOfBirth; }

    /* Setters */
    public void setName(String pName) { name = pName; }
    public void setNationality(String pNationality) { nationality = pNationality; }
    public void setYearOfBirth(int pYearOfBirth) { yearOfBirth = pYearOfBirth; }

    /* Equals to */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Author)) return false;
        Author inAuthor = (Author) obj;
        return name.equals(inAuthor.getName()) &&
               nationality.equals(inAuthor.getNationality()) &&
               yearOfBirth == inAuthor.getYearOfBirth();
    }

    /* To String */
    public String toString() {
        return name + " (" + nationality + ", " + yearOfBirth + ")";
    }
}
