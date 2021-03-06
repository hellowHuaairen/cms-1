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

package com.webpagebytes.cms.engine;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import com.webpagebytes.cms.exception.WPBFileNotFoundException;
import com.webpagebytes.cms.exception.WPBIOException;
import com.webpagebytes.cms.exception.WPBReadConfigException;

import java.util.Locale;

public class LanguageLocaleManager {
	protected static String LANGUAGES_CONFIG_FILE = "META-INF/config/langs.csv"; 
	protected Map<String, Locale> langToLocales;
	protected Map<String, Locale> langAndCountriesToLocales;
	private static LanguageLocaleManager localeManager = null;
	public static LanguageLocaleManager getInstance() {
		if (localeManager == null)
		{
			localeManager = new LanguageLocaleManager();
			try {
				localeManager.loadLocalesfromFile(LANGUAGES_CONFIG_FILE);
			} catch (WPBIOException e)
			{
				localeManager = null;
				return null;
			}
		}
		return localeManager;
		
	}
	public Map<String, Locale> getSupportedLanguages()
	{
		Map<String, Locale> retValue = new HashMap<String, Locale>();
		retValue.putAll(langToLocales);
		return retValue;
	}
	
	public Map<String, Locale> getSupportedLanguagesAndCountries()
	{
		Map<String, Locale> retValue = new HashMap<String, Locale>();
		retValue.putAll(langAndCountriesToLocales);
		return retValue;
	}
	
	
	public void loadLocalesfromFile(String filePath) throws WPBIOException
	{
		langToLocales = new HashMap<String, Locale>();
		langAndCountriesToLocales = new HashMap<String, Locale>();

		try
		{
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(filePath);
			if (null == is)
			{
				throw new WPBFileNotFoundException("Could not locate:" + filePath);
			}
			BufferedReader breader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = breader.readLine()) != null)
			{
				StringTokenizer stk = new StringTokenizer(line, "*");
				String display = "";
				if (stk.hasMoreElements()) {
					display = stk.nextElement().toString().trim();
				} 
				if (stk.hasMoreTokens())
				{
					String language = stk.nextToken().trim();
					String country = "";
					if (stk.hasMoreElements())
					{
						country = stk.nextToken().trim();
					}
					if (language.length()> 0 && country.length() == 0)
					{
						String key = language.toLowerCase();
						langToLocales.put(key, new Locale(language.toLowerCase()));
						langAndCountriesToLocales.put(key, new Locale(language.toLowerCase()));
					} else if (language.length()> 0 && country.length() > 0)
					{
						String key = language.toLowerCase() + "_" + country.toUpperCase();
						langAndCountriesToLocales.put(key, new Locale(language.toLowerCase(), country.toUpperCase()));
					}					
				}
			}
			breader.close();
	        is.close();

		} catch (IOException e)
		{
			throw new WPBReadConfigException("Coult not read config file:" + filePath, e);
		}
	
	}
}
