package com.davidhorstman.countries.controllers;

import com.davidhorstman.countries.models.Country;
import com.davidhorstman.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class CountryController {

    @Autowired
    CountryRepository countryRep;

    private List<Country> filterCountries(List<Country> countries, CheckValue<Country> tester) {
        List<Country> filteredList = new ArrayList<>();
        for (Country c :
                countries) {
            if (tester.test(c)) filteredList.add(c);
        }
        return filteredList;
    }

    private List<Country> getSortedCountries() {
        List<Country> countries = new ArrayList<>();
        countryRep.findAll().forEach(c -> countries.add(c));

        countries.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return countries;
    }

    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries() {
        List<Country> countries = getSortedCountries();

        countries.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> countryByFirstLetter(@PathVariable char letter) {
        List<Country> countries = getSortedCountries();

        countries = filterCountries(countries, c -> Character.toLowerCase(c.getName().charAt(0)) == Character.toLowerCase(letter));

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> totalPopluation() {
        List<Country> countries = getSortedCountries();
        long totalPop = 0;
        for (Country c :
                countries) {
            totalPop += c.getPopulation();
        }
        System.out.println("Total population: " + totalPop);
        return new ResponseEntity<>(totalPop, HttpStatus.OK);
    }

    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> minimumPopulation() {
        List<Country> countries = getSortedCountries();
        countries.sort((c1, c2) -> (int) (c1.getPopulation() - c2.getPopulation()));
        return new ResponseEntity<>(countries.get(0), HttpStatus.OK);
    }

    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> maximumPopulation() {
        List<Country> countries = getSortedCountries();
        countries.sort((c1, c2) -> (int) (c1.getPopulation() - c2.getPopulation()));
        return new ResponseEntity<>(countries.get(countries.size() - 1), HttpStatus.OK);
    }


    @GetMapping(value = "/population/median", produces = {"application/json"})
    public ResponseEntity<?> medianPopulation() {
        List<Country> countries = getSortedCountries();
        countries.sort((c1, c2) -> (int) (c1.getPopulation() - c2.getPopulation()));
        int size = countries.size();
        int middleIndex;
        if (size % 2 == 0) middleIndex = size / 2;
        else middleIndex = size / 2 + 1;
        Country middleCountry = countries.get(middleIndex);
        return new ResponseEntity<>(middleCountry, HttpStatus.OK);
    }
}
