package spotify.base;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class AlbumCollection implements Serializable {
    private List<Album> albums;

    public AlbumCollection() {
        albums = new ArrayList<>();
    }

    public AlbumCollection(List<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public int getTotalTracks() {
        return albums.stream().map(Album::getTracks).flatMap(List::stream).toList().size();
    }

    public double getAverageTrackDuration() {
        List<Track> allTracks = albums.stream().map(Album::getTracks).flatMap(List::stream).toList();
        return allTracks.stream().mapToDouble(Track::getDurationMs).sum() / allTracks.size() / 60000;
    }

    public double getAverageAlbumDuration() {
        List<Track> allTracks = albums.stream().map(Album::getTracks).flatMap(List::stream).toList();
        return allTracks.stream().mapToDouble(Track::getDurationMs).sum() / albums.size() / 60000;
    }

    public String getMostPopularTrack() {
        return albums.stream().map(Album::getTracks).flatMap(List::stream)
                .max(Comparator.comparingInt(Track::getPopularity))
                .map(Track::getName).orElse(null);
    }

    public String getMostFrequentArtist() {
        return albums.stream().map(Album::getArtists).flatMap(List::stream)
                .collect(Collectors.groupingBy(artist -> artist, Collectors.counting()))
                .entrySet().stream()
                .max((entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue()))
                .map(Map.Entry::getKey).orElse(null);
    }

    public String getMostFrequentGenre() {
        return albums.stream().map(Album::getGenres).flatMap(Collection::stream)
                .collect(Collectors.groupingBy(genre -> genre, Collectors.counting()))
                .entrySet().stream()
                .max((entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue()))
                .map(Map.Entry::getKey).orElse(null);
    }

    public double getAveragePopularity() {
        return albums.stream().mapToDouble(Album::getPopularity).average().orElse(0);
    }

    public double getAverageTrackNumber() {
        return albums.stream().mapToDouble(Album::getTotalTracks).average().orElse(0);
    }

    public AlbumCollection filterAlbumsByPopularity(int popularity) {
        return new AlbumCollection(albums.stream().filter(album -> album.getPopularity() >= popularity).toList());
    }

    public AlbumCollection filterAlbumsByGenre(String genre) {
        return new AlbumCollection(albums.stream().filter(album -> album.getGenres().contains(genre)).toList());
    }

    public AlbumCollection filterAlbumsByArtist(String artist) {
        return new AlbumCollection(albums.stream().filter(album -> album.getArtists().contains(artist)).toList());
    }

    public AlbumCollection filterAlbumsByName(String name) {
        return new AlbumCollection(albums.stream().filter(album -> album.getName().contains(name)).toList());
    }

    public AlbumCollection filterAlbumsByTotalTracks(int length) {
        return new AlbumCollection(albums.stream().filter(album -> album.getTotalTracks() >= length).toList());
    }

    public AlbumCollection orderAlbumsByPopularity() {
        return new AlbumCollection(albums.stream().sorted((album1, album2) -> album2.getPopularity() - album1.getPopularity()).toList());
    }

    public AlbumCollection orderAlbumsByReleaseYear() {
        return new AlbumCollection(albums.stream().sorted(Comparator.comparing(Album::getReleaseYear)).toList());
    }





}
