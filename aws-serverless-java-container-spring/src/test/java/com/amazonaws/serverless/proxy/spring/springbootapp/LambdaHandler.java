package com.amazonaws.serverless.proxy.spring.springbootapp;


import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.springbootapp.TestApplication;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.springframework.web.context.support.XmlWebApplicationContext;


public class LambdaHandler
        implements RequestHandler<AwsProxyRequest, AwsProxyResponse>
{
    SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    boolean isinitialized = false;

    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context)
    {
        if (!isinitialized) {
            isinitialized = true;
            try {
                handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(TestApplication.class);
            } catch (ContainerInitializationException e) {
                e.printStackTrace();
                return null;
            }
        }
        AwsProxyResponse res = handler.proxy(awsProxyRequest, context);
        return res;
    }
}

