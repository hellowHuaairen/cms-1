/*
 *   Copyright 2014 Webpagebytes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.webpagebytes.cms.appinterfaces;

import java.util.Map;
import java.util.Set;

public class WPBModel {
	
	public static final String GLOBALS_KEY = "wpbGlobals";
	public static final String REQUEST_KEY = "wpbRequest";
	public static final String PAGE_PARAMETERS_KEY = "wpbPageParams";
	public static final String URI_PARAMETERS_KEY = "wpbUriParams";
	
	public static final String APPLICATION_CONTROLLER_MODEL_KEY = "wpbAppModel";
	
	public static final String LOCALE_KEY = "wpbLocale";
	public static final String LOCALE_LANGUAGE_KEY = "WPB_LOCALE_LANGUAGE";
	public static final String LOCALE_COUNTRY_KEY = "WPB_LOCALE_COUNTRY";
	public static final String LOCALE_MESSAGES = "wpbMessages";
	public static final String MODULE_DIRECTIVE = "wpbModule";
	public static final String IMAGE_DIRECTIVE = "wpbImage";
	public static final String ARTICLE_DIRECTIVE = "wpbArticle";
	public static final String TEXT_FORMAT_DIRECTIVE = "wpbFormatText";
	public static final String FORMAT_TEXT_METHOD = "wpbFormatText";
	
	
	public static final String GLOBAL_PROTOCOL = "WPB_GLOBAL_PROTOCOL";
	public static final String GLOBAL_DOMAIN = "WPB_GLOBAL_DOMAIN";
	public static final String GLOBAL_CONTEXT_PATH = "WPB_GLOBAL_CONTEXT_PATH";
	public static final String GLOBAL_BASE_URL = "WPB_GLOBAL_BASE_URL";
	

	protected WPBCmsModel cmsModel = new WPBCmsModel();
	protected WPBApplicationModel applicationModel = new WPBApplicationModel();
	
	public WPBCmsModel getCmsModel()
	{
		return cmsModel;
	}
	public WPBApplicationModel getCmsCustomModel()
	{
		return applicationModel;
	}
	
	public void transferModel(Map<String, Object> rootObject)
	{
		Set<String> keys = cmsModel.keySet();
		for(String key: keys)
		{
			rootObject.put(key, cmsModel.get(key));
		}
				
	}
}
