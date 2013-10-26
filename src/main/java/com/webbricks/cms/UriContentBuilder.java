package com.webbricks.cms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webbricks.appinterfaces.IPageModelProvider;
import com.webbricks.appinterfaces.IWBRequestHandler;
import com.webbricks.appinterfaces.WBForward;
import com.webbricks.cache.WBCacheInstances;
import com.webbricks.cmsdata.WBProject;
import com.webbricks.cmsdata.WBUri;
import com.webbricks.exception.WBException;

public class UriContentBuilder extends BaseModelProvider {

	private WBCacheInstances cacheInstances;
	private Map<String, IWBRequestHandler> customControllers;
	
	UriContentBuilder(WBCacheInstances cacheInstances)
	{
		super(cacheInstances);
		customControllers = new HashMap<String, IWBRequestHandler>();
	}
	
	public void initialize()
	{
		
	}
	
	public Map<String, Object> buildUriContent(HttpServletRequest request, HttpServletResponse response,
			URLMatcherResult urlMatcherResult, 
			WBUri wburi, 
			WBProject project, WBForward forward) throws WBException
	{
		String controllerClassName = wburi.getControllerClass();
		if (controllerClassName !=null && controllerClassName.length()>0)
		{
			Map<String, Object> controllerModel = new HashMap<String, Object>();
			IWBRequestHandler controllerInst = null;
			if (customControllers.containsKey(controllerClassName))
			{
				controllerInst = customControllers.get(controllerClassName);
			} else
			{
				try {
				controllerInst = (IWBRequestHandler) Class.forName(controllerClassName).newInstance();
				} catch (Exception e) { throw new WBException("Cannot instantiate page controller " + controllerClassName, e); }			
			}
			if (controllerInst != null)
			{
				Map<String, Object> model = getControllerModel(request, urlMatcherResult, wburi.getExternalKey(), project);
				controllerInst.handleRequest(request, response, model, forward);
				return model;
			}			
		}
		return null;

	}

}
