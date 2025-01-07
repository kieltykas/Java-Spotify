package spotify;

import spotify.base.Album;
import spotify.base.AlbumCollection;
import spotify.connect.SpotifyAuthorization;
import spotify.fetcher.AlbumFetcher;

public class Main {
    public static void main(String[] args) {
        SpotifyAuthorization spotifyAuthorization = new SpotifyAuthorization();
        String accessToken = spotifyAuthorization.getAccessToken("1b20d9e2c8394936af2aacddd724b24d", "81e9a52248e246e9ba383782ae2941e0");

        AlbumCollection albumCollection = new AlbumCollection();

        Album album = AlbumFetcher.fetchAlbum("thriller", accessToken);
        album.setExtendedInformation(accessToken);
        albumCollection.addAlbum(album);

        album = AlbumFetcher.fetchAlbum("self control laura", accessToken);
        album.setExtendedInformation(accessToken);
        albumCollection.addAlbum(album);

        album = AlbumFetcher.fetchAlbum("brat", accessToken);
        album.setExtendedInformation(accessToken);
        albumCollection.addAlbum(album);

        album = AlbumFetcher.fetchAlbum("good kid maad city", accessToken);
        album.setExtendedInformation(accessToken);
        albumCollection.addAlbum(album);

        System.out.println("Total tracks: " + albumCollection.getTotalTracks());
        System.out.println("Average track duration: " + albumCollection.getAverageTrackDuration());
        System.out.println("Average album duration: " + albumCollection.getAverageAlbumDuration());
        System.out.println("Most popular track: " + albumCollection.getMostPopularTrack());
        System.out.println("Most frequent artist: " + albumCollection.getMostFrequentArtist());
        System.out.println("Most frequent genre: " + albumCollection.getMostFrequentGenre());
        System.out.println("Average popularity: " + albumCollection.getAveragePopularity());
        System.out.println("Average track number: " + albumCollection.getAverageTrackNumber());
    }
}
