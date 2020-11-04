package com.davidhorstman.countries.controllers;

import com.davidhorstman.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    CountryRepository countryRep;

    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
