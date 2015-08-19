package com.hexin.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Aspect
@Controller
public class LoggingAspect {
	private static final Logger logger = LoggerFactory
			.getLogger(LoggingAspect.class);

	@Before("execution(* com.hexin.icp.dao..*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println("aaaaaaaaaaaaaaa");
		Object[] signatureArgs = joinPoint.getArgs();
		for (Object signatureArg : signatureArgs) {
			logger.info("Arg: " + signatureArg);
		}
	}

}