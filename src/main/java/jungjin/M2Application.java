package jungjin;

import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class M2Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(M2Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(M2Application.class, args);
	}

	@Bean
	public HandlebarsViewResolver viewResolver() throws Exception {
		HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
		viewResolver.setOrder(1);
		viewResolver.setPrefix("/views/");
		viewResolver.setSuffix(".hbs");
		viewResolver.setCache(false);
		//viewResolver.registerHelpers(new HandlebarsHelper());
		viewResolver.registerHelpers(StringHelpers.class);
		return viewResolver;
	}

	@Bean
	ServletRegistrationBean h2servletRegistration(){
		ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}


}

