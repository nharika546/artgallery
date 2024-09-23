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
public class ArtistJpaService implements ArtistRepository {
    @Autowired
    private GalleryJpaRepository galleryJpaRepository;
    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @Override
    public ArrayList<Artist> getArtists() {
        List<Artist> artistList = artistJpaRepository.findAll();
        ArrayList<Artist> artists = new ArrayList<>(artistList);
        return artists;
    }

    public Artist getArtistById(int artistId) {
        try {
            Artist sp = artistJpaRepository.findById(artistId).get();
            return sp;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Artist addArtist(Artist artist) {
        try {
            List<Integer> Ids = new ArrayList<>();
            for (Gallery ep : artist.getGalleries()) {
                Ids.add(ep.getGalleryId());
            }
            List<Gallery> galleryList = galleryJpaRepository.findAllById(Ids);
            for (int i : Ids) {
                if (!galleryJpaRepository.existsById(i))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            artist.setGalleries(galleryList);
            for (Gallery ap : galleryList) {
                ap.getArtists().add(artist);
            }
            artistJpaRepository.save(artist);
            galleryJpaRepository.saveAll(galleryList);
            return artist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Artist updateArtist(int artistId, Artist artist) {
        try {
            Artist new_artist = artistJpaRepository.findById(artistId).get();
            if (artist.getArtistName() != null) {
                new_artist.setArtistName(artist.getArtistName());
            }
            if (artist.getGenre() != null) {
                new_artist.setGenre(artist.getGenre());
            }
            if (artist.getGalleries() != null) {
                List<Integer> arr = new ArrayList<>();
                for (Gallery e : artist.getGalleries()) {
                    arr.add(e.getGalleryId());
                }
                List<Gallery> gallerys1 = galleryJpaRepository.findAllById(arr);
                if (arr.size() != gallerys1.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                List<Gallery> new_gallerys = new ArrayList<>();
                for (Gallery gallery : artist.getGalleries()) {
                    Gallery new_gallery = gallery;
                    if (gallery.getGalleryName() != null) {
                        new_gallery.setGalleryName(gallery.getGalleryName());
                    }
                    if (gallery.getLocation() != null) {
                        new_gallery.setLocation(gallery.getLocation());
                    }
                    new_gallerys.add(new_gallery);
                }
                galleryJpaRepository.saveAll(new_gallerys);
                new_artist.setGalleries(new_gallerys);
            }
            artistJpaRepository.save(new_artist);
            return new_artist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteArtist(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            List<Gallery> gallerys = artist.getGalleries();
            for (Gallery gallery : gallerys) {
                gallery.getArtists().remove(artist);
            }
            galleryJpaRepository.saveAll(gallerys);
            artistJpaRepository.deleteById(artistId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Gallery> getArtistGalleries(int artistId) {
        Artist artist = getArtistById(artistId);
        return artist.getGalleries();
    }
}