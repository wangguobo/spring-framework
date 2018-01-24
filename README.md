# <img src="src/docs/asciidoc/images/spring-framework.png" width="80" height="80"> Spring Framework

This is the home of the Spring Framework, the foundation for all
[Spring projects](https://spring.io/projects). Together the Spring Framework and the family of Spring projects make up what we call "Spring". 

Spring provides everything you need beyond the Java language to create enterprise
applications in a wide range of scenarios and architectures. Please read the
[Overview](https://docs.spring.io/spring/docs/current/spring-framework-reference/overview.html#spring-introduction)
section in the reference for a more complete introduction.

## View forwarding feature in webflux controller
Current spring webflux(version 5.0.3) doesn't support view forwarding feature in webflux controller,see [spring jira](https://jira.spring.io/browse/SPR-14537).   
This fork implements it,so same as spring mvc controller,you 
can use this feature in webflux controller now.
 
 import org.springframework.stereotype.Controller;   
 import org.springframework.ui.Model;    
 import org.springframework.web.bind.annotation.GetMapping;    
 import org.springframework.web.server.ServerWebExchange;  
 import reactor.core.publisher.Mono; 
 
 @Controller  
 public class WebfluxForwardDemoController {
 	
 	@GetMapping("/")
	public String home(ServerWebExchange exchange) {
	    System.out.println("/");
		return "forward:index";
	}
	
	@GetMapping("index")
	public String index(Model model,ServerWebExchange exchange) {
	    System.out.println("index");
	    model.addAttribute("content", "hello");
		return "index";
    }
    
    @GetMapping("webflux")
	public Mono<String> webflux(ServerWebExchange exchange) {
	    System.out.println("webflux");
		return Mono.just("forward:forward-view");
	}
	
	@GetMapping("forward-view")
	public String index(Model model,ServerWebExchange exchange) {
	    System.out.println("forward-view");
	    model.addAttribute("content", "webflux forward view");
		return "view1";
    }
} 
       
    
Test:  
 curl http://127.0.0.1:8080/  
 curl http://127.0.0.1:8080/webflux    
    

## License

The Spring Framework is released under version 2.0 of the
[Apache License](http://www.apache.org/licenses/LICENSE-2.0).
