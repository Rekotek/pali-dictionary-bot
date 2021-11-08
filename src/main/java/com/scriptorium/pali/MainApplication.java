package com.scriptorium.pali;

import com.scriptorium.pali.config.ProfileResolver;
import com.scriptorium.pali.engine.SourceFileReader;
import com.scriptorium.pali.service.VocabularyService;
import org.slf4j.Logger;
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

import static org.slf4j.LoggerFactory.getLogger;

@SuppressWarnings("SpringJavaAutowiringInspection")
@SpringBootApplication
public class MainApplication implements CommandLineRunner {
    private static final Logger LOG = getLogger(MainApplication.class);
    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        ProfileResolver.build();
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        var loadedProfiles = new ArrayList<>(List.of(applicationContext.getEnvironment().getActiveProfiles()));
        for (var profile : loadedProfiles) {
            LOG.info("PROFILE: {}", profile);
        }
        populateDB();
    }

    public void populateDB() {
        VocabularyService vocabularyService;
        vocabularyService = applicationContext.getBean(VocabularyService.class);
        if (vocabularyService.isEmptyDatabase()) {
            LOG.info("Read and parse vocabulary");
            try {
                SourceFileReader.readDictionaryFile(vocabularyService::saveNewEntry);
            } catch (FileNotFoundException | InvalidObjectException e) {
                ((ConfigurableApplicationContext) applicationContext).close();
            }
        }
    }
}
