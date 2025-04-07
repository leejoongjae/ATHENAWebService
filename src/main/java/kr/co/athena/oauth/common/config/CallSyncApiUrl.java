package kr.co.athena.oauth.common.config;

import org.springframework.http.HttpMethod;

public enum CallSyncApiUrl implements SyncApiUrl {

	/** API URL :  */
	
	/** 소속 정보 */
	DEPT(HttpMethod.POST, "/DEPARTMENT"),
	
	/** 사용자 정보 중 학습자 정보 연동 */
	USER_LEARNER(HttpMethod.POST, "/USER_LEARNER"), 
	
	/** 사용자 정보 중 교수자 정보 연동*/
	PROFESSOR(HttpMethod.POST, "/PROFESSOR"),
	
	/** 사용자 정보 중 기타 사용자 정보 연동*/
	ETC_USER(HttpMethod.POST, "/ETC_USER"),
	
	/** 운영 과목 정보 연동*/
	COURSE(HttpMethod.POST, "/COURSE"),
	
	/** 과목운영자 정보 연동*/
	COURSE_TCH(HttpMethod.POST, "/COURSE_TCH"), 
	
	/** 학습자 정보 연동*/
	LEARNER(HttpMethod.POST, "/LEARNER"),
	
	/** 강의계획서 기본정보*/
	CRECRS_PLAN(HttpMethod.POST, "/CRECRS_PLAN"),
	
	/** 강의계획서 평가비율*/
	CRECRS_PLAN_EVAL(HttpMethod.POST, "/CRECRS_PLAN_EVAL"),
	
	/** 강의계획서 주차별 계획*/
	CRECRS_LESSON_PLAN(HttpMethod.POST, "/CRECRS_LESSON_PLAN"),
	
	/** COSS_HUB, LMS 로그인 정보 */
	LOGIN_DATA(HttpMethod.POST, "/LOGIN_LOG/%s/%s/%s"),


	; 

	private HttpMethod method;
	private String url;

	private CallSyncApiUrl(HttpMethod method, String url) {
		this.method = method;
		this.url = url;
	}

	@Override
	public HttpMethod getMethod() {
		return method;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLmsLoginLogUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
