package spotify.fetcher;

import org.json.JSONObject;
import spotify.base.Album;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class AlbumFetcher {
    private static final String ALBUM_URL = "https://api.spotify.com/v1/albums/";
    private static final String SEARCH_URL = "https://api.spotify.com/v1/search?q=";

    private static String getAlbumId(String albumName, String accessToken) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SEARCH_URL + URLEncoder.encode(albumName, StandardCharsets.UTF_8) + "&type=album"))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());
                return json.getJSONObject("albums").getJSONArray("items").getJSONObject(0).getString("id");
            } else {
                System.err.println("Error getting album id. Status: " + response.statusCode());
                System.err.println("Response: " + response.body());
                return null;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Album fetchAlbum(String albumName, String accessToken) {
        String albumId = getAlbumId(albumName, accessToken);
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ALBUM_URL + albumId))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());
                return new Album(json);
            } else {
                System.err.println("Error getting album. Status: " + response.statusCode());
                System.err.println("Response: " + response.body());
                return null;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
