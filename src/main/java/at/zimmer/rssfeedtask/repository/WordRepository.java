package at.zimmer.rssfeedtask.repository;

import at.zimmer.rssfeedtask.model.RssAnalytics;
import at.zimmer.rssfeedtask.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findTop3ByRssAnalyticsOrderByAmountDesc(RssAnalytics rssAnalytics);
}
