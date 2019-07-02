package project.cases.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 *  配置Druid数据源 - mysql
 */

//@Configuration  // 开启注释开启
@MapperScan(basePackages = "kim.chengcheng.goods.dao",sqlSessionFactoryRef = "masterSqlSessionFactory")
public class DruidMysqlDataSourceConfiguration {

	@Primary
	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource masterDataSource(){
		DataSource druidDataSource = new DruidDataSource();
		return druidDataSource;

	}

	@Primary
	@Bean(name = "masterSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:mybatis/MysqlMapper/*/*.xml"));
		return sessionFactoryBean.getObject();
	}
}