package com.javaspringcourseproject.gameplifiers.service;

import com.javaspringcourseproject.gameplifiers.model.Publisher;
import com.javaspringcourseproject.gameplifiers.repository.PublisherRepository;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService{

    @Autowired
    private PublisherRepository publisherRepository;

    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher findPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id: " + id));
    }

    public void upsertPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void deletePublisher(Publisher publisher) {
        publisherRepository.delete(publisher);
    }

    public List<Publisher> searchPublishersByCriteria(@Nullable String criterion, @Nullable String searchTerm) {
        List<Publisher> publishersToSearchByCriteria = new ArrayList<>();

        if (criterion != null) {
            switch (criterion) {
                case "name":
                    publishersToSearchByCriteria =  publisherRepository
                            .findByNameContainingIgnoreCase(searchTerm);
                    break;
                case "headquarters":
                    publishersToSearchByCriteria =  publisherRepository
                            .findByHeadquartersContainingIgnoreCase(searchTerm);
                    break;
            }
        } else {
            publishersToSearchByCriteria = this.findAllPublishers();
        }

        return publishersToSearchByCriteria;
    }

    public List<Publisher> findTopFourPublishers() {
        return publisherRepository.findTop4ByOrderByNameAsc();
    }
}
