package com.javaprojects.urlshortener;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RequestMapping("/rest/url")
@RestController
public class UrlShortenerResource {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/{id}")
    public String getUrl(@PathVariable String id) throws Exception {
        String url = stringRedisTemplate.opsForValue().get(id);
        System.out.println("URL : "+url);
        if(url == null){
            throw new Exception("No shorter url can be generated");
        }
        return url;
    }
    @PostMapping
    public String createUrl(@RequestBody String url) throws Exception {
        UrlValidator urlValidator=new UrlValidator(
                new String[]{"http","https"}
                );
        if(urlValidator.isValid(url)){
            //generating a unique key
            //Redis is a key-value store
            String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
            System.out.println("Generated ID "+id );
            stringRedisTemplate.opsForValue().set(id,url);
            return id;
        }
       throw new RuntimeException("url invalid : "+url);

    }
}
