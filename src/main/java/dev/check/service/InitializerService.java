package dev.check.service;

import dev.check.service.InitializerAnyServices.InitialNewsletterWithAddress;
import dev.check.service.InitializerAnyServices.InitializerStudentsUsers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitializerService {

    private final InitializerStudentsUsers initializerStudentsUsers;
    private final InitialNewsletterWithAddress initialNewsletterWithAddress;

    @Transactional
    public void initial() {

        initializerStudentsUsers.initialStudentsUsers();

        initialNewsletterWithAddress.initialNewsletterWithAddress();

        initializerStudentsUsers.initialAdmin();

        log.info("DB initialization completed");
    }

}
