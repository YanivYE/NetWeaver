package com.NetWeaver.Controllers;

import com.NetWeaver.DTO.CrawlRequestDTO;
import com.NetWeaver.Services.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/crawl")
public class CrawlController {

    @Autowired
    private CrawlService service;

    @PostMapping("")
    public ResponseEntity<Object> start(@RequestBody CrawlRequestDTO req) {
        UUID id = service.start(req);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
