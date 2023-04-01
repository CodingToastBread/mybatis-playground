package coding.toast.playground.user;

import coding.toast.playground.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

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
                .build();

        int insertCnt = userMapper.insert(user);
        assertThat(insertCnt).isEqualTo(1);
    }

    @Test
    void searchTest() {
        User param = new User();
        // param.setName("%");
        // param.setPhoneNumber("010");
        
        List<User> search = userMapper.search(param);
        for (User user : search) {
            System.out.println("user = " + user);
        }
    }
    
    @Test
    void getOneTest() {
    
    }
    
    @Test
    void deleteTest() {
        User user = new User();
        user.setId(null);
        userMapper.delete(user);
    }

}
