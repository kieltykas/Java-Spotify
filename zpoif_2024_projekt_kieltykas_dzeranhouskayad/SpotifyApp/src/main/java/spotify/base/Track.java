package spotify.base;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Track {
    private final List<String> artists;
    private final int durationMs;
    private final String id;
    private final String name;
    private final int popularity;

    public Track(JSONObject trackJsonMain) {
        this.artists = new ArrayList<>();
        JSONArray artistsJson = trackJsonMain.getJSONArray("artists");
        for (int i = 0; i < artistsJson.length(); i++) {
            this.artists.add(artistsJson.getJSONObject(i).getString("name"));
        }
        this.id = trackJsonMain.getString("id");
        this.name = trackJsonMain.getString("name");
        this.durationMs = trackJsonMain.getInt("duration_ms");
        this.popularity = trackJsonMain.getInt("popularity");
    }

    public List<String> getArtists() {
        return artists;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }
}
