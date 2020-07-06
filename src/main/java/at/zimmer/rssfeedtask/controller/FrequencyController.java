package at.zimmer.rssfeedtask.controller;

import at.zimmer.rssfeedtask.model.Word;
import at.zimmer.rssfeedtask.service.FrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frequency")
public class FrequencyController {

    private final FrequencyService frequencyService;

    @Autowired
    public FrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getTopThreeKeywords(@PathVariable Long id) {
        try {
            List<Word> topThreeKeywords = frequencyService.getTopThreeKeywords(id);
            return new ResponseEntity<>(topThreeKeywords, HttpStatus.OK);
        } catch (IllegalArgumentException illegalArgumentException) {
            return new ResponseEntity(illegalArgumentException.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
