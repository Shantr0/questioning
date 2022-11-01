package com.example.ankets.repositories;

import com.example.ankets.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {

    @Query(value = "SELECT f.id FROM FORM f where f.dtype='Form'", nativeQuery = true)
    List<Long> findAllIds();
}
