package com.cf.tradeprocessor.config;

import java.io.IOException;
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
@PropertySource("classpath:/mongo.properties")
public class MongoConfig {
	@Value("${mongo.host}")
	public String mongoHost;
	@Value("${mongo.port}")
	public String mongoPort;
	@Value("${mongo.database}")
	public String mongoDatabase;

	private int port;
	private MongodExecutable mongodExecutable;
	private MongodProcess mongod;

	@PostConstruct
	public void init() throws UnknownHostException, IOException {
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
		return new MongoClient(mongoHost, port);
	}

	@Bean 
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), mongoDatabase);
	}
}
