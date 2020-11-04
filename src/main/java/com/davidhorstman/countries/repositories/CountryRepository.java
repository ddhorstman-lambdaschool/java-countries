package com.davidhorstman.countries.repositories;

import com.davidhorstman.countries.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
