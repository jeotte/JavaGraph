package com.jessica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jessica.graph.DijkstrasShortestPath;
import com.jessica.graph.ShortestPathHelper;

@Configuration
@ComponentScan("com.jessica")
public class GraphConfig {

	@Bean
	ShortestPathHelper shortestPathHelper() {
		return new DijkstrasShortestPath();
	}
}
