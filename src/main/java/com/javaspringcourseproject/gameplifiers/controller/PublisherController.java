package com.javaspringcourseproject.gameplifiers.controller;

import com.javaspringcourseproject.gameplifiers.model.Publisher;
import com.javaspringcourseproject.gameplifiers.model.User;
import com.javaspringcourseproject.gameplifiers.repository.PublisherRepository;
import com.javaspringcourseproject.gameplifiers.service.GameService;
import com.javaspringcourseproject.gameplifiers.service.PublisherService;
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
    PublisherService publisherService;

    @Autowired
    UserService userService;

    @RequestMapping("/publishers")
    public String publishers(Model model, @Param("criterion") String criterion,
                             @Param("searchTerm") String searchTerm) {
        List<Publisher> publishersSearchResults = publisherService.searchPublishersByCriteria(criterion, searchTerm);

        model.addAttribute("criterion", criterion);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("publishers", publishersSearchResults);

        if (userService.checkCurrentUserAdministrativePrivileges()) {
            model.addAttribute("privileges", "superuser");
        } else {
            model.addAttribute("privileges", "user");
        }

        return "publishers";
    }

    @GetMapping("/publisher/{id}")
    public String publisherDetails(@PathVariable("id") Long id, Model model) {
        Publisher singlePublisher = publisherService.findPublisherById(id);

        model.addAttribute("publisher", singlePublisher);

        return "publisherDetails";
    }

    @GetMapping("/add-publisher")
    public String addPublisher(Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        model.addAttribute("publisher", new Publisher());

        return "addPublisher";
    }

    @PostMapping("/add-publisher")
    public String addPublisher(@Valid Publisher publisher, BindingResult bindingResult, Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        if (bindingResult.hasErrors()) {
            return "addPublisher";
        }

        publisherService.upsertPublisher(publisher);

        List<Publisher> publishersToLoadAfterCreation = publisherService.findAllPublishers();
        model.addAttribute("publishers", publishersToLoadAfterCreation);

        return "redirect:/publishers";
    }

    @GetMapping("/publisher/edit/{id}")
    public String editPublisher(@PathVariable("id") Long id, Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        Publisher publisherToEdit = publisherService.findPublisherById(id);

        model.addAttribute("publisher", publisherToEdit);

        return "editPublisher";
    }

    @PostMapping("/publisher/update/{id}")
    public String updatePublisher(@PathVariable("id") Long id, @Valid Publisher publisherToUpdate,
                                  BindingResult bindingResult, Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        if (bindingResult.hasErrors()) {
            publisherToUpdate.setId(id);
            return "editPublisher";
        }

        publisherService.upsertPublisher(publisherToUpdate);

        List<Publisher> publishersToLoadAfterUpdate = publisherService.findAllPublishers();
        model.addAttribute("publisher", publishersToLoadAfterUpdate);

        return "redirect:/publishers";
    }

    @GetMapping("/publisher/delete/{id}")
    public String publisherDeletionDetails(@PathVariable("id") Long id, Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        Publisher publisherToDelete = publisherService.findPublisherById(id);

        model.addAttribute("publisher", publisherToDelete);

        return "publisherDeletionDetails";
    }

    @PostMapping("/publisher/delete/{id}")
    public String deletePublisher(@PathVariable("id") Publisher publisherToConfirmDeletion, Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        publisherService.deletePublisher(publisherToConfirmDeletion);

        List<Publisher> publishersToLoadAfterDeletion = publisherService.findAllPublishers();

        model.addAttribute("publishers", publishersToLoadAfterDeletion);

        return "redirect:/publishers";
    }
}
