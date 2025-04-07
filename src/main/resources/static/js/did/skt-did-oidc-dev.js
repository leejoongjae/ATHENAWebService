/** SKT oidc-client 데모소스 : https://github.com/sktston/ivp-sample-apps/tree/main/oidc-client-react */
/** 참고 URL : 
	1. https://mderriey.com/2016/08/21/openid-connect-and-js-applications-with-oidc-client-js/
	2. https://github.com/IdentityModel/oidc-client-js
*/
const settings = {
	    authority: 'https://dev-console.myinitial.io/kc/auth/realms/vc-authn',
	    client_id: 'portal-hub',
	    popup_redirect_uri: 'https://www.coss.ac.kr/login/oidc/did', //SK가 최종으로 던져주는 엔드포인트
		client_secret : '9d2f9fbc-25d2-45a6-bb53-02d32ce8de33',
		
	    response_type: 'code',
	    scope: 'openid profile vc_authn',
		prompt:'login',
	    filterProtocolClaims: true
};


const oidcUsrMgr = new Oidc.UserManager(settings);