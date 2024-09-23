create table if not exists artist (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name varchar(255),
  genre varchar(255)
);

create table if not exists art (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title varchar(255),
  theme varchar(255),
  artistid INT,
  FOREIGN KEY(artistid) REFERENCES artist(id)
);

create table if not exists gallery (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    location varchar(255)
);

create table if not exists artist_gallery (
    artistid INT,
    galleryid INT,
    PRIMARY KEY(artistid,galleryid),
    FOREIGN KEY(artistid) REFERENCES artist(id),
    FOREIGN KEY(galleryid) REFERENCES gallery(id)
);