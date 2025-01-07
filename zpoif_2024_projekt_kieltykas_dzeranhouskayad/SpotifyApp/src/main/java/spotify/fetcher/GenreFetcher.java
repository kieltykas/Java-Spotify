package spotify.fetcher;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreFetcher {
    private static final String ARTISTS_URL = "https://api.spotify.com/v1/artists?ids=";

    public static Set<String> fetchGenre(List<String> artistsId, String accessToken) {

        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ARTISTS_URL + URLEncoder.encode(String.join(",", artistsId), StandardCharsets.UTF_8)))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());
                return json.getJSONArray("artists").toList().stream()
                    .map(artistJson -> new JSONObject((Map<?, ?>) artistJson).getJSONArray("genres").toList())
                    .flatMap(List::stream).map(Object::toString).collect(Collectors.toSet());

            } else {
                System.err.println("Error getting track. Status: " + response.statusCode());
                System.err.println("Response: " + response.body());
                return null;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
