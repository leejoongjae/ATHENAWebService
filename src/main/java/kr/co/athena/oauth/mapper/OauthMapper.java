package kr.co.athena.oauth.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.athena.oauth.model.Oauth;

@Mapper
public interface OauthMapper {
	public Oauth selectOauthCmpnInfo(Oauth oauth);
	
	public Oauth selectOauthTokenInfo(Oauth oauth);
	
	public void updateOauthUseN(Oauth oauth);
	
	public void updateOauthRevoke(Oauth oauth);
	
	public void insertOauth(Oauth oauth);
}
