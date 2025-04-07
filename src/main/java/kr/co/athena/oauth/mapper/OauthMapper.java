package kr.co.athena.oauth.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.athena.oauth.model.Oauth;

@Mapper
public interface OauthMapper {
	public Oauth selectOauthCmpnInfo(Oauth oauth);
}
