package com.wt.twitter;
import java.util.List;

import com.semantria.interfaces.ICallbackHandler;
import com.semantria.objects.output.CollAnalyticData;
import com.semantria.objects.output.DocAnalyticData;
import com.semantria.objects.RequestArgs;
import com.semantria.objects.ResponseArgs;

public class CallbackHandler implements ICallbackHandler 
{

	@Override
	public void onResponse(Object sender, ResponseArgs responseArgs)
	{
		System.out.println("rawResponse = " + responseArgs.getMessage());
	}

	@Override
	public void onRequest(Object sender, RequestArgs requestArgs) 
	{
		System.out.println("requestUrl = " + requestArgs.getUrl());
		System.out.println("rawRequest = " + requestArgs.getMessage());
	}

	@Override
	public void onError(Object sender, ResponseArgs errorArgs) 
	{
		System.out.println("HTTP status " + Integer.toString(errorArgs.getStatus()) + " error message: " + errorArgs.getMessage());
	}

	@Override
	public void onDocsAutoResponse(Object sender, List<DocAnalyticData> processedData) 
	{
		for(DocAnalyticData aData : processedData)
		{
			System.out.println(aData.getId() + " = " + aData.getStatus());
		}

	}
	
	@Override
	public void onCollsAutoResponse(Object sender, List<CollAnalyticData> processedData) 
	{
		for(CollAnalyticData aData : processedData)
		{
			System.out.println(aData.getId() + " = " + aData.getStatus());
		}

	}
}
