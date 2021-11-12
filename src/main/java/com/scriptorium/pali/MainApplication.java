package com.scriptorium.pali;

import com.scriptorium.pali.config.ProfileResolverHelper;
import com.scriptorium.pali.engine.DocumentFileHelper;
import com.scriptorium.pali.service.VocabularyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileNotFoundException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Slf4j
@SpringBootApplication
public class MainApplication implements CommandLineRunner {
    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        ProfileResolverHelper.generate();
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        var loadedProfiles = new ArrayList<>(List.of(applicationContext.getEnvironment().getActiveProfiles()));
        for (var profile : loadedProfiles) {
            log.info("PROFILE: {}", profile);
        }
        populateDB();
    }

    public void populateDB() {
        VocabularyService vocabularyService;
        vocabularyService = applicationContext.getBean(VocabularyService.class);
        if (vocabularyService.isEmptyDatabase()) {
            log.info("Read and parse vocabulary");
            try {
                DocumentFileHelper.readDictionaryFile(vocabularyService::saveNewEntry);
            } catch (FileNotFoundException | InvalidObjectException e) {
                ((ConfigurableApplicationContext) applicationContext).close();
            }
        }
    }
}
