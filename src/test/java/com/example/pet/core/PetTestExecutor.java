package com.example.pet.core;

import com.example.pet.test.PetstorePetCreateTest;
import com.example.pet.test.PetstorePetDeleteTest;
import com.example.pet.test.PetstorePetOrderTest;
import com.example.pet.test.PetstorePetRecordSanityTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.util.*;
import java.util.stream.Collectors;

public class PetTestExecutor {

    public static final String TAGS = "-tags=";
    public static final String URL = "--url=";
    private static String urlParam;
    private static Set<String> tags = new HashSet<>();

    public static void main(String... args) {

        if (extractParams(args)) {
            configureUrlFactory(urlParam);

            List<Class<?>> registeredTestClasses = getTestClasses();

            Map<String, Map<Class<?>, Result>> results = executeTestsWithTag(registeredTestClasses, tags);

            printResults(results, tags);
        } else {
            System.out.println("Usage: \nMandatory parameter: -tags=<list of tags delimited by comma (no spaces)>\n" +
                    "Optional parameter: --url=<valid url ends with />\n" +
                    "For example: -tags=Tag1,Tag2,Tag3 --url=example.com/customurl/");
        }

    }

    private static boolean extractParams(String... args) {
        if (args.length == 0 || args.length > 2) return false;
        try {
            tags.addAll(extractTags(args[0]));

            if (args.length == 2) {
                urlParam = extractUrl(args[1]);
            }

            return true;
        } catch (Exception e) {
            // log if necessary
        }

        return false;
    }

    private static String extractUrl(String rawString) {
        return rawString.substring(rawString.indexOf(URL));
    }

    private static Set<String> extractTags(String rawString) {
        return Arrays.stream(rawString.substring(rawString.indexOf(TAGS) + TAGS.length()).split(",")).collect(Collectors.toSet());
    }

    private static void configureUrlFactory(String url) {
        if (url != null && !url.isEmpty()) {
            ServiceLocator.setBaseUrlFactory(new CustomBaseUrlFactory(url));
        } else {
            ServiceLocator.setBaseUrlFactory(new DefaultBaseUrlFactory());
        }
    }

    private static List<Class<?>> getTestClasses() {
        List<Class<?>> classes = new LinkedList<>();
        // maybe scan for tests instead
        classes.add(PetstorePetCreateTest.class);
        classes.add(PetstorePetDeleteTest.class);
        classes.add(PetstorePetOrderTest.class);
        classes.add(PetstorePetRecordSanityTest.class);

        return classes;
    }

    private static Map<String, Map<Class<?>, Result>> executeTestsWithTag(List<Class<?>> testClasses, Set<String> tags) {
        Map<String, Map<Class<?>, Result>> results = new TreeMap<>();

        for (Class<?> testClass : testClasses) {
            String tag = testClass.getAnnotation(TestTag.class).tag();

            if (tags.contains(tag)) {
                Result result = JUnitCore.runClasses(testClass);
                results.compute(tag, (k, v) -> {
                    if (v == null) {
                        v = new HashMap<>();
                        v.put(testClass, result);
                    } else {
                        v.putIfAbsent(testClass, result);
                    }
                    return v;
                });
            }
        }

        return results;
    }

    private static void printResults(Map<String, Map<Class<?>, Result>> results, Set<String> tags) {
        System.out.println("Executing tests with tags: " + tags);
        System.out.println("-----------------");
        if (results.size() == 0) {
            System.out.println("No tests found with the given tags");
        }

        results.keySet().forEach(k -> {
            System.out.println("Tests for " + k + ":");
            results.get(k).forEach((c, r) -> System.out.println(c.getSimpleName() + ": " + (r.wasSuccessful() ? "OK" : "Failed")));
        });

    }
}
