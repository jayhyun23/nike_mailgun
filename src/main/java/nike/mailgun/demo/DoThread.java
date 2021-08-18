package nike.mailgun.demo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
public class DoThread {
    static String apiKey = "key-be0a3f714810f7a183aa7bdd7e39c075";
    static String sender = "나이키닷컴 <postmaster@nike.co.kr>";
    static String title ="[NIKE.COM] 개인정보 이용 내역 통지 안내";

    @Async("executor")
    public void run(List<String> to){
        for(String data : to){
            System.out.println(Thread.currentThread().getName() +" => " + data);
        }
        /*for(String target : to){
            try{
                ClientResponse clientResponse = send(target);
                if (clientResponse.getStatusInfo().getStatusCode() != 200) {
                    System.out.println("Fail Taget Eamil = " + target);
                }
            }catch (Exception e) {
                System.out.println("### Mailgun : https Error="+ e);
            }

        }*/
    }

    public ClientResponse send(String target) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", apiKey));
        WebResource webResource = client.resource("https://api.mailgun.net/v3/nike.co.kr/messages");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", sender);
        formData.add("to", target);
        formData.add("subject", title);
        formData.add("template", "customer_info");
        return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
    }


}
