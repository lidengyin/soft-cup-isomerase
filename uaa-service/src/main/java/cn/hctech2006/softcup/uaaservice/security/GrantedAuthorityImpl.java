package cn.hctech2006.softcup.uaaservice.security;

import org.springframework.security.core.GrantedAuthority;
//权限f封装
public class GrantedAuthorityImpl implements GrantedAuthority {
    String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
