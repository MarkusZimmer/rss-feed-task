package at.zimmer.rssfeedtask.controller;

import at.zimmer.rssfeedtask.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analyze")
public class AnalyzeController {

    private final AnalyzeService analyzeService;

    @Autowired
    public AnalyzeController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public long analyze(@RequestParam(value = "rssFeed") List<String> rssFeeds) {
        return analyzeService.analyze(rssFeeds);
    }
}
