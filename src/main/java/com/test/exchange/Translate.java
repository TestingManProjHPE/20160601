package com.test.exchange;

import java.io.IOException;
import org.apache.log4j.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.platform.config.mapping.BaseMapping;
import static com.platform.tools.ToolDateTime.*;

public class Translate {
	private static Logger log = Logger.getLogger(BaseMapping.class);
	private String baseUrl;
	private String username;
	private String password;

	public Translate(String baseUrl, String username, String password) {
		this.baseUrl = baseUrl;
		this.username = username;
		this.password = password;
	}

	public void importData() throws IOException {

		AlmRestfulServiceImpl almService = new AlmRestfulServiceImpl(baseUrl);
		// TODO 拆成鉴权,获取domains,获取其他数据


			almService.setUsername(username);
			almService.setPassword(password);

			almService.login();
			almService.siteSession();

			Domains domains = almService.getDomains();
			Domain domain = domains.getList().get(0);

			almService.setDomain(domain.getName());
			almService.setProject(domain.getProjects().getList().get(0).getName());
			// System.out.println(almService.getRequirements().body());
			// log.info(almService.getRequirements().body());

			for (Entity entity : almService.getRequirements().getList()) {

				for (int j = 0; j < entity.getList().size(); j++) {
					Record record = new Record();
					for (int i = 0; i < entity.getList().get(j).getField().size(); i++) {
						Field field = entity.getList().get(j).getField().get(i);

						String sqlValue;
						if (field.getValuelist() == null || field.getValuelist().size() <= 0) {
							sqlValue = null;
						} else {
							sqlValue = field.getValuelist().get(0).getText();
						}

						switch (field.getName()) {
						case "id":
							record.set("req_sn", sqlValue);
							break;
						case "creation-time":
							record.set("creationdate", getSqlTimestamp(parse(sqlValue, pattern_ymd)));
							break;
						case "owner":
							record.set("author", sqlValue);
							break;
						case "req-product":
							record.set("product_name", sqlValue);
							break;
						case "status":
							record.set("req_status", sqlValue);
							break;
						case "type-id":
							record.set("req_type", Integer.valueOf(sqlValue));
							break;
						case "description":
							record.set("description", sqlValue);
							break;
						case "attachment":
							record.set("attachment", sqlValue);
							break;
						// case "target-rcyc":
						// sqlPara[j][4] = field.getValuelist().get(0) ? "0"
						// : field.getValuelist().get(0);
						default:
							;
						}
					}

					if (Db.findById("requirements", "req_sn", record.get("req_sn").toString()) != null) {
						Db.deleteById("requirements", "req_sn", record.get("req_sn").toString());
					}

					Db.save("requirements", record);

				}
			}

			for (Entity DefectEntity : almService.getDefects().getList()) {

				for (int j = 0; j < DefectEntity.getList().size(); j++) {
					Record record = new Record();
					for (int i = 0; i < DefectEntity.getList().get(j).getField().size(); i++) {
						Field field = DefectEntity.getList().get(j).getField().get(i);

						String sqlValue;
						if (field.getValuelist() == null || field.getValuelist().size() <= 0) {
							sqlValue = null;
						} else {
							sqlValue = field.getValuelist().get(0).getText();
						}
//						System.out.println("----------" + field.getName().toString());
						switch (field.getName()) {
						case "id":
							record.set("defect_id", sqlValue);
							break;
						case "project":
							record.set("system", sqlValue);
							break;
						case "status":
							record.set("def_Status", sqlValue);
							break;
						case "detected-by":
							record.set("Detected_By", sqlValue);
							break;
						case "priority":
							record.set("Priority", sqlValue);
							break;
						case "creation-time":
							record.set("deteded_date", getSqlTimestamp(parse(sqlValue, pattern_ymd)));
							break;
						case "description":
							record.set("description", sqlValue);
							break;
						case "attachment":
							record.set("attachment", sqlValue);
							break;
						// // case "target-rcyc":
						// // sqlPara[j][4] = field.getValuelist().get(0) ? "0"
						// // : field.getValuelist().get(0);
						default:
							;
						}
					}

					if (Db.findById("defect", "defect_id", record.get("defect_id").toString()) != null) {
						Db.deleteById("defect", "defect_id", record.get("defect_id").toString());
					}

					Db.save("defect", record);
				}
			}

			// String sql = "insert into requirements(req_sn, creationdate,
			// author, product_name) values(?, ?, ?, ?)";
			// Db.batch(sql, sqlPara, 200);

	}

	public static void main(String[] args) {
		// Translate translate = new Translate();
		// translate.importData();
	}

}
