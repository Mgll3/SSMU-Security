package co.udea.ssmu.api.model.jpa.repository.role;

import co.udea.ssmu.api.model.jpa.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
