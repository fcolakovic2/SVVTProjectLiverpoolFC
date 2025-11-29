package utils;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataGenerator {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+<>?";
    private static final String ALL = UPPER + LOWER + DIGITS + SPECIAL;

    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Password length should be at least 8 characters"); // according to my website that I am using for this project
        }

        StringBuilder password = new StringBuilder(length);

        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        for (int i = 4; i < length; i++) {
            password.append(ALL.charAt(random.nextInt(ALL.length())));
        }

        return password.toString();
    }

    private static final String[] GENDERS = {"Male", "Female", "Prefer not to say"};

    // Random Gender
    public static String generateGender() {
        int index = ThreadLocalRandom.current().nextInt(GENDERS.length);
        return GENDERS[index];
    }

    // Random Birthdate
    public static String generateBirthdate(int minAge, int maxAge) {
        // Today
        LocalDate now = LocalDate.now();

        // Calculate earliest and latest birthdate
        LocalDate earliest = now.minusYears(maxAge);
        LocalDate latest = now.minusYears(minAge);

        // Generate random epoch day between earliest and latest
        long randomEpochDay = ThreadLocalRandom.current().nextLong(earliest.toEpochDay(), latest.toEpochDay());

        LocalDate birthdate = LocalDate.ofEpochDay(randomEpochDay);

        // Format as "dd/MM/yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return birthdate.format(formatter);
    }


    public static String generateEmail(String domain) {
        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return randomString + "@" + domain + ".com";
    }
}
