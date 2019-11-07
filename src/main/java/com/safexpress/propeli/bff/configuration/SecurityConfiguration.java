package com.safexpress.propeli.bff.configuration;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.safexpress.propeli.authorization.filter.AuthFilter;
import com.safexpress.propeli.authorization.filter.BranchAuthFilter;
import com.safexpress.propeli.bff.realm.CustomRealm;
import com.safexpress.propeli.cache.manager.AuthnzCacheManagerImpl;
import com.safexpress.propeli.security.filter.SecurityFilter;
import com.safexpress.propeli.security.filter.SessionFilter;
import com.safexpress.propeli.security.util.AuthUtil;

/**
 * @author Arun Singh
 * @author Ajay Singh Negi
 * @ClassType Configuration
 *@Description Beans configuration for security and authorization
 */
@Configuration

public class SecurityConfiguration {

	@Bean("redisCacheManager")
	@Primary
	public CacheManager cacheManager() {
		return new AuthnzCacheManagerImpl();
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public Realm realm() {
		CustomRealm realm = new CustomRealm();
		realm.setAuthorizationCachingEnabled(true);
		realm.setAuthenticationCachingEnabled(true);
		realm.setName("propeli");
		return realm;
	}

	@Bean
	public Authorizer authorizer() {
		CustomRealm realm = new CustomRealm();
		realm.setAuthenticationCachingEnabled(true);
		realm.setAuthorizationCachingEnabled(true);
		realm.setCacheManager(cacheManager());
		return realm;
	}

//	@Bean
//	public DefaultWebSecurityManager authenticator() {
//		DefaultWebSecurityManager securityMgr = new DefaultWebSecurityManager(realm());
//		securityMgr.setCacheManager(cacheManager());
//		securityMgr.setSessionManager(sessionManager());
//		return securityMgr;
//	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityMgr = new DefaultWebSecurityManager(realm());
		securityMgr.setCacheManager(cacheManager());
		securityMgr.setSessionManager(sessionManager());
		securityMgr.setAuthorizer(authorizer());
		return securityMgr;
	}

	@Bean
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setCacheManager(cacheManager());
		sessionManager.setSessionDAO(sessionDAO());
		return sessionManager;
	}

	@Bean
	public SecurityFilter securityFilter() {
		return new SecurityFilter();
	}

	@Bean
	public AuthFilter authFilter() {
		return new AuthFilter();
	}

	@Bean
	public SessionDAO sessionDAO() {
		return new EnterpriseCacheSessionDAO();
	}


	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean= new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		Map<String, Filter> filters= new HashMap<>();
		filters.put("sessionFilter", new SessionFilter());
		filters.put("branchAuthFilter", new BranchAuthFilter());
		shiroFilterFactoryBean.setFilters(filters);
		Map<String, String> filterChainDefinitionMap= new HashMap<>();
		// here url pattern is as per shiro
		filterChainDefinitionMap.put("/**", "anon");
		filterChainDefinitionMap.put("/secure/**", "sessionFilter,branchAuthFilter");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public FilterRegistrationBean<SecurityFilter> securityFilterRegisterationBean() {

	    FilterRegistrationBean<SecurityFilter> registration = new FilterRegistrationBean<>();
	    registration.setFilter(securityFilter());
	    registration.setName(AuthUtil.filterTypeEnum.SECURITY_FILTER.value());
	    // filter order should be set as per the requirement
	    registration.setOrder(2);
	    return registration;
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> authFilterRegisterationBean() {

	    FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
	    registration.setFilter(authFilter());
	    registration.addUrlPatterns("/secure/*"); // url pattern is as per servlet specification
	    registration.setName(AuthUtil.filterTypeEnum.AUTH_FILTER.value());
	    // filter order should be set as per the requirement
	    registration.setOrder(1);
	    return registration;
	}


}
