package at.zimmer.rssfeedtask.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="RSS_ANALYTICS")
public class RssAnalytics {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private List<Word> words;

    public RssAnalytics() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Word> getWords() {
        if(this.words == null) {
            this.words = new ArrayList<>();
        }
        return this.words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
