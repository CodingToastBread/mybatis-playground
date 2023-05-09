package coding.toast.playground.sqlinjection;

import coding.toast.playground.sql_injection.dto.SqlInjectionTestDTO;
import coding.toast.playground.sql_injection.mapper.SqlInjectionTestMapper;
import coding.toast.playground.sql_injection.service.SqlInjectionService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

}
