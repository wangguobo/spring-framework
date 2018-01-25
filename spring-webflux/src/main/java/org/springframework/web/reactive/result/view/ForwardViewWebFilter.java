package org.springframework.web.reactive.result.view;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 *  webfilter for forward view{@link WebFilter}.
 *  Same as spring mvc controller,spring webflux controller method can return "forward:url" or
 *  Mono<String> that content is "forward:url".
 *  
 * @author Wang Guobo 
 */
public class ForwardViewWebFilter implements WebFilter {
	public static final String WEBFLUX_FILTERCHAIN_NAME = "spring.webflux.Filterchain"; 
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		if(null == exchange.getAttribute(WEBFLUX_FILTERCHAIN_NAME)) {
			exchange.getAttributes().put(WEBFLUX_FILTERCHAIN_NAME,chain);
		}
		return chain.filter(exchange);
	}
}