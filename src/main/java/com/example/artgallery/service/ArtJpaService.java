package com.example.artgallery.service;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtJpaService implements ArtRepository {

    @Autowired
    private ArtJpaRepository artJpaRepository;
    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @Override
    public Artist getArtArtist(int artId) {
        try {
            Art art = artJpaRepository.findById(artId).get();
            return art.getArtist();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ArrayList<Art> getArts() {
        List<Art> artList = artJpaRepository.findAll();
        ArrayList<Art> arts = new ArrayList<>(artList);
        return arts;
    }

    @Override
    public Art getArtById(int artId) {
        try {
            Art art = artJpaRepository.findById(artId).get();
            return art;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Art addArt(Art art) {
        Artist artist = art.getArtist();
        int artistId = artist.getArtistId();
        try {
            Artist completeartist = artistJpaRepository.findById(artistId).get();
            art.setArtist(completeartist);
            artJpaRepository.save(art);
            return art;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Art updateArt(int artId, Art art) {
        try {
            Art newart = artJpaRepository.findById(artId).get();
            if (art.getArtTitle() != null) {
                newart.setArtTitle(art.getArtTitle());
            }
            if (art.getTheme() != null) {
                newart.setTheme(art.getTheme());
            }
            artJpaRepository.save(newart);
            return newart;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteArt(int artId) {
        try {
            artJpaRepository.deleteById(artId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}