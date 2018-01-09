package jungjin;

import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.script.ScriptTemplateViewResolver;

@SpringBootApplication
public class M2Application {

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
}

