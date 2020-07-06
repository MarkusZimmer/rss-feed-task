package at.zimmer.rssfeedtask.service;

import at.zimmer.rssfeedtask.model.News;
import at.zimmer.rssfeedtask.model.RssAnalytics;
import at.zimmer.rssfeedtask.model.Word;
import at.zimmer.rssfeedtask.repository.NewsRepository;
import at.zimmer.rssfeedtask.repository.RssAnalyticsRepository;
import at.zimmer.rssfeedtask.repository.WordRepository;
import com.google.common.collect.Lists;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyzeService {

    private final RssAnalyticsRepository rssAnalyticsRepository;
    private final NewsRepository newsRepository;
    private final WordRepository wordRepository;
    private static List<String> stopwords = Arrays.asList("a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero", "news", "full", "coverage", "view");


    @Autowired
    public AnalyzeService(RssAnalyticsRepository rssAnalyticsRepository, NewsRepository newsRepository, WordRepository wordRepository) {
        this.rssAnalyticsRepository = rssAnalyticsRepository;
        this.newsRepository = newsRepository;
        this.wordRepository = wordRepository;
    }

    @Transactional
    public long analyze(List<String> rssFeeds) {
        try {
            RssAnalytics rssAnalytics = new RssAnalytics();
            List<News> convertedNews = new ArrayList<>();
            for (String rssFeed : rssFeeds) {
                convertedNews.addAll(convertFeed(rssFeed));
            }
            rssAnalytics = rssAnalyticsRepository.save(rssAnalytics);
            generateWordAnalyze(convertedNews, rssAnalytics);
            return rssAnalytics.getId();
        } catch (FeedException | IOException feedException) {
            throw new IllegalArgumentException("Unable to read RSS Feed.");
        }

    }

    private List<News> convertFeed(String rssFeedUrl) throws IOException, FeedException {
        SyndFeedInput syndFeedInput = new SyndFeedInput();
        URL url = new URL(rssFeedUrl);
        SyndFeed feed = syndFeedInput.build(new XmlReader(url));
        return feed.getEntries().stream().map(this::convertNews).collect(Collectors.toList());
    }

    private News convertNews(SyndEntry entry) {
        String sanitizedDescription = Jsoup.parse(entry.getDescription().getValue()).text();
        News news = new News(entry.getTitle(), entry.getLink(), sanitizedDescription);
        return newsRepository.save(news);
    }

    private List<Word> generateWordAnalyze(List<News> convertedNews, RssAnalytics rssAnalytics) {
        HashMap<String, List<News>> analytics = new HashMap<>();
        convertedNews.forEach(news -> {
            List<String> splittedDescription = Arrays.stream(news.getDescription().toLowerCase().split(" ")).collect(Collectors.toList());
            splittedDescription.removeAll(stopwords);
            splittedDescription.forEach(splittedWord -> {
                if(analytics.containsKey(splittedWord)) {
                    analytics.get(splittedWord).add(news);
                } else {
                    analytics.put(splittedWord, Lists.newArrayList(news));
                }
            });
        });

        return analytics.entrySet().stream().map(entry -> wordRepository.save(new Word(entry.getKey(), rssAnalytics, entry.getValue()))).collect(Collectors.toList());
    }
}
