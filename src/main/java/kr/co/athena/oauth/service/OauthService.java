package kr.co.athena.oauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.athena.oauth.mapper.OauthMapper;
import kr.co.athena.oauth.model.Oauth;


@Service
public class OauthService {

	@Autowired
	private OauthMapper oauthMapper;

	private static Logger LOGGER = LoggerFactory.getLogger(OauthService.class);
	
	
	@Transactional(readOnly = true)
	public Oauth getOauthCmpnInfo(Oauth oauth)
	{
		oauth = this.oauthMapper.selectOauthCmpnInfo(oauth);
		return oauth;
	}
	
	@Transactional(readOnly = true)
	public Oauth getOauthTokenInfo(Oauth oauth)
	{
		oauth = this.oauthMapper.selectOauthTokenInfo(oauth);
		return oauth;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void editOauthUseN(Oauth oauth)
	{
		this.oauthMapper.updateOauthUseN(oauth);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void editOauthRevoke(Oauth oauth)
	{
		this.oauthMapper.updateOauthRevoke(oauth);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addOauth(Oauth oauth)
	{
		this.oauthMapper.insertOauth(oauth);
	}
		
}
