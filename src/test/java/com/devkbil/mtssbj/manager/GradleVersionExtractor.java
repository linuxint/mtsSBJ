package com.devkbil.mtssbj.manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradleVersionExtractor {

    private static final Pattern DEPENDENCY_PATTERN = Pattern.compile("['\"]([^'\"]+:[^'\"]+:[^'\"]+)['\"]");

    @Test
    public void main() {
        String filePath = "build.gradle";

        try {
            List<String> dependenciesWithVersions = extractDependenciesWithVersions(filePath);
            List<String> updatedDependencies = new ArrayList<>();

            for (String dependency : dependenciesWithVersions) {
                String latestVersion = findLatestVersion(dependency);
                if (latestVersion != null && !latestVersion.equals(getVersion(dependency))) {
                    String updatedDependency = replaceVersion(dependency, latestVersion);
                    updatedDependencies.add(updatedDependency);
                } else {
                    updatedDependencies.add(dependency);
                }
            }

            List<String> updatedContent = updateDependenciesInFile(filePath, updatedDependencies);
            //writeUpdatedFile(filePath, updatedContent);

            // 모든 내용을 콘솔에 출력
            updatedContent.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error reading the gradle file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static List<String> extractDependenciesWithVersions(String filePath) throws IOException {
        List<String> dependenciesWithVersions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = DEPENDENCY_PATTERN.matcher(line);
                if (matcher.find()) {
                    dependenciesWithVersions.add(matcher.group(1));
                }
            }
        }
        return dependenciesWithVersions;
    }

    private static String findLatestVersion(String dependency) {
        String[] parts = dependency.split(":");
        if (parts.length < 3) {
            return null; // invalid dependency format
        }
        String groupId = parts[0];
        String artifactId = parts[1];

        try {
            String encodedGroupId = URLEncoder.encode(groupId, StandardCharsets.UTF_8.toString());
            String encodedArtifactId = URLEncoder.encode(artifactId, StandardCharsets.UTF_8.toString());
            String urlString = String.format("https://search.maven.org/solrsearch/select?q=g:%s%%20a:%s&rows=1&wt=json", encodedGroupId, encodedArtifactId);

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray docs = jsonObject.getJSONObject("response").getJSONArray("docs");
                    if (docs.length() > 0) {
                        return docs.getJSONObject(0).getString("latestVersion");
                    }
                }
            } else {
                System.err.println("Server returned non-OK status: " + responseCode);
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error occurred while fetching the latest version: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static String getVersion(String dependency) {
        String[] parts = dependency.split(":");
        return parts.length > 2 ? parts[2] : null;
    }

    private static String replaceVersion(String dependency, String newVersion) {
        String[] parts = dependency.split(":");
        if (parts.length < 3) {
            return dependency;
        }
        return String.join(":", parts[0], parts[1], newVersion);
    }

    private static List<String> updateDependenciesInFile(String filePath, List<String> updatedDependencies) throws IOException {
        List<String> updatedContent = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int dependencyIndex = 0;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = DEPENDENCY_PATTERN.matcher(line);
                if (matcher.find()) {
                    String updatedLine = line.replace(matcher.group(1), updatedDependencies.get(dependencyIndex++));
                    if (!matcher.group(1).equals(updatedDependencies.get(dependencyIndex - 1))) {
                        updatedLine += " // " + formattedDate;
                    }
                    updatedContent.add(updatedLine);
                } else {
                    updatedContent.add(line);
                }
            }
        }
        return updatedContent;
    }

    private static void writeUpdatedFile(String filePath, List<String> updatedContent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : updatedContent) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}