package danycode.dsm.repository;

import danycode.dsm.entity.Student;
import danycode.dsm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long>, JpaRepository<Student, Long> {
    @Query("""
            SELECT u FROM Student u\s
            WHERE CAST(u.id as string)  = ?1\s
                OR LOWER(u.email) LIKE CONCAT('%',LOWER(?1),'%')\s
                OR LOWER(u.firstName) LIKE CONCAT('%',LOWER(?1),'%')\s         
                OR LOWER(u.lastName) LIKE CONCAT('%',LOWER(?1),'%')             
            """)
    Page<Student> findPageByKeyword(String keyword, Pageable pageable);
}
