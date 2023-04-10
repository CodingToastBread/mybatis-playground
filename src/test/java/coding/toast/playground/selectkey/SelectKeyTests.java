package coding.toast.playground.selectkey;

import coding.toast.playground.selectkey.domain.TestInsertDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class SelectKeyTests {

	@Autowired
	private TestInsertMapper mapper;
	
	@Test
	@Transactional
	@Commit
	void testMe() {
		
		TestInsertDTO testInsertDTO = new TestInsertDTO();
		testInsertDTO.setName("daily");
		mapper.insertWithSelectKey(testInsertDTO);
	
	}
}
