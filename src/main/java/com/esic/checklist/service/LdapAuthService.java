package com.esic.checklist.service;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class LdapAuthService {

	private final LdapContextSource contextSource;

	@Value("${ldap.userSearchAttribute}")
	private String userSearchAttr;

	public LdapAuthService(LdapContextSource contextSource) {
		this.contextSource = contextSource;
	}

	public boolean authenticate(String username, String password) {
		try {
			
			// Construct user DN (adjust OU as per your LDAP tree)
			log.info("Authenticating user: " + username);	
            String userDn = "uid=" + username + ",ou=employeegroup,o=esic.in,o=esic";
            DirContext ctx = contextSource.getContext(userDn, password);
            log.info("Authentication successful for user: " + username);
            ctx.close();
            return true;

		} catch (NamingException e) {
			log.error("Auth failed: " + e.getMessage());
			log.info("Authentication failure for user: " + username);
			return false;
		} catch (Exception e) {
			log.error("Unexpected error: " + e.getMessage());
			log.info("Authentication failure for user: " + username);
			return false;
		}
	}
}
