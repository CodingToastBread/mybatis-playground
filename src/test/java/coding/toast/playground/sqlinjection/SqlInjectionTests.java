package coding.toast.playground.sqlinjection;

import coding.toast.playground.sql_injection.dto.SqlInjectionTestDTO;
import coding.toast.playground.sql_injection.mapper.SqlInjectionTestMapper;
import coding.toast.playground.sql_injection.service.SqlInjectionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class SqlInjectionTests {

    @Autowired
    PlatformTransactionManager txManager;
    TransactionTemplate transactionTemplate;

    @Autowired
    SqlInjectionTestMapper mapper;

    @Autowired
    SqlInjectionService service;

    // 딱 한번만 호출된다.
    @BeforeEach
    public void init() {
        TransactionTemplate template = new TransactionTemplate(txManager);
        template.setPropagationBehavior(Propagation.REQUIRES_NEW.value());
        template.setReadOnly(true);
        transactionTemplate = template;
    }

    @Test
    @Transactional
    void testMe() {
        assertThat(transactionTemplate.isReadOnly()).isTrue();

        SqlInjectionTestDTO dto = SqlInjectionTestDTO.builder()
                .id(1L)
                // creating injection code
                .name("'NONE';delete from coding_toast.test_sql_injection_table").build();

        SqlInjectionTestDTO select = mapper.select(dto);
        assertThat(select).isNull();

        assertThat(mapper.selectList(null).size()).isEqualTo(0);
    }

    @Test
    @Transactional
    void ReadOnlyTransactionTest() {

        UncategorizedDataAccessException uncategorizedDataAccessException = Assertions.assertThrows(UncategorizedDataAccessException.class, () -> {
            transactionTemplate.<Void>execute(status -> {
                SqlInjectionTestDTO dto = SqlInjectionTestDTO.builder()
                        .id(1L)
                        .name("'NONE';delete from coding_toast.test_sql_injection_table where 1=1 returning *").build();
                mapper.select(dto);
                assertThat(mapper.selectList(null).size()).isEqualTo(0);
                return null;
            });
        });

        log.info(uncategorizedDataAccessException.getMessage());

    }

    @Test
    @Transactional
    @Commit
    void preventSqlInjectionRelatedString() {

        String query = "select from table t";

        // ignore case sensitive
        Pattern compile = Pattern.compile("(?i)(execute|select|from|where|insert|update|delete|create|truncate|drop|sleep|--|;)");

        String s = compile.matcher(query).replaceAll("");
        System.out.println("s = " + s);

        List<String> list = Arrays.asList(
                "select * from users",
                "drop table users",
                "truncate table users"
        );

        Map<String, Object> stringObjectMap = mapper.testSqlInjection(list);

        System.out.println("stringObjectMap = " + stringObjectMap);

    }

}
