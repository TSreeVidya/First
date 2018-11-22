/**
 * 
 */
package com.accenture.runner.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.runner.vo.RunnerRepoVO;

/**
 * Class is used to list all runner classes
 *
 * @author vijay.venkatappa
 *
 */
@SuppressWarnings({"rawtypes"})
public class ListAllRunner {
    
 /**
 * Method is used to list all runner classes from excel
 *
 * @return List<Class>
 */
public  List<Class> getAllRunnerClasses(String runnerType){
   List<Class> testCases = new ArrayList<Class>();
   RunnerRepoReader runnerRepoReader = new RunnerRepoReader();
  	LinkedHashMap<String, RunnerRepoVO> map = runnerRepoReader.readRunnerRepo(runnerType);
    Set<String> set = map.keySet();
  	Iterator<String> it = set.iterator();
  	
  	Map<String, Queue<String>> excInfoMap = ExtentTestManager.threadExecutionInfoMap;
  	
  	while (it.hasNext()) {
  	  RunnerRepoVO vo = map.get(it.next());
  	  String className = vo.getRunnerClassName().trim();
  	  Class cl;
	  try {
		if(vo.getExecute().equalsIgnoreCase("Yes")){
		cl = Class.forName(className);
		
		/* Added for Live Reporting */
		try {
			Queue<String> q = excInfoMap.get(className);			
			if (q == null) {
				q = new PriorityQueue<>(); 
			}
			String type = null;
			if (runnerType.equals("selenium")) {
				type = "cl_";
			} else if (runnerType.equals("platform")) {
				type = "pl_";
			} else if (runnerType.equals("api")) {
				type = "ap_";
			} else {
				type = "ag_";
			}
			q.add(type+vo.getSlName());
			
			excInfoMap.put(className, q);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		 testCases.add(cl);
		}
	  } catch (ClassNotFoundException e) {
		e.printStackTrace();
	  }
  	 
	  
  	}
  	return testCases;
 }
}
