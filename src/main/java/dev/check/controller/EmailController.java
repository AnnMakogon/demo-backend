package dev.check.controller;

import dev.check.dto.Newsletter;
import dev.check.dto.ParamForGet;
import dev.check.FindError500;
import dev.check.controller.errorHandler.NewsletterNullError;
import dev.check.manager.ManagerUtils;
import dev.check.service.NewsletterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FindError500
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class EmailController {

    private final NewsletterService emailService;

    @PostMapping(value = "newsletter", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> createNewsletter(@RequestBody Newsletter newsletter) throws NewsletterNullError {
        //todo почитай про валидацию @validated
        log.info("Received newsletter: " + newsletter);
        if (newsletter.getDate() == null || newsletter.getText() == null) {
            throw new NewsletterNullError();
        }
        return emailService.createNewsletter(newsletter);
    }

    @PutMapping(value = "newsletter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Newsletter changeNewsletter(@RequestBody Newsletter changingNewsletter) {
        log.info("Received newsletter: " + changingNewsletter);
        return emailService.changeNewsletter(changingNewsletter);
    }

    @PutMapping(value = "newsletterDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Newsletter changeDateNewsletter(@RequestBody Newsletter changingNewsletterDate) {
       return emailService.changeDateNewsletter(changingNewsletterDate);
    }

    @DeleteMapping(value = "newsletter/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteNewsletter(@PathVariable("id") Long id) {
       return emailService.deleteNewsletter(id);
    }

    @GetMapping(value = "newsletter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Newsletter> getNewsletterPagSortFilter(@ModelAttribute ParamForGet request) {
        return emailService.getNewsletter(request);
    }

}
