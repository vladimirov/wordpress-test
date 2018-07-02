package model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


//@XStreamAlias("group")
@Entity
@Table(name = "wp_posts")
public class PostData {

    @XStreamOmitField
    @Id
    @Column(name = "id")
    private int id;

    @Expose
    @Column(name = "post_title")
    @Type(type = "text")
    private String title;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public PostData withId(int id) {
        this.id = id;
        return this;
    }

    public PostData withTitle(String name) {
        this.title = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostData postData = (PostData) o;
        return id == postData.id &&
                Objects.equals(title, postData.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "PostData{" +

                "id='" + id + '\'' +
                ", name='" + title + '\'' +
                '}';

    }


}
