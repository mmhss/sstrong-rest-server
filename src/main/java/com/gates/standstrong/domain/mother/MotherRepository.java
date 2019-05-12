package com.gates.standstrong.domain.mother;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface MotherRepository extends BaseRepository<Mother> {

    @Query(value="SELECT id FROM mother where mother.identification_number = :identificationNumber", nativeQuery = true)
    Long findId(@Param("identificationNumber")String identificationNumber);

    @Query(value="SELECT m FROM Mother m where m.viberId = :viberId")
    Mother findMother(@Param("viberId")String viberId);

}
