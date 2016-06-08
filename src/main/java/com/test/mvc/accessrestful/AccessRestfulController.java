package com.test.mvc.accessrestful;

import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseController;

import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;
import com.jfinal.aop.Before;
import com.test.exchange.Translate;

/*
 * 管理	
 * 描述：
 * 
 * /jf/test/accessRestful
 * /jf/test/accessRestful/save
 * /jf/test/accessRestful/edit
 * /jf/test/accessRestful/update
 * /jf/test/accessRestful/view
 * /jf/test/accessRestful/delete
 * /common/accessRestful/add.html
 * 
 */
//@Controller(controllerKey = "/jf/test/accessRestful")
public class AccessRestfulController extends BaseController {
	private static Logger log = Logger.getLogger(AccessRestfulController.class);

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInit.db_dataSource_main, splitPage, AccessRestful.sqlId_splitPage_from);
		render("/test/accessRestful/list.html");
	}

	/**
	 * 保存
	 */
	@Before(AccessRestfulValidator.class)
	public void save() {
		getModel(AccessRestful.class).save();
		render("/test/accessRestful/add.html");
	}

	/**
	 * 准备更新
	 */
	public void edit() {
		AccessRestful accessRestful = AccessRestful.dao.findById(getPara());
		setAttr("accessRestful", accessRestful);
		render("/test/accessRestful/update.html");
	}

	/**
	 * 更新
	 */
	@Before(AccessRestfulValidator.class)
	public void update() {
		getModel(AccessRestful.class).update();
		redirect("/jf/test/accessRestful");
	}

	/**
	 * 查看
	 */
	public void view() {
		AccessRestful accessRestful = AccessRestful.dao.findById(getPara());
		setAttr("accessRestful", accessRestful);
		render("/test/accessRestful/view.html");
	}

	/**
	 * 删除
	 */
	public void delete() {
		AccessRestfulService.service.delete("access_restful", "ids", getPara() == null ? ids : getPara());
		redirect("/jf/test/accessRestful");
	}

	/**
	 * 数据导入
	 * 
	 */
	public void importData() {

		String result = "true";

		AccessRestful accessRestful = AccessRestful.dao.findById(getPara());

		Translate translate = new Translate(accessRestful.getBaseurl(), accessRestful.getUsername(),
				accessRestful.getPassword());

		try {
			translate.importData();
		} catch (IOException e) {
			result = e.getMessage();
			log.error(accessRestful.getBaseurl() + " 导入数据错误!");
		}

		renderText("地址: " + accessRestful.getBaseurl() + " 结果是 " + result);

	}

}
