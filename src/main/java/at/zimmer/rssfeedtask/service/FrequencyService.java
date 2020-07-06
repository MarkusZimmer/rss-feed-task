package at.zimmer.rssfeedtask.service;

import at.zimmer.rssfeedtask.model.RssAnalytics;
import at.zimmer.rssfeedtask.model.Word;
import at.zimmer.rssfeedtask.repository.RssAnalyticsRepository;
import at.zimmer.rssfeedtask.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FrequencyService {

    private final RssAnalyticsRepository rssAnalyticsRepository;
    private final WordRepository wordRepository;

    @Autowired
    public FrequencyService(RssAnalyticsRepository rssAnalyticsRepository, WordRepository wordRepository) {
        this.rssAnalyticsRepository = rssAnalyticsRepository;
        this.wordRepository = wordRepository;
    }

    @Transactional
    public List<Word> getTopThreeKeywords(Long id) {
        Optional<RssAnalytics> rssAnalytics = rssAnalyticsRepository.findById(id);
        if(rssAnalytics.isPresent()) {
            return wordRepository.findTop3ByRssAnalyticsOrderByAmountDesc(rssAnalytics.get());
        } else {
            throw new IllegalArgumentException("Unable to find RSS Analytics Report with the given ID.");
        }
    }
}
