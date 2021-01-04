package nic.vahan5.reg.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
public class TomcatConfigs {	
	
	@Value( "${spring.datasource.name}" )
	private String name;
	@Value( "${spring.datasource.driverClassName}" )
	private String driverClassName;
	@Value( "${spring.datasource.url}" )
	private String url;
	@Value( "${spring.datasource.username}" )
	private String username;
	@Value( "${spring.datasource.password}" )
	private String password;
	@Value( "${spring.datasource.jndiName}" )
	private String jndiName;
	
	@Bean
	public TomcatServletWebServerFactory tomcatFactory() {

		return new TomcatServletWebServerFactory() {

			/*
			 * @Override protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
			 * tomcat.enableNaming(); return super.getTomcatWebServer(tomcat); }
			 */

			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();
				resource.setType(DataSource.class.getName());
				resource.setName(name);
				resource.setProperty("driverClassName", driverClassName);
				resource.setProperty("url", url);
				resource.setProperty("username", username);
				resource.setProperty("password", password);
				context.getNamingResources().addResource(resource);
			}
		};
	}

	
	@Bean
    public DataSource getDataSource() 
    {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }	


//	@Bean
//	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
//		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//		bean.setJndiName(jndiName);
//
//		bean.setProxyInterface(DataSource.class);
//		bean.setLookupOnStartup(false);
//		bean.afterPropertiesSet();
//
//		return (DataSource) bean.getObject();
//	}
}