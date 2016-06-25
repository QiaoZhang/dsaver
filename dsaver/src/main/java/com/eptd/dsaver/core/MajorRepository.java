package com.eptd.dsaver.core;
import java.util.ArrayList;
import java.util.List;

public class MajorRepository extends Repository{
	private ArrayList<User> contributors = new ArrayList<User>();
	
	public MajorRepository(){
		super();
	}

	public ArrayList<User> getContributors() {
		return contributors;
	}

	public void setContributors(ArrayList<User> contributors) {
		this.contributors = contributors;
	}
	
	public void addContributor(User contributor){
		this.contributors.add(contributor);
	}
	
	public void addAllContributors(List<User> contributors){
		this.contributors.addAll(contributors);
	}
}
