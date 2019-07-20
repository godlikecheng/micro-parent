package project.cases.druid;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 *   配置Druid数据源 - oracle
 */
//@Configuration  // 开启注释开启
@MapperScan(
		basePackages = "kim.chengcheng.goods.daoOracle",
		sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class DruidOracleDataSourceCongfiguration {

	@Bean(name = "slaveDataSource")
	@ConfigurationProperties(prefix = "spring.datasource-oracle")
	public DataSource slaveDataSource(){
		DataSource druidDataSource = DataSourceBuilder.create().build();
		return druidDataSource;

	}

	@Bean(name = "slaveSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:mybatis/OracleMapper/*.xml"));
		return sessionFactoryBean.getObject();
	}


}
