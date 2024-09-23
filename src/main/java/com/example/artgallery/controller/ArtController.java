package com.example.artgallery.controller;

import com.example.artgallery.service.ArtJpaService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.example.artgallery.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@RestController
public class ArtController {
    @Autowired
    public ArtJpaService artService;

    @GetMapping("/arts/{artId}/artist")
    public Artist getartartist(@PathVariable("artId") int artId) {
        return artService.getArtArtist(artId);
    }

    @GetMapping("/galleries/artists/arts")
    public ArrayList<Art> getArts() {
        return artService.getArts();
    }

    @GetMapping("/galleries/artists/arts/{artId}")
    public Art getArtById(@PathVariable("artId") int artId) {
        return artService.getArtById(artId);
    }

    @PostMapping("/galleries/artists/arts")
    public Art addArt(@RequestBody Art art) {
        return artService.addArt(art);
    }

    @PutMapping("/galleries/artists/arts/{artId}")
    public Art updateArt(@PathVariable("artId") int artId, @RequestBody Art art) {
        return artService.updateArt(artId, art);
    }

    @DeleteMapping("/galleries/artists/arts/{artId}")
    public void deleteArt(@PathVariable("artId") int artId) {
        artService.deleteArt(artId);
    }
}