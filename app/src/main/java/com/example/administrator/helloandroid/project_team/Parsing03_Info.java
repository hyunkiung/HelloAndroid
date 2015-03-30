
package com.example.administrator.helloandroid.project_team;

/**
 * Created by Administrator on 2015-03-30.
 */
public class Parsing03_Info {
    private String id;
    private String title;
    private String author;
    private String updated;


    public Parsing03_Info(String id, String title, String author, String updated) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("  {");
//        builder.append("    id:" + getId() + ",");
//        builder.append("    title:" + getTitle() + ",");
//        builder.append("    author:" + getAuthor() + "");
//        builder.append("  }");
//
//        return builder.toString();
//    }
}
