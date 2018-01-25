package org.springframework.web.reactive.result.view;

import java.util.List;
import org.springframework.http.server.PathContainer.Element;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 *  util for forward view.
 *  Same as spring mvc controller,spring webflux controller method can return "forward:url" or
 *  Mono<String> that content is "forward:url".
 *  
 * @author Wang Guobo 
 * @since 5.0.3
 */
public class ForwardViewUtil {
    public static final String FORWARD_URL_PREFIX = "forward:";
	public static final String THROUGH_URL_PREFIX = "through:";
	public static final String FORWARD_MODE_NAME = "spring.webflux.forward.mode";
	public static final String FORWARD_MODE_REPLAY = "replay";
	public static final String FORWARD_MODE_THROUGH = "through";
	public static final String ABSOLUTE_URL_PREFIX = "/";
	

	public static boolean isForwardView(String viewName) {
		return viewName.startsWith(FORWARD_URL_PREFIX) || viewName.startsWith(THROUGH_URL_PREFIX) ;
	}
	
	public static  Mono<Void> forward(String forwardView,ServerWebExchange originExchange)
	{
		ServerWebExchange forwardExchange = generateForwardExchange(forwardView,originExchange);
		return forwardTo(forwardExchange);
	}
	
	private static  ServerWebExchange generateForwardExchange(String forwardView,ServerWebExchange originExchange)
	{
		String forwardMode = FORWARD_MODE_REPLAY;
		String forwardTargetUrl;
		if(forwardView.startsWith(FORWARD_URL_PREFIX)) {
			forwardTargetUrl = forwardView.substring(FORWARD_URL_PREFIX.length());
		}
		else if(forwardView.startsWith(THROUGH_URL_PREFIX)) {
			forwardTargetUrl = forwardView.substring(THROUGH_URL_PREFIX.length());
			forwardMode = FORWARD_MODE_THROUGH;
		}
		else {
			throw new UnsupportedOperationException("this forward not currently supported");
		}
		
		String absoluteTargetUrl = forwardTargetUrl;
		
		if(!forwardTargetUrl.startsWith(ABSOLUTE_URL_PREFIX)) {
			List<Element> elementList = originExchange.getRequest().getPath().elements();
			int i= 0;
			StringBuilder parentPathBuilder = new StringBuilder("");
			do {
				parentPathBuilder.append(elementList.get(i).value());
			} while (++i < elementList.size()-1);
			absoluteTargetUrl =parentPathBuilder.append(forwardTargetUrl).toString();
		}
		
	    ServerHttpRequest forwardRequest = originExchange.getRequest().mutate().path(absoluteTargetUrl).build();
	    ServerWebExchange forwardWebExchange =  originExchange.mutate().request(forwardRequest).build();
	    forwardWebExchange.getAttributes().put(FORWARD_MODE_NAME, forwardMode);
        return forwardWebExchange;
	}
	
	private static Mono<Void> forwardTo(ServerWebExchange forwardExchange) {
		WebFilterChain webFilterChain = (WebFilterChain)forwardExchange.getAttributes().get(ForwardViewWebFilter.WEBFLUX_FILTERCHAIN_NAME);
		String forwardMode = (String)forwardExchange.getAttribute(FORWARD_MODE_NAME);
		if(FORWARD_MODE_REPLAY.equals(forwardMode) ) {
			return webFilterChain.replayForward(forwardExchange).then();
		}
		else if(FORWARD_MODE_THROUGH.equals(forwardMode)){
			return webFilterChain.throughForward(forwardExchange).then();
		}
		else {
			throw new IllegalArgumentException("this forward mode not currently supported");
		}
	}
	
}
