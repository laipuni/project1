package project.project1.role;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query("select r from Role r where r.role =:role")
    Optional<Role> findByMemberRole(@Param("role")MemberRole role);
}
