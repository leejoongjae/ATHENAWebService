/** SKT oidc-client 데모소스 : https://github.com/sktston/ivp-sample-apps/tree/main/oidc-client-react */
/** 참고 URL : 
	1. https://mderriey.com/2016/08/21/openid-connect-and-js-applications-with-oidc-client-js/
	2. https://github.com/IdentityModel/oidc-client-js
*/
const settings = {
	    authority: 'https://console.myinitial.io/kc/auth/realms/vc-authn',
	    client_id: 'portal-hub',
	    popup_redirect_uri: 'https://www.coss.ac.kr/login/oidc/did', //SK가 최종으로 던져주는 엔드포인트
		redirect_uri : 'https://www.coss.ac.kr/login/oidc/did',
		silent_redirect_uri : 'https://www.coss.ac.kr/login/oidc/did',
		client_secret : '6f86c014-fe94-4c18-a284-02c09029ed5f',
		
	    response_type: 'code',
	    scope: 'openid profile vc_authn',
		prompt:'login',
	    filterProtocolClaims: true
};



const oidcUsrMgr = new Oidc.UserManager(settings);