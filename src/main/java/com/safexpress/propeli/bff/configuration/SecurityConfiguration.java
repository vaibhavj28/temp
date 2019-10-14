/*
 * package com.safexpress.propeli.bff.configuration;
 * 
 * 
 * import java.util.HashMap; import java.util.Map;
 * 
 * import javax.servlet.Filter;
 * 
 * import org.apache.shiro.authz.Authorizer; import
 * org.apache.shiro.cache.CacheManager; import org.apache.shiro.realm.Realm;
 * import org.apache.shiro.session.mgt.SessionManager; import
 * org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO; import
 * org.apache.shiro.session.mgt.eis.SessionDAO; import
 * org.apache.shiro.spring.web.ShiroFilterFactoryBean; import
 * org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition; import
 * org.apache.shiro.spring.web.config.ShiroFilterChainDefinition; import
 * org.apache.shiro.web.mgt.DefaultWebSecurityManager; import
 * org.apache.shiro.web.session.mgt.DefaultWebSessionManager; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.ComponentScan; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.Primary;
 * 
 * import com.safexpress.propeli.bff.realm.CustomRealm; import
 * com.safexpress.propeli.cache.manager.AuthnzCacheManagerImpl; import
 * com.safexpress.propeli.security.filter.SessionFilter;
 * 
 *//**
	 * @author Arun Singh
	 * @ClassType Configuration
	 * @Description Beans configuration for security and authorization
	 *//*
		 * @Configuration
		 * 
		 * public class SecurityConfiguration {
		 * 
		 * @Bean("redisCacheManager")
		 * 
		 * @Primary public CacheManager cacheManager() { return new
		 * AuthnzCacheManagerImpl(); }
		 * 
		 * @Bean public Realm realm() { CustomRealm realm = new CustomRealm();
		 * realm.setAuthorizationCachingEnabled(true);
		 * realm.setAuthenticationCachingEnabled(true); realm.setName("propeli"); return
		 * realm; }
		 * 
		 * @Bean public Authorizer authorizer() { CustomRealm realm = new CustomRealm();
		 * realm.setAuthenticationCachingEnabled(true);
		 * realm.setAuthorizationCachingEnabled(true);
		 * realm.setCacheManager(cacheManager()); return realm; }
		 * 
		 * @Bean public DefaultWebSecurityManager authenticator() {
		 * DefaultWebSecurityManager securityMgr = new
		 * DefaultWebSecurityManager(realm());
		 * securityMgr.setCacheManager(cacheManager());
		 * securityMgr.setSessionManager(sessionManager()); return securityMgr; }
		 * 
		 * @Bean public DefaultWebSecurityManager securityManager() {
		 * DefaultWebSecurityManager securityMgr = new
		 * DefaultWebSecurityManager(realm());
		 * securityMgr.setCacheManager(cacheManager());
		 * securityMgr.setSessionManager(sessionManager()); return securityMgr; }
		 * 
		 * @Bean public SessionManager sessionManager() { DefaultWebSessionManager
		 * sessionManager = new DefaultWebSessionManager();
		 * sessionManager.setCacheManager(cacheManager());
		 * sessionManager.setSessionDAO(sessionDAO());
		 * //sessionManager.setSessionIdCookie(cookie()); return sessionManager; }
		 * 
		 * @Bean public SessionFilter sessionFilter() { return new SessionFilter(); }
		 * 
		 * 
		 * @Bean public Cookie cookie() { Cookie cookie = new
		 * org.apache.shiro.web.servlet.SimpleCookie(); cookie.setName("JSESSIONID");
		 * return cookie; }
		 * 
		 * @Bean public SessionDAO sessionDAO() { SessionDAO sessionDAO = new
		 * EnterpriseCacheSessionDAO(); return sessionDAO; }
		 * 
		 * @Bean public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		 * DefaultShiroFilterChainDefinition filter = new
		 * DefaultShiroFilterChainDefinition(); filter.addPathDefinition("/**", "anon");
		 * filter.addPathDefinition("/secure", "authc");
		 * filter.addPathDefinition("/secure", "sessionFilter"); return filter; }
		 * 
		 * @Bean public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		 * ShiroFilterFactoryBean shiroFilterFactoryBean= new ShiroFilterFactoryBean();
		 * shiroFilterFactoryBean.setSecurityManager(securityManager());
		 * 
		 * Map<String, Filter> filters= new HashMap<>(); filters.put("sessionFilter",
		 * sessionFilter()); shiroFilterFactoryBean.setFilters(filters); return
		 * shiroFilterFactoryBean; }
		 * 
		 * }
		 */