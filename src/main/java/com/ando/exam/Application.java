package com.ando.exam;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.ando.exam.base.BaseRepositoryImpl;
import com.ando.exam.sys.config.Constant;

@SpringBootApplication(scanBasePackages = "com.ando.exam")
@EnableJpaRepositories(basePackages = {"com.ando.exam"}, repositoryBaseClass = BaseRepositoryImpl.class)
public class Application {
	public static ConfigurableApplicationContext run;

	public static void main(String[] args) {

		Application.run = SpringApplication.run(Application.class, args);

		ThymeleafViewResolver viewResolver = Application.run.getBean(ThymeleafViewResolver.class);
		if (viewResolver != null) {
			try {
				TomcatServletWebServerFactory tomcatServletWebServerFactory = (TomcatServletWebServerFactory) Application.run
						.getBean("tomcatServletWebServerFactory");
				String host = InetAddress.getLocalHost().getHostAddress();
				int port = tomcatServletWebServerFactory.getPort();
				System.out.println("http://" + host + ":" + port + Constant.APP_INDEX);
				Map<String, Object> vars = new HashMap<String, Object>();
				vars.put("baseUrl", "http://" + host + ":" + port);
				viewResolver.setStaticVariables(vars);
				viewResolver.setStaticVariables(Constant.mo);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}

}