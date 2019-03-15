package hr.pishe.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GoogleSearchItemDTO implements Serializable {
    @JsonProperty("htmlTitle")
    private String title;

    @JsonProperty("htmlSnippet")
    private String snippet;

    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "GoogleSearchItemDTO{" +
            "title='" + title + '\'' +
            ", snippet='" + snippet + '\'' +
            ", link='" + link + '\'' +
            '}';
    }
}
