package coding.toast.playground.batch;

import coding.toast.playground.batch.dto.ExcelLikeDTO;
import coding.toast.playground.batch.service.DDLService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@SpringBootTest
public class DDLBatchWithServiceTest {

    @Autowired
    private DDLService ddlService;

    @Test
    @Transactional
    @Commit
    void createServiceTest() {


        ExcelLikeDTO map = ExcelLikeDTO.builder()
                .schemaName("coding_toast")
                .tableName("user_test")
                .headerColumnList(List.of("id", "attr1", "attr2", "attr3", "attr4"))
                .bodyColumnList(List.of(
                        List.of("New York", 1L, 2L, 3L, 4L),
                        List.of("California", 2L, 3L, 4L, 5L),
                        List.of("Texas", 123L, 123L, 123L, 123L),
                        List.of("Arizona", 99L, 23L, 12L, 51L),
                        List.of("Indiana", 41L, 12L, 0L, 99L),
                        List.of("Washington", 511L, 232L, 32L, 41L),
                        List.of("Oklahoma", 11L, 22L, 33L, 45L)
                ))
                .build();


        ddlService.createTable(map);

    }

}
