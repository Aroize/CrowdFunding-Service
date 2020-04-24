package ru.ifmo.server.data.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifmo.server.data.entities.Fund;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface FundRepository extends CrudRepository<Fund, Integer> {
    Collection<Fund> findFundByOwnerId(int ownerId);

    Optional<Fund> findFundByName(String name);

    @Query(value = "SELECT * FROM funds WHERE id = :id FOR UPDATE" ,nativeQuery = true)
    Fund findFundByIdForEdit(@Param("id") int id);
}
