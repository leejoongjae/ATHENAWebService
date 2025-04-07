package kr.co.athena.oauth.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Alias("page")
public class Page implements Serializable {

	private static final long serialVersionUID = -3884746632899048605L;
	
	private Integer page = 1;
	
	private Integer pageSize = 10;	//화면에 표시할 ROW수
	
	private Integer pageStart;
	
	private int resultCnt = 0;	//조회결과 갯수
	private int pageCnt = 10;	//조회 결과에 따른 화면에 출력할 페이지의 갯수
	private int totalPageCnt;	//조회 결과 총 페이지 갯수
	
	private int startPageNum;	//페이징 화면 출력시 시작 페이지 번호
	private int endPageNum;		//페이징 화면 출력시 끝 페이지 번호
	
	public int getPage()
	{
		if (this.getTotalPageCnt() < this.page)
		{
			this.page = this.getTotalPageCnt();
		}
		return this.page;
	}
	
	public int getStartPageNum()
	{
		if (this.resultCnt > 0)
		{
			return ((this.page - 1) / this.getPageCnt()) * this.getPageCnt() + 1;
		}
		return 0;
	}
	
	public int getEndPageNum()
	{
		if (this.resultCnt > 0)
		{
			if (this.endPageNum > this.getTotalPageCnt())
			{
				return this.getTotalPageCnt();
			}			
			return this.getStartPageNum() + this.getPageCnt() - 1;
		}
		return 0;
	}
	
	public int getPageStart() {
		return (this.page - 1) * this.pageSize;
	}
	
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	
	public int getTotalPageCnt()
	{
		if (this.resultCnt > 0)
		{
			return (int)Math.ceil((double)this.resultCnt / (int)this.pageSize);
		}
		return 0;
	}

}
