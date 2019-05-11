package dev.itsu.gakusapo.entity;

public class Train {

    private String url;
    private String name;

    public Train() {
        this(null, null);
    }

    public Train(String name, String url) {
        this.url = url;
        this.name = name;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
