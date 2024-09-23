package com.example.artgallery.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Art {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int artId;

    @Column(name = "title")
    private String artTitle;

    @Column(name = "theme")
    private String theme;

    @ManyToOne
    @JoinColumn(name = "artistid")
    private Artist artist;

    public Art() {
    }

    public int getArtId() {
        return this.artId;
    }

    public void setArtId(int artId) {
        this.artId = artId;
    }

    public String getArtTitle() {
        return this.artTitle;
    }

    public void setArtTitle(String artTitle) {
        this.artTitle = artTitle;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String artTheme) {
        this.theme = theme;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

}