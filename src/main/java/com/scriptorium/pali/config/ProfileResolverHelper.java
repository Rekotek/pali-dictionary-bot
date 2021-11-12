package com.scriptorium.pali.config;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ProfileResolverHelper {
    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";
    private static final String DB_H2 = "local";
    private static final String DB_POSTGRES = "postgres";

    private ProfileResolverHelper() { }

    //  Get DB profile depending on DB driver in classpath
    private static String getActiveDbProfile() {
        try {
            Class.forName("org.h2.Driver");
            return DB_H2;
        } catch (ClassNotFoundException ex) {
            try {
                Class.forName("org.postgresql.Driver");
                return DB_POSTGRES;
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Could not find DB driver");
            }
        }
    }

    public static void generate() {
        String activeDbProfile = ProfileResolverHelper.getActiveDbProfile();
        log.info("Found driver for profile:{}", activeDbProfile);
        String envProperty = System.getProperty(SPRING_PROFILES_ACTIVE);
        String springProfileProperty = Objects.requireNonNullElse(envProperty, "");
        if(!springProfileProperty.contains(activeDbProfile)) {
            if(!springProfileProperty.isBlank()) {
                springProfileProperty = String.join(",", springProfileProperty, activeDbProfile);
            } else {
                springProfileProperty = activeDbProfile;
            }
        }
        System.setProperty(SPRING_PROFILES_ACTIVE, springProfileProperty);
        log.info("Store {} as spring active profile(s)", springProfileProperty);
    }
}