package com.zz.lab.neo4j.repo;

import com.zz.lab.neo4j.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByName(String name);
}
