package jungjin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import jakarta.servlet.MultipartConfigElement;
import java.io.File;

@SpringBootApplication
public class M2Application extends SpringBootServletInitializer {

	public static final String UPLOAD_DIR = "/Users/rosariayu/IdeaProjects/m2/src/main/webapp/upload";
	public static String UPLOAD_DIR_PATH;

	public static void main(String[] args) {
		File f = new File(UPLOAD_DIR);
		UPLOAD_DIR_PATH = f.getAbsolutePath();
		SpringApplication.run(M2Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(M2Application.class);
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
		return new StandardServletMultipartResolver();
	}
}