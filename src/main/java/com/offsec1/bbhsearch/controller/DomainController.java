package com.offsec1.bbhsearch.controller;


import com.offsec1.bbhsearch.model.Domain;
import com.offsec1.bbhsearch.repository.DomainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/domain")
public class DomainController {

    private static final Logger LOG = LoggerFactory.getLogger(DomainController.class);

    @Autowired
    private DomainRepository domainRepository;

    @GetMapping("/all")
    public ResponseEntity getAllDomains() {
        List<Domain> domains = new ArrayList<>();
        try {
            domainRepository.findAll().forEach(domains::add);
            return ResponseEntity.ok().body(domains);
        } catch (Exception e) {
            LOG.error("Failed loading domain data: {}", e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping()
    public ResponseEntity saveDomains(@RequestBody List<Domain> d) {
        try {
            domainRepository.saveAll(d);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOG.error("Failed saving domain data: {}", e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

}