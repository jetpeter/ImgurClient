package me.jefferey.imgur.models;

/**
 * Merger of GalleryImage and GalleryAlbum models, This allows us to easily cache image and albums
 * in the same LRU cache
 */
public class GalleryItem extends Image {
}
