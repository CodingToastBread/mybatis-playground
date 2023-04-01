package coding.toast.playground.user;

import coding.toast.playground.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface UserMapper {
    List<User> list();

    User select(@Param("id") Long id);
    
    // List<User> search(@Param("name") String name);
    
    List<User> search(User user);

    int insert(User user);
    
    int delete(User user);
}
