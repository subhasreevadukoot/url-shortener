package com.javaprojects.urlshortener;

import org.apache.commons.validator.UrlValidator;
import org.springframework.web.bind.annotation.*;

@RestController("/rest/url")
@RequestMapping
public class UrlShortnerResource {

    @GetMapping("/{id}")
    public String getUrl(@PathVariable String id){
        return "";
    }

    @PostMapping
    public void postUrl(@RequestBody String url ){
        UrlValidator urlValidator = new UrlValidator(new String[]{"http","https"});
        if(urlValidator.isValid(url)){

        }

    }
}
