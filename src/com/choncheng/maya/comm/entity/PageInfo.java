package com.choncheng.maya.comm.entity;

public class PageInfo {
	private int page;//":"2"," ��ǰҳ
	private int totalcount;//":"17",������
	private int numberofpage;//":"20" һҳ������
	
	
	public int getNumberofpage() {
		return numberofpage;
	}
	public int getPage() {
		return page;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setNumberofpage(int numberofpage) {
		this.numberofpage = numberofpage;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
}
