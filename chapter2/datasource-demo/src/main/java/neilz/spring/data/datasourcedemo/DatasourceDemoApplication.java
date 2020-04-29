package neilz.spring.data.datasourcedemo;

import jdk.internal.instrumentation.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *  @Slf4j lombok注解 下面代码中直接使用log 打印日志 省略了自己定义log
 */
@SpringBootApplication
@Slf4j
public class DatasourceDemoApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DatasourceDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		showConnection();
		showData();
	}

	/**
	 * 使用的内置H2数据库 连接spring默认的
	 * SpringBoot会默认加载了类路径下的schema.sql和data.sql脚本。
	 *
	 * HikariProxyConnection@1079167170 wrapping conn0: url=jdbc:h2:mem:testdb user=SA
	 * {ID=1, BAR=aaa}
	 * {ID=2, BAR=bbb}
	 *
	 */
	private void showData() {
		jdbcTemplate.queryForList("select * from FOO").forEach(row -> log.info(row.toString()));
	}

	private void showConnection() throws SQLException {
		log.info(dataSource.toString());
		Connection conn = dataSource.getConnection();
		log.info(conn.toString());
		conn.close();
	}
}
