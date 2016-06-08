package com.test.exchange;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlmRestfulService {
	@GET("rest/is-authenticated")
	Call<String> isAuthenticated();

	// 登录鉴权，需要登录的用户名和密码
	@GET("authentication-point/authenticate")
	Call<String> login(@Header("Authorization") String authorization);

	// 设置session，alm系统将返回QCSession cookie 和 XSRF-TOKEN
	// cookie信息，并将cookie添加到cookieStore中
	@POST("rest/site-session")
	Call<String> siteSession(@Body String xmlString);

	// alm信息的domains信息，这里同时提取projects信息
	@GET("rest/domains")
	Call<Domains> getDomains(@Query(value = "include-projects-info") String queryString);

	// 获取缺陷信息，并返回Entities对象
	@GET("rest/domains/{domain}/projects/{project}/defects")
	Call<Entities> getDefects(@Path("domain") String domain, @Path("project") String project);
	
//	@GET("rest/domains/{domain}/projects/{project}/defects")
//	Call<String> getDefects(@Path("domain") String domain, @Path("project") String project);
	
	// 获取需求信息，并返回Entities对象
	@GET("rest/domains/{domain}/projects/{project}/requirements")
	Call<Entities> getRequirements(@Path("domain") String domain, @Path("project") String project);

	// 获取版本信息
	@GET("rest/domains/{domain}/projects/{project}/releases")
	Call<Entities> getReleases(@Path("domain") String domain, @Path("project") String project);

	// 获取测试信息
	@GET("rest/domains/{domain}/projects/{project}/tests")
	Call<Entities> getTests(@Path("domain") String domain, @Path("project") String project);

}
