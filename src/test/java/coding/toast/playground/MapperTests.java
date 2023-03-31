package coding.toast.playground;

import coding.toast.playground.user.UserMapper;
import coding.toast.playground.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void testUserList() {
        List<User> list =
                userMapper.list();

        for (User user : list) {
            System.out.println("user = " + user);
        }
    }

    @Test
    void createTableTest() {

    }

}
