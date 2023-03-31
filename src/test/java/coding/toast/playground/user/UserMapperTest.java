package coding.toast.playground.user;

import coding.toast.playground.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
// @TestPropertySource(
//         properties = {
//                 "spring.jpa.hibernate.ddl-auto=validate",
//                 "liquibase.enabled=false"
//         }
// )
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void getUserListTest() {
        List<User> list =
                userMapper.list();

        for (User user : list) {
            System.out.println("user = " + user);
        }
    }

    @Test
    void readOneRowTest() {
        User user = userMapper.select(null);
        System.out.println("user = " + user);
    }

    @Test
    void insertRowTest() {
        User user = User.builder()
                .name("mike jaw")
                .phoneNumber(null)
                .build();

        int insertCnt = userMapper.insert(user);
        assertThat(insertCnt).isEqualTo(1);
    }


}
