package com.javaspringcourseproject.gameplifiers.service;

import com.javaspringcourseproject.gameplifiers.model.Publisher;
import com.sun.istack.Nullable;

import java.util.List;

public interface PublisherService {
    List<Publisher> findAllPublishers();

    Publisher findPublisherById(Long id);

    void upsertPublisher(Publisher publisher);

    void deletePublisher(Publisher publisher);

    List<Publisher> searchPublishersByCriteria(@Nullable String criterion, @Nullable String searchTerm);
}
