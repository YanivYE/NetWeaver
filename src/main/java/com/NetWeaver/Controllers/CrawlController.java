package com.NetWeaver.Controllers;

import com.NetWeaver.Models.CrawlRequest;
import com.NetWeaver.Models.CrawlResult;
import com.NetWeaver.Services.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/crawl")
public class CrawlController {

    @Autowired
    private CrawlService crawlService;

    @PostMapping
    public ResponseEntity<CrawlResult> start(@RequestBody CrawlRequest req) {
        CrawlResult result = crawlService.start(req);
        return ResponseEntity.ok(result);
    }
}
