package com.NetWeaver.Controllers;

import com.NetWeaver.DTO.CrawlRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/crawl")
public class CrawlController {

    @PostMapping("")
    public ResponseEntity<Object> startCrawl(@RequestBody CrawlRequestDTO crawlRequest) {
        String url = crawlRequest.getStartUrl();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
