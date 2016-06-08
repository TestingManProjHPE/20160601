package com.test.mvc.defect;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.platform.annotation.Controller;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;

import java.util.List;

/**
 * XXX 管理 描述：
 * 
 * /jf/test/defect /jf/test/defect/save /jf/test/defect/edit
 * /jf/test/defect/update /jf/test/defect/view /jf/test/defect/delete
 * /common/defect/add.html
 * 
 */
// @Controller(controllerKey = "/jf/test/defect")
public class DefectController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(DefectController.class);

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInit.db_dataSource_main, splitPage, Defect.sqlId_splitPage_from);
		render("/test/defect/list.html");
	}

	/**
	 * 保存
	 */
	@Before(DefectValidator.class)
	public void save() {
		getModel(Defect.class).save();
		render("/test/defect/add.html");
	}

	/**
	 * 准备更新
	 */
	public void edit() {
		Defect defect = Defect.dao.findById(getPara());
		setAttr("defect", defect);
		render("/test/defect/update.html");
	}

	/**
	 * 更新
	 */
	@Before(DefectValidator.class)
	public void update() {
		getModel(Defect.class).update();
		redirect("/jf/test/defect");
	}

	/**
	 * 查看
	 */
	public void view() {
		Defect defect = Defect.dao.findById(getPara());
		setAttr("defect", defect);
		render("/test/defect/view.html");
	}

	/**
	 * 删除
	 */
	public void delete() {
		DefectService.service.delete("defect", "defect_id", getPara() == null ? ids : getPara());
		redirect("/jf/test/defect");
	}

	public void reportone() {
		List<Record> data = Db.find("select count(*) as 数量,detected_by as 人员 from defect group by detected_by");
		renderJson("testdata", data);
	}

}
