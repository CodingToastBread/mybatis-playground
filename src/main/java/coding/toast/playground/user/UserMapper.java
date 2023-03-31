package coding.toast.playground.user;

import coding.toast.playground.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<User> list();

    int createTable(@Param("map") Map<String, String> map);

}
