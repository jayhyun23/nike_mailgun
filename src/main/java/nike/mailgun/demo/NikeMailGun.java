package nike.mailgun.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class NikeMailGun implements CommandLineRunner {

    static List<String> sendToMailList = new ArrayList<>();
    static final int MAX_LIMIT = 1000;

    @Resource DoThread doThread;

    public static void main(String[] args) {
        SpringApplication.run(NikeMailGun.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("### Mailgun Start ###");
        try {
            sendToMailList = getSendListByFile();
            for(int index =0 ;index < sendToMailList.size();){
                int limitSize = index + MAX_LIMIT;
                if (limitSize > sendToMailList.size()) {
                    limitSize = sendToMailList.size();
                }
                List<String> builderList = sendToMailList.subList(index, limitSize);
                doThread.run(builderList);
                index += MAX_LIMIT;
            }

        } catch (Exception e) {
            // 내부 오류 : 메일 발송 서비스를 호출하는 도중 실패한 경우
            System.out.println("### Mailgun : sendMessageByConfiguredProviders Error="+ e);
        }

        System.out.println("### Mailgun End ###");
    }

    private static List<String> getSendListByFile() {
        Path path = Paths.get("C:\\Users\\Jay\\Documents\\test1.csv");
        Charset charset = Charset.forName("UTF-8");
        List<String> list = new ArrayList<>();
        try {
            for(String line : Files.readAllLines(path, charset) ){
                for (String sentence : line.split(",")){
                    list.add(sentence);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

}
