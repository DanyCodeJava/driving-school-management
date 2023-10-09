package danycode.dsm.repository;

import danycode.dsm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);
    @Query("""
            SELECT u FROM User u\s
            WHERE CAST(u.id as string)  = ?1\s
                OR LOWER(u.email) LIKE CONCAT('%',LOWER(?1),'%')\s
                OR LOWER(u.firstName) LIKE CONCAT('%',LOWER(?1),'%')\s         
                OR LOWER(u.lastName) LIKE CONCAT('%',LOWER(?1),'%')             
            """)
    Page<User> findPageByKeyword(String keyword, Pageable pageable);

}
