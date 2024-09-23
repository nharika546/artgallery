package com.example.artgallery.repository;

import java.util.*;

import com.example.artgallery.model.*;

public interface ArtistRepository {
    ArrayList<Artist> getArtists();

    Artist getArtistById(int artistId);

    Artist addArtist(Artist artist);

    Artist updateArtist(int artistId, Artist artist);

    void deleteArtist(int artistId);

    List<Gallery> getArtistGalleries(int artistId);
}