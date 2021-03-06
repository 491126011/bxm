package cn.kiway.bxm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//若不加这行，扫描会从当前包及子包下扫描
@ComponentScan(basePackages={"cn.kiway"})
@ServletComponentScan
@EnableTransactionManagement
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
