package com.example.artgallery.service;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;
import com.sun.xml.bind.annotation.OverrideAnnotationOf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class GalleryJpaService implements GalleryRepository {
    @Autowired
    private GalleryJpaRepository galleryJpaRepository;
    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @Override
    public ArrayList<Gallery> getGalleries() {
        List<Gallery> galleryList = galleryJpaRepository.findAll();
        ArrayList<Gallery> gallerys = new ArrayList<>(galleryList);
        return gallerys;
    }

    @Override
    public Gallery getGalleryById(int galleryId) {
        try {
            Gallery gallery = galleryJpaRepository.findById(galleryId).get();
            return gallery;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Gallery addGallery(Gallery gallery) {
        try {
            List<Integer> Ids = new ArrayList<>();
            for (Artist sp : gallery.getArtists()) {
                Ids.add(sp.getArtistId());
            }
            List<Artist> artistList = artistJpaRepository.findAllById(Ids);
            if (Ids.size() != artistList.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            gallery.setArtists(artistList);
            for (Artist artist : artistList) {
                artist.getGalleries().add(gallery);
            }
            Gallery newgallery = galleryJpaRepository.save(gallery);
            artistJpaRepository.saveAll(artistList);
            return newgallery;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Gallery updateGallery(int galleryId, Gallery gallery) {
        try {
            Gallery new_gallery = galleryJpaRepository.findById(galleryId).get();
            if (gallery.getGalleryName() != null) {
                new_gallery.setGalleryName(gallery.getGalleryName());
            }
            if (gallery.getLocation() != null) {
                new_gallery.setLocation(gallery.getLocation());
            }
            if (gallery.getArtists() != null) {
                List<Integer> arr = new ArrayList<>();
                for (Artist sp : gallery.getArtists()) {
                    arr.add(sp.getArtistId());
                }
                List<Artist> artist1 = artistJpaRepository.findAllById(arr);
                try {
                    for (int i : arr) {
                        Artist sp = artistJpaRepository.findById(i).get();
                    }
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                List<Artist> new_artists = new ArrayList<>();
                for (Artist artist : gallery.getArtists()) {
                    Artist new_artist = artistJpaRepository.findById(artist.getArtistId()).get();
                    if (artist.getArtistName() != null) {
                        new_artist.setArtistName(artist.getArtistName());
                    }
                    if (artist.getGenre() != null) {
                        new_artist.setGenre(artist.getGenre());
                    }
                    new_artists.add(new_artist);
                }
                artistJpaRepository.saveAll(new_artists);
                new_gallery.setArtists(new_artists);
            }
            galleryJpaRepository.save(new_gallery);
            return getGalleryById(galleryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteGallery(int galleryId) {
        try {
            Gallery gallery = galleryJpaRepository.findById(galleryId).get();
            List<Artist> artists = gallery.getArtists();
            for (Artist artist : artists) {
                artist.getGalleries().remove(gallery);
            }
            artistJpaRepository.saveAll(artists);
            galleryJpaRepository.deleteById(galleryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Artist> getGalleryArtists(int galleryId) {
        Gallery gallery = getGalleryById(galleryId);
        return gallery.getArtists();
    }
}