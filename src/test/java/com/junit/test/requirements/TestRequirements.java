package com.junit.test.requirements;

import java.sql.SQLException;

import org.junit.Test;

import com.junit.TestBase;
import com.test.mvc.requirements.RequirementsService;

/**
 * 博客单元测试类
 * 
 * @author 董华健 dongcb678@163.com
 */
public class TestRequirements extends TestBase {

	@Test
	public void delete() throws SQLException {
		RequirementsService.service.delete("requirements", "req_sn", "ids001,ids002");
	}

}
