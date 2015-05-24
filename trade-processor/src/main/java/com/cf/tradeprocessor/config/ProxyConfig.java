package com.cf.tradeprocessor.config;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/proxy.properties")
public class ProxyConfig {
	@Value("${proxy.host}")
	public String proxyHost;
	@Value("${proxy.port}")
	public String proxyPort;
	@Value("${proxy.user}")
	public String proxyUser;
	@Value("${proxy.password}")
	public String proxyPassword;

	@PostConstruct
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
	
}
