package coding.toast.playground.nulltest;

import coding.toast.playground.nulltest.dto.NullTestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest//(properties = "mybatis.configuration.jdbc-type-for-null=varchar")
class NullTestMapperTest {
	
	// 1. without [parameterType="long"] attribute in select tag
	//  1-1. jdbc-type-for-null: varchar ==> error
	//  1-2. jdbc-type-for-null: null ==> works
	
	// 2. with [parameterType="long"] attribute in select tag
	//  2-1. jdbc-type-for-null: varchar ==> works
	//  2-2. jdbc-type-for-null: null ==> works
	
	@Autowired
	private NullTestMapper mapper;
	
	@Test
	void findWithParamType() {
		NullTestDTO found = mapper.findWithParamType(1L);
		System.out.println("find with value = " + found);
		
		// ok, what about null?
		found = mapper.findWithParamType(null);
		System.out.println("find with null param = " + found);
	}
	
	
	@Test
	void findWithOutParamType() {
		NullTestDTO found = mapper.findWithOutParamType(1L);
		System.out.println("find with value = " + found);
		
		// ok, what about null?
		found = mapper.findWithOutParamType(null);
		System.out.println("find with null param = " + found);
	}
	
	@Test
	void findWithOutParamTypeButHasJdbcType() {
		NullTestDTO found = mapper.findWithOutParamTypeButHasJdbcType(1L);
		System.out.println("find with value = " + found);
		
		// ok, what about null?
		found = mapper.findWithOutParamTypeButHasJdbcType(null);
		System.out.println("find with null param = " + found);
	}
	
	@Test
	void findWithParamTypeString() {
		NullTestDTO found = mapper.findWithParamTypeString("Charlie Puth");
		System.out.println("find with value = " + found);
		
		// ok, what about null?
		found = mapper.findWithParamTypeString(null);
		System.out.println("find with null param = " + found);
	}
	@Test
	void findWithOutParamTypeString() {
		NullTestDTO found = mapper.findWithOutParamTypeString("Charlie Puth");
		System.out.println("find with value = " + found);
		
		// ok, what about null?
		found = mapper.findWithOutParamTypeString(null);
		System.out.println("find with null param = " + found);
	}
	
	@Test
	void findWithOutParamTypeButHasJdbcTypeString() {
		NullTestDTO found = mapper.findWithOutParamTypeButHasJdbcTypeString("Charlie Puth");
		System.out.println("find with value = " + found);
		
		// ok, what about null?
		found = mapper.findWithOutParamTypeButHasJdbcTypeString(null);
		System.out.println("find with null param = " + found);
	}
	
	
	@Test
	void insertTest() {
		NullTestDTO dto = new NullTestDTO();
		
		// normal insert
		dto.setName("daily Code");
		dto.setAge(14);
		mapper.insert(dto);
		System.out.println("done1");
		
		// null field include
		dto.setAge(null);
		mapper.insert(dto);
		System.out.println("done2");

		// null parameter
		mapper.insert(null);
		System.out.println("done3");
	}
}