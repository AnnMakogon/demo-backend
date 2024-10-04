package dev.check.service;

import dev.check.entity.*;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.EnumEntity.*;
import dev.check.repositories.DepartmentRepository;
import dev.check.repositories.NewsletterRepository;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import dev.check.service.InitializerAnyServices.InitialAdmin;
import dev.check.service.InitializerAnyServices.InitialNewsletterWithAddress;
import dev.check.service.InitializerAnyServices.InitializerStudentsUsers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitializerService {

    private final InitializerStudentsUsers initializerStudentsUsers;
    private final InitialNewsletterWithAddress initialNewsletterWithAddress;
    private final InitialAdmin initialAdmin;

    @Transactional
    public void initial() {

        initializerStudentsUsers.initialStudentsUsers();

        initialNewsletterWithAddress.initialNewsletterWithAddress();

        initialAdmin.initialAdmin();

        log.info("DB initialization completed");
    }




}
