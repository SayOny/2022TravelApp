package com.project.travel.controller;

import com.project.travel.domain.Image;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter
@NotEmpty
public class PostForm {

    private Long userId;
    private String title;
    private List<String> sTags;
    private List<String> placeTypes;
    private List<Float> scores;
    private List<String> contents;
    private List<Image> images;
    private List<Integer> days;
    private List<String> sPlaces;
}
