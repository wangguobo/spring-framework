# <img src="src/docs/asciidoc/images/spring-framework.png" width="80" height="80"> Spring Framework

This is the home of the Spring Framework, the foundation for all
[Spring projects](https://spring.io/projects). Together the Spring Framework and the family of Spring projects make up what we call "Spring". 

Spring provides everything you need beyond the Java language to create enterprise
applications in a wide range of scenarios and architectures. Please read the
[Overview](https://docs.spring.io/spring/docs/current/spring-framework-reference/overview.html#spring-introduction)
section in the reference for a more complete introduction.

## forward feature of webflux controller
Current spring webflux(version 5.0.3) doesn't support forward feature in webflux controller. This fork implements this feature,so like spring mvc controller,you 
can use forward feature in webflux controller now.
 
 import org.springframework.ui.Model;  
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
	    System.out.println("/webflux");
		return Mono.just("forward:forward-view");
	}
	
	@GetMapping("forward-view")
	public String index(Model model,ServerWebExchange exchange) {
	    System.out.println("forward-view");
	    model.addAttribute("content", "webflux forward view");
		return "view1";
    }
}    
    
    
    

## License

The Spring Framework is released under version 2.0 of the
[Apache License](http://www.apache.org/licenses/LICENSE-2.0).
