package dev.check.controller;

import dev.check.DTO.Newsletter;
import dev.check.DTO.ParamForGet;
import dev.check.FindError500;
import dev.check.controller.errorhandler.NewsletterNullError;
import dev.check.manager.SentOnTime.SentManagerOnTime;
import dev.check.service.NewsletterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@FindError500
@Slf4j
@RequestMapping("/api/mail")  //не влияет на webSocket
public class EmailController {

    @Autowired
    private NewsletterService emailService;

    @Autowired
    private SentManagerOnTime sentManagerOnTime;

    // создание
    @PostMapping(value = "newsletter", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> messNewsletter(@RequestBody Newsletter nl) throws NewsletterNullError {
        log.info("Received newsletter: " + nl);
        if (nl.getDate() == null || nl.getText() == null) {
            throw new NewsletterNullError();
        }
        //emailService.createNewsletter(nl);
        sentManagerOnTime.createNewsletter(nl);
        return ResponseEntity.ok().build();
    }

    //изменить все
    @PutMapping(value = "newsletter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Newsletter changeNl(@RequestBody Newsletter changingNl) {
        log.info("Received newsletter: " + changingNl);
        //return emailService.update(changingNl);
        return sentManagerOnTime.updateInQueue(changingNl);
    }

    //изменить только дату
    @PutMapping(value = "newsletterDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Newsletter changeDateNl(@RequestBody Newsletter changingNlId) {
        //return emailService.dateUpdate(changingNlId);
        return sentManagerOnTime.dateUpdate(changingNlId);
    }

    @DeleteMapping(value = "newsletter/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteNl(@PathVariable("id") Long id) {
        //return emailService.deleteNl(id);
        return sentManagerOnTime.deleteNl(id);
    }

    @GetMapping(value = "newsletter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Newsletter> getNlPagSortFilter(@ModelAttribute ParamForGet request) {
        return emailService.getNl(request.getFilter(), ControllerUtils.createPageable(request.getPage(), request.getSize(), request.getColumn(), request.getDirection()), request.isShowFlag());
    }

}
