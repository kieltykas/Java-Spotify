package spotify.fetcher;

import org.json.JSONObject;
import spotify.base.Track;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrackFetcher {
    private static final String TRACK_URL = "https://api.spotify.com/v1/tracks?ids=";

    public static List<Track> fetchTracks(List<String> tracksId, String accessToken) {

        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TRACK_URL + URLEncoder.encode(String.join(",", tracksId), StandardCharsets.UTF_8)))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());
                return json.getJSONArray("tracks").toList().stream()
                        .map(trackJson -> new Track(new JSONObject((Map<?, ?>) trackJson)))
                        .collect(Collectors.toList());
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
