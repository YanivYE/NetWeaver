package com.NetWeaver.Controllers;

import com.NetWeaver.DTO.CrawlRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/crawl")
public class CrawlController {

    @PostMapping("")
    public ResponseEntity<Object> startCrawl(@RequestBody CrawlRequestDTO dto) {

    }
}
