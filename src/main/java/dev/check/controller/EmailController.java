package dev.check.controller;

import dev.check.DTO.NewsletterDTO;
import dev.check.FindError500;
import dev.check.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@FindError500
@RequestMapping("/api/mail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // создание и отправка
    @MessageMapping("/newsletter")
    @SendTo("/topic/messNl")
    @PostMapping(value = "newsletter", produces = MediaType.APPLICATION_JSON_VALUE)  //отправка
    public NewsletterDTO messNewsletter(@RequestBody NewsletterDTO nl) {
        //emailService.setDateNl(nl);     // здесь сначала получение nl
        emailService.messNewsletter(nl);                         //потом здесь отправка через шедуйлер
        return nl;
    }

    @PutMapping(value = "newsletter", produces = MediaType.APPLICATION_JSON_VALUE)
    public NewsletterDTO changeNl(@RequestBody NewsletterDTO changingNl){
        return emailService.update(changingNl);
    }
    @PutMapping(value = "newsletterDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public NewsletterDTO changeDateNl(@RequestBody NewsletterDTO changingNlId){
        return emailService.dataUpdate(changingNlId);
    }

    @DeleteMapping(value = "newsletter/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteNl(@PathVariable("id") Long id){
        return emailService.deleteNl(id);
    }

    @GetMapping(value = "newsletter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NewsletterDTO> getNlPagSortFilter(@RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size,
                                                  @RequestParam(name = "column") String column,
                                                  @RequestParam(name = "direction") String direction,
                                                  @RequestParam(name = "filter") String filter,
                                                  @RequestParam(name = "showflag") boolean showflag) {
        Sort sort = Objects.equals(direction, "") ? Sort.by("date") : Sort.by(Sort.Direction.fromString(direction), column);//если не указано направление, то сортируем по fio
        Pageable pageable = PageRequest.of(page, size, sort);
        return emailService.getNl(filter, pageable, showflag);
    }

    @GetMapping("length")
    public Number getFullLength(@RequestParam(name = "filter") String filter) {
        return emailService.getLengthStudents(filter);
    }

}
