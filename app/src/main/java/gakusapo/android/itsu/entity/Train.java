package gakusapo.android.itsu.entity;

public class Train {

    private String company;
    private String name;

    public Train() {
        this(null, null);
    }

    public Train(String company, String name) {
        this.company = company;
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
