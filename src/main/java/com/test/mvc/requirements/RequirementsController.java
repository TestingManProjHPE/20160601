package com.test.mvc.requirements;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseController;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * XXX 管理	
 * 描述：
 * 
 * /jf/test/requirements
 * /jf/test/requirements/save
 * /jf/test/requirements/edit
 * /jf/test/requirements/update
 * /jf/test/requirements/view
 * /jf/test/requirements/delete
 * /common/requirements/add.html
 * 
 */
//@Controller(controllerKey = "/jf/test/requirements")
public class RequirementsController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(RequirementsController.class);
	
	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInit.db_dataSource_main, splitPage, Requirements.sqlId_splitPage_from);
		render("/test/requirements/list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(RequirementsValidator.class)
	public void add() {
		getModel(Requirements.class).save();
		render("/test/requirements/add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {

		Requirements requirements = Requirements.dao.findById(getPara());
		setAttr("requirements", requirements);
		render("/test/requirements/update.html");
	}
	
	/**
	 * 更新
	 */
	@Before(RequirementsValidator.class)
	public void update() {
		getModel(Requirements.class).update();
		redirect("/jf/test/requirements");
	}

	/**
	 * 查看
	 */
	public void view() {
		Requirements requirements = Requirements.dao.findById(getPara());
		setAttr("requirements", requirements);
		render("/test/requirements/view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
//		RequirementsService.service.delete("requirements", "req_sn", getPara() == null ? ids : getPara());
//		redirect("/jf/test/requirements");
	}

	public void reportone() {
		List<Record> data = Db.find("select count(*) as 数量,creationdate as 日期 from requirements group by creationdate");
		renderJson("testdata",data);
	}

	public void reqcover() {
		long total = Db.queryLong("select count(*) from requirements");
		long covered = Db.queryLong("select count(*) from requirements where req_status != 'Not Covered' or req_status is not null");
		Record record = new Record();
		record.set("数量",covered);
		record.set("类型","覆盖数");
		Record recordtwo = new Record();
		recordtwo.set("数量",total-covered);
		recordtwo.set("类型","非覆盖数");
		List<Record> lor = new ArrayList<>();
		lor.add(record);
		lor.add(recordtwo);
		renderJson("data",lor);
	}

	public void reqpass() {
		long total = Db.queryLong("select count(*) from requirements");
		long pass = Db.queryLong("select count(*) from requirements where req_status = 'Passed'");
		Record record = new Record();
		record.set("数量",pass);
		record.set("类型","通过数");
		Record recordtwo = new Record();
		recordtwo.set("数量",total-pass);
		recordtwo.set("类型","非通过数");
		List<Record> lor = new ArrayList<>();
		lor.add(record);
		lor.add(recordtwo);
		renderJson("data",lor);
	}
	
}
