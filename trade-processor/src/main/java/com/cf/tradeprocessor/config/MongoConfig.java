package com.cf.tradeprocessor.config;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
@EnableMongoRepositories(basePackages = "com.cf.tradeprocessor.repository.mongo")
@PropertySource("classpath:/app.properties")
public class MongoConfig {
	@Value("${mongo.port}")
	public String mongoPort;
	@Value("${mongo.database}")
	public String mongoDatabase;
	@Value("${proxy.host}")
	public String proxyHost;
	@Value("${proxy.port}")
	public String proxyPort;
	@Value("${proxy.user}")
	public String proxyUser;
	@Value("${proxy.password}")
	public String proxyPassword;

	private int port;
	private MongodExecutable mongodExecutable;
	private MongodProcess mongod;

	public void configureProxy() {
		if (proxyHost.trim().equals("") || proxyPort.trim().equals("")) // exiting because there's no proxy 
			return;
		
		Authenticator.setDefault(
		   new Authenticator() {
		      public PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication(
		               proxyUser, proxyPassword.toCharArray());
		      }
		   }
		);

		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyPort);
		System.setProperty("http.proxyUser", proxyUser);
		System.setProperty("http.proxyPassword", proxyPassword);		
	}
	
	@PostConstruct
	public void init() throws UnknownHostException, IOException {
		configureProxy();
		
		port = Integer.parseInt(mongoPort);
		
		MongodStarter starter = MongodStarter.getDefaultInstance();

		IMongodConfig mongodConfig = new MongodConfigBuilder()
				.version(Version.Main.PRODUCTION)
				.net(new Net(port, Network.localhostIsIPv6())).build();
		
		mongodExecutable = starter.prepare(mongodConfig);
		mongod = mongodExecutable.start();
	}

	@PreDestroy
	public void destroy() {
		mongod.stop();
		mongodExecutable.stop();
	}

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
	@Bean
	public Mongo mongo() throws IOException {
		return new MongoClient("localhost", port);
	}

	@Bean 
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), mongoDatabase);
	}
}
