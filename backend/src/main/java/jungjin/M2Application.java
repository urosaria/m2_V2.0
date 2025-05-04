package jungjin;

import com.github.jknack.handlebars.helper.PartialHelper;
import com.github.jknack.handlebars.helper.PrecompileHelper;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import java.io.File;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.util.unit.DataSize;
import org.springframework.boot.web.servlet.MultipartConfigFactory;

@SpringBootApplication
@Configuration
public class M2Application extends SpringBootServletInitializer {
	//public static String UPLOAD_DIR = "/home/hosting_users/apppanel/tomcat/m2/ROOT/upload";
	public static String UPLOAD_DIR = "/Users/rosariayu/IdeaProjects/m2/src/main/webapp/upload";

	public static String UPLOAD_DIR_PATH;

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(new Class[] { M2Application.class });
	}

	public static void main(String[] args) {
		File f = new File(UPLOAD_DIR);
		UPLOAD_DIR_PATH = f.getAbsolutePath();
		SpringApplication.run(M2Application.class, args);
	}

	@Bean
	public HandlebarsViewResolver viewResolver() throws Exception {
		HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
		viewResolver.setOrder(1);
		viewResolver.setPrefix("/views/");
		viewResolver.setSuffix(".hbs");
		viewResolver.setCache(false);
		viewResolver.registerHelpers(new HandlebarsHelper());
		viewResolver.registerHelpers(new PaginationHelper());
		viewResolver.registerHelpers(StringHelpers.class);
		viewResolver.registerHelpers(PartialHelper.class);
		viewResolver.registerHelpers(PrecompileHelper.class);
		viewResolver.setRequestContextAttribute("requestContext");
		return viewResolver;
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofMegabytes(128));
		factory.setMaxRequestSize(DataSize.ofMegabytes(128));
		return factory.createMultipartConfig();
	}

	@Bean
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		return (MultipartResolver)multipartResolver;
	}
}
