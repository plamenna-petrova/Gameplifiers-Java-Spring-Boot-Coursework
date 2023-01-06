package com.javaspringcourseproject.gameplifiers.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 40, nullable = false, unique = true)
    @NotBlank(message = "The game title is mandatory!")
    private String title;

    @Column(name = "edition", length = 50, nullable = false)
    @NotBlank(message = "The game's edition is mandatory!")
    private String edition;

    @Column(name = "year_of_release", nullable = false)
    @NotNull(message = "Please provide a year of release to the game!")
    @Min(value = 1970, message = "The year of release cannot be before 1970!")
    @Max(value = 2023, message = "The year of release cannot be after 2023")
    private int yearOfRelease;

    @Column(name = "image_url", nullable = false)
    @NotBlank(message = "Please provide a image url for the game!")
    private String imageUrl;

    @Column(name = "main_story_completion_time", nullable = false)
    @NotBlank(message = "Please provide an estimated for the main story's completion!")
    private String mainStoryCompletionTime;

    @Column(name = "main_story_plus_side_quests_completion_time", nullable = false)
    @NotBlank(message = "Please provide an estimated for the main story's + side quests completion!")
    private String mainStoryPlusSideQuestsCompletionTime;

    @Column(name = "completionist_time", nullable = false)
    @NotBlank(message = "Please provide an estimated completionist time!")
    private String completionistTime;

    @Column(name = "all_styles_completion_time", nullable = false)
    @NotBlank(message = "Please provide an estimated time for all styles completion!")
    private String allStylesCompletionTime;

    @Column(name = "annotation", nullable = false)
    @NotBlank(message = "Please provide a game's annotation!")
    @Size(min = 20, max = 1000, message = "The game annotation must be between 20 and 1000 symbols long!")
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    public Game() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getMainStoryCompletionTime() {
        return mainStoryCompletionTime;
    }

    public void setMainStoryCompletionTime(String mainStoryCompletionTime) {
        this.mainStoryCompletionTime = mainStoryCompletionTime;
    }

    public String getMainStoryPlusSideQuestsCompletionTime() {
        return mainStoryPlusSideQuestsCompletionTime;
    }

    public void setMainStoryPlusSideQuestsCompletionTime(String mainStoryPlusSideQuestsCompletionTime) {
        this.mainStoryPlusSideQuestsCompletionTime = mainStoryPlusSideQuestsCompletionTime;
    }

    public String getCompletionistTime() {
        return completionistTime;
    }

    public void setCompletionistTime(String completionistTime) {
        this.completionistTime = completionistTime;
    }

    public String getAllStylesCompletionTime() {
        return allStylesCompletionTime;
    }

    public void setAllStylesCompletionTime(String allStylesCompletionTime) {
        this.allStylesCompletionTime = allStylesCompletionTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
