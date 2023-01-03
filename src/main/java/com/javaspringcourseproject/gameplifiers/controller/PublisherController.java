package com.javaspringcourseproject.gameplifiers.controller;

import com.javaspringcourseproject.gameplifiers.model.Publisher;
import com.javaspringcourseproject.gameplifiers.model.User;
import com.javaspringcourseproject.gameplifiers.repository.PublisherRepository;
import com.javaspringcourseproject.gameplifiers.service.SecurityService;
import com.javaspringcourseproject.gameplifiers.service.UserService;
import com.javaspringcourseproject.gameplifiers.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PublisherController {

    @Autowired
    SecurityService securityService;

    @Autowired
    PublisherRepository publisherRepository;

    @RequestMapping("/publishers")
    public String publishers(Model model, @Param("keyword") String keyword) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        if (keyword != null) {
            List<Publisher> searchedPublishers = publisherRepository.findPublishersByKeyword(keyword);
            model.addAttribute("publishers", searchedPublishers);
        } else {
            List<Publisher> allPublishers = publisherRepository.findAll();
            model.addAttribute("publishers", allPublishers);
        }

        model.addAttribute("keyword", keyword);

        return "publishers";
    }

    @GetMapping("/publisher/{id}")
    public String publisherDetails(@PathVariable("id") Long id, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        Publisher singlePublisher = publisherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id: " + id));

        model.addAttribute("publisher", singlePublisher);
        return "publisherDetails";
    }

    @GetMapping("/add-publisher")
    public String addPublisher(Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        model.addAttribute("publisher", new Publisher());
        return "addPublisher";
    }

    @PostMapping("/add-publisher")
    public String addPublisher(@Valid Publisher publisher, BindingResult bindingResult, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "addPublisher";
        }

        publisherRepository.save(publisher);

        List<Publisher> publishersToLoadAfterCreation = publisherRepository.findAll();
        model.addAttribute("publishers", publishersToLoadAfterCreation);

        return "redirect:/publishers";
    }

    @GetMapping("/publisher/edit/{id}")
    public String editPublisher(@PathVariable("id") Long id, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        Publisher publisherToEdit = publisherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id: " + id));

        model.addAttribute("publisher", publisherToEdit);
        return "editPublisher";
    }

    @PostMapping("/publisher/update/{id}")
    public String updatePublisher(@PathVariable("id") Long id, @Valid Publisher publisherToUpdate,
                                  BindingResult bindingResult, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            publisherToUpdate.setId(id);
            return "editPublisher";
        }

        publisherRepository.save(publisherToUpdate);

        List<Publisher> publishersToLoadAfterUpdate = publisherRepository.findAll();
        model.addAttribute("publisher", publishersToLoadAfterUpdate);

        return "redirect:/publishers";
    }

    @GetMapping("/publisher/delete/{id}")
    public String publisherDeletionDetails(@PathVariable("id") Long id, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        Publisher publisherToDelete = publisherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + id));

        model.addAttribute("publisher", publisherToDelete);

        return "publisherDeletionDetails";
    }

    @PostMapping("/publisher/delete/{id}")
    public String deletePublisher(@PathVariable("id") Long id, Publisher publisherToConfirmDeletion, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        publisherRepository.delete(publisherToConfirmDeletion);

        List<Publisher> publishersToLoadAfterDeletion = publisherRepository.findAll();

        model.addAttribute("publishers", publishersToLoadAfterDeletion);

        return "redirect:/publishers";
    }
}
