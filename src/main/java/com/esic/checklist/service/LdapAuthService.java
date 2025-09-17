package com.esic.checklist.service;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Service;

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
            String userDn = "uid=" + username + ",ou=employeegroup,o=esic.in,o=esic";
            DirContext ctx = contextSource.getContext(userDn, password);
            ctx.close();
            return true;

		} catch (NamingException e) {
			System.out.println("Auth failed: " + e.getMessage());
			return false;
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
			return false;
		}
	}
}
