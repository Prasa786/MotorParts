package com.MotorParts.TN.Repository;

import com.MotorParts.TN.Model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part,Long> {
@Query(value="Select * from parts where stockQuantity < 20" ,nativeQuery=true)
    List<Part> findByStockQuantity();
}
