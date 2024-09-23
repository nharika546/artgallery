package com.example.artgallery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.*;
import com.example.artgallery.model.Artist;

@Entity
@Table(name = "gallery")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int galleryId;

    @Column(name = "name")
    private String galleryName;

    @Column(name = "location")
    private String location;

    @ManyToMany(mappedBy = "galleries")
    @JsonIgnoreProperties("galleries")
    private List<Artist> artists;

    public Gallery() {

    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Artist> getArtists() {
        return this.artists;
    }

    public void setGalleryId(int id) {
        this.galleryId = id;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryName(String name) {
        this.galleryName = name;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}