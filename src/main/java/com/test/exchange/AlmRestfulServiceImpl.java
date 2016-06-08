package com.test.exchange;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlmRestfulServiceImpl {
	@SuppressWarnings("unused")
	private String baseUrl;
	private String username;
	private String password;
	private String answerChar = "y";
	private String domain;
	private String project;
	RestfulFactory retroFactory;

	public AlmRestfulServiceImpl(String baseUrl) {
		this.baseUrl = baseUrl;
		this.retroFactory = new RestfulFactory(baseUrl);
	}

	public void isAuthenticated() {

		Call<String> call = retroFactory.getScalarsService().isAuthenticated();

		call.enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {
				if (response.isSuccessful() && response.errorBody() == null) {
					System.out.println("str:" + response.body().toString());
				} else {
					String authenticationHeader = response.headers().get("WWW-Authenticate");
					String newUrl = authenticationHeader.substring(authenticationHeader.lastIndexOf("=") + 1);
					newUrl = newUrl.replace("\"", "");
					newUrl += "/authenticate";

					System.out.println(newUrl);
					// ret = newUrl;
				}
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {
				System.out.println("error:" + t.getMessage());
			}
		});

	}

	public void login() throws IOException {
		byte[] credBytes = (username + ":" + password).getBytes();
		String credEncodedString = "Basic " + Base64Encoder.encode(credBytes);
		Call<String> call = retroFactory.getScalarsService().login(credEncodedString);
		call.execute();
	}

	public void siteSession() throws IOException {
		String xml = ("<session-parameters><client-type>REST Client</client-type></session-parameters>");
		Call<String> call = retroFactory.getScalarsService().siteSession(xml);
		call.execute();
	}

	public Domains getDomains() throws IOException {
		Call<Domains> call = retroFactory.getXmlService().getDomains(answerChar);
		Response<Domains> response = call.execute();
		Domains domains = response.body();
		return domains;
	}

	 public Entities getDefects() throws IOException {
	 Call<Entities> call = retroFactory.getXmlService().getDefects(domain,
	 project);
	 Response<Entities> response = call.execute();
	 Entities defects = response.body();
	 return defects;
	 }

//	public void getDefects() throws IOException {
//		Call<String> call = retroFactory.getScalarsService().getDefects(domain, project);
//		Response<String> response = call.execute();
//		System.out.println(response.body());
//	}

	public Entities getRequirements() throws IOException {
		Call<Entities> call = retroFactory.getXmlService().getRequirements(domain, project);
		Response<Entities> response = call.execute();
		Entities Requirements = response.body();
		return Requirements;
	}

	public Entities getTests() throws IOException {
		Call<Entities> call = retroFactory.getXmlService().getTests(domain, project);
		Response<Entities> response = call.execute();
		Entities tests = response.body();
		return tests;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setProject(String project) {
		this.project = project;
	}

}
