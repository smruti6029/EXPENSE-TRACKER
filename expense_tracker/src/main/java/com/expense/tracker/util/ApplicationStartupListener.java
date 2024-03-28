package com.expense.tracker.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.tracker.model.ApiEndpoint;
import com.expense.tracker.repository.ApiEndpointRepository;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private ApiEndpointRepository apiEndpointRepository;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		List<ApiEndpoint> existingEndpoints = apiEndpointRepository.findAll();
		Set<String> existingUrls = existingEndpoints.stream().map(ApiEndpoint::getUrl).collect(Collectors.toSet());

		ApplicationContext context = event.getApplicationContext();
		String[] beanNames = context.getBeanNamesForAnnotation(RestController.class);

		for (String beanName : beanNames) {
			Object bean = context.getBean(beanName);
			Class<?> beanType = bean.getClass();
			RequestMapping classRequestMapping = AnnotationUtils.findAnnotation(beanType, RequestMapping.class);
			if (classRequestMapping != null && classRequestMapping.value().length > 0) { // Check if the value array is
																							// not empty
				String classBaseUrl = classRequestMapping.value()[0];
				Method[] methods = beanType.getDeclaredMethods();
				for (Method method : methods) {
					PostMapping methodPostMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
					GetMapping methodGetMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
					if (methodPostMapping != null || methodGetMapping != null) {
						String methodUrl = classBaseUrl;
						if (methodPostMapping != null) {
							methodUrl += methodPostMapping.value()[0];
						} else if (methodGetMapping != null) {
							methodUrl += methodGetMapping.value()[0];
						}
						String methodName = method.getName();
						if (!existingUrls.contains(methodUrl)) {
							ApiEndpoint apiEndpoint = new ApiEndpoint();
							apiEndpoint.setUrl(methodUrl);
							apiEndpointRepository.save(apiEndpoint);
						}
					}
				}
			}
		}
	}
}
