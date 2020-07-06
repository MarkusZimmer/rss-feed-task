package at.zimmer.rssfeedtask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "WORD")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RSS_ANALYTICS_ID")
    private RssAnalytics rssAnalytics;
    @Column(name = "keyword", nullable = false)
    private String keyword;
    @Column(name = "amount")
    private Integer amount;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "WORD_NEWS",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "news_id")
    )
    private List<News> newsEntries;

    public Word() {

    }

    public Word(String keyword, RssAnalytics rssAnalytics, List<News> newsEntries) {
        this.keyword = keyword;
        this.rssAnalytics = rssAnalytics;
        this.amount = newsEntries.size();
        this.newsEntries = newsEntries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RssAnalytics getRssAnalytics() {
        return rssAnalytics;
    }

    public void setRssAnalytics(RssAnalytics rssAnalytics) {
        this.rssAnalytics = rssAnalytics;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<News> getNewsEntries() {
        if(this.newsEntries == null) {
            this.newsEntries = new ArrayList<>();
        }
        return this.newsEntries;
    }

    public void setNewsEntries(List<News> newsEntries) {
        this.newsEntries = newsEntries;
    }
}
