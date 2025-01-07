package spotify.base;

import org.json.JSONArray;
import org.json.JSONObject;
import spotify.fetcher.GenreFetcher;
import spotify.fetcher.TrackFetcher;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Album {
    private final String albumType;
    private final int totalTracks;
    private final String id;
    private final String imageUrl;
    private final BufferedImage coverArt;
    private final String name;
    private final String releaseYear;
    private final List<String> artists;
    private final List<String> artistsId;
    private Set<String> genres;
    private List<Track> tracks;
    private final List<String> tracksName;
    private final List<String> tracksId;
    private final int popularity;

    public Album(JSONObject albumJson) {
        this.albumType = albumJson.getString("album_type");
        this.totalTracks = albumJson.getInt("total_tracks");
        this.id = albumJson.getString("id");
        this.imageUrl = albumJson.getJSONArray("images").getJSONObject(0).getString("url");
        try {
            this.coverArt = ImageIO.read(new URL(imageUrl));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.name = albumJson.getString("name");
        this.releaseYear = albumJson.getString("release_date").substring(0, 4);
        this.artists = new ArrayList<>();
        this.artistsId = new ArrayList<>();

        JSONArray artistsJson = albumJson.getJSONArray("artists");
        for (int i = 0; i < artistsJson.length(); i++) {
            this.artists.add(artistsJson.getJSONObject(i).getString("name"));
            this.artistsId.add(artistsJson.getJSONObject(i).getString("id"));
        }
        this.genres = null;
        this.tracksName = new ArrayList<>();
        this.tracksId = new ArrayList<>();
        JSONArray tracksJson = albumJson.getJSONObject("tracks").getJSONArray("items");
        for (int i = 0; i < tracksJson.length(); i++) {
            this.tracksName.add(tracksJson.getJSONObject(i).getString("name"));
            this.tracksId.add(tracksJson.getJSONObject(i).getString("id"));
        }
        this.tracks = null;
        this.popularity = albumJson.getInt("popularity");
    }

    public void setExtendedInformation(String accessToken) {
        this.tracks = TrackFetcher.fetchTracks(tracksId, accessToken);
        this.genres = GenreFetcher.fetchGenre(artistsId, accessToken);
    }

    public String getAlbumType() {
        return albumType;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public List<String> getArtists() {
        return artists;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public List<String> getTracksId() {
        return tracksId;
    }

    public int getPopularity() {
        return popularity;
    }
}
