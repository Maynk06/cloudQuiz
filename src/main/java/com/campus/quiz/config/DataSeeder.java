package com.campus.quiz.config;

import com.campus.quiz.model.*;
import com.campus.quiz.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataSeeder {
    @Bean CommandLineRunner seed(UserAccountRepository users,QuizRepository quizzes,PasswordEncoder encoder){return args->{
        if(users.count()>0){users.findByUsername("student1").ifPresent(student->{if("Demo Student".equals(student.getDisplayName())){student.setDisplayName("Student One");users.save(student);}});return;}
        users.save(new UserAccount("student1",encoder.encode("Student@123"),"Student One","STUDENT",null));
        String[][] teachers={{"java_teacher","Java@123"},{"cloud_teacher","Cloud@123"},{"networks_teacher","Networks@123"},{"frontend_teacher","Frontend@123"},{"aptitude_teacher","Aptitude@123"}};
        Subject[] subjects=Subject.values();
        for(int i=0;i<subjects.length;i++)users.save(new UserAccount(teachers[i][0],encoder.encode(teachers[i][1]),subjects[i].getLabel()+" Teacher","TEACHER",subjects[i]));
        String[] titles={"Java Foundations · Practice 01","Cloud Essentials · Practice 01","Network Fundamentals · Practice 01","Frontend Basics · Practice 01","Aptitude Warm-up · Practice 01"};
        for(int i=0;i<subjects.length;i++){
            Quiz quiz=new Quiz(titles[i],subjects[i],LocalDateTime.now().minusMinutes(1),15,true,teachers[i][0]);
            quiz.replaceQuestions(sampleQuestions(subjects[i]));quizzes.save(quiz);
        }
    };}
    private List<QuizQuestion> sampleQuestions(Subject s){
        String[][] rows=switch(s){
            case ADVANCED_JAVA -> new String[][]{
                {"Which Swing component is commonly used for a clickable button?","JButton","JFrame","JPanel","JLabel","0"},
                {"Which AWT layout places components in five named regions?","FlowLayout","GridLayout","BorderLayout","CardLayout","2"},
                {"Which JDBC object is best for parameterized SQL?","Statement","PreparedStatement","ResultSet","DriverManager","1"},
                {"Which call starts a new Java thread?","run()","start()","execute()","launch()","1"},
                {"Which method handles a button click in Swing?","actionPerformed","paintComponent","main","getAction","0"},
                {"What does JDBC primarily provide?","Database connectivity","UI layout","Thread scheduling","File compression","0"},
                {"Which class is a top-level Swing window?","JFrame","JButton","JTextField","JMenuItem","0"},
                {"Which keyword helps prevent concurrent access to a method?","volatile","static","synchronized","native","2"},
                {"Which AWT component displays a single line of text?","Label","Canvas","Scrollbar","Panel","0"},
                {"What does ResultSet hold?","Rows returned by a query","SQL parameters","Database password","Thread state","0"}};
            case CLOUD_COMPUTING -> new String[][]{
                {"Which service model gives users virtual machines and networking?","SaaS","PaaS","IaaS","FaaS","2"},
                {"A provider hosts an application platform for developers. This is:","IaaS","PaaS","On-premises","Colocation","1"},
                {"Which model combines private infrastructure with public cloud?","Community","Hybrid","Public","Edge","1"},
                {"A cloud reserved for one organization is a:","Public cloud","Private cloud","Hybrid cloud","Shared cloud","1"},
                {"What does SLA usually define?","Service commitments","Source code","Storage algorithm","User interface","0"},
                {"Uptime is a measure of:","Availability","CPU speed","Data size","Encryption strength","0"},
                {"Which is an example of SaaS?","Hosted email app","Virtual network","Raw VM","Bare-metal server","0"},
                {"Public cloud resources are generally:","Shared across customers with isolation","Owned by one user only","Always on-premises","Disconnected from internet","0"},
                {"Which deployment model is operated for a group with shared needs?","Community cloud","Private cloud","Public cloud","Local cloud","0"},
                {"A service-level availability target is commonly written as:","Percentage uptime","Screen resolution","Code coverage","Data schema","0"}};
            case COMPUTER_NETWORKS -> new String[][]{
                {"Which layer transmits raw bits over a medium?","Network","Physical","Transport","Application","1"},
                {"Which device forwards frames using MAC addresses?","Switch","Hub","Repeater","Modem","0"},
                {"What is the main purpose of the data link layer?","Node-to-node delivery","Process scheduling","Web rendering","Name resolution","0"},
                {"SNR compares signal power with:","Noise power","Bandwidth","Delay","Packet size","0"},
                {"In stop-and-wait ARQ, the sender sends:","One frame then waits for ACK","All frames at once","Only acknowledgements","No frames","0"},
                {"Go-Back-N may retransmit:","The lost frame and later unacknowledged frames","Only the ACK","Every earlier session","No frames","0"},
                {"Selective Repeat retransmits:","Only missing or damaged frames","All frames in the window","Only the first frame","Every ACK","0"},
                {"Which layer handles logical addressing and routing?","Physical","Data link","Network","Presentation","2"},
                {"A protocol is best described as:","Rules for communication","A network cable","A storage device","A screen layout","0"},
                {"An ACK in reliable delivery indicates:","Successful receipt","Congestion only","A new route","A DNS query","0"}};
            case FRONTEND_ENGINEERING -> new String[][]{
                {"Which HTML element creates a hyperlink?","<a>","<p>","<img>","<div>","0"},
                {"Which CSS property changes text color?","font-style","color","background","display","1"},
                {"Which HTML element is used for a form control?","<input>","<head>","<title>","<meta>","0"},
                {"AJAX lets a page:","Exchange data without a full reload","Compile Java","Style a server","Create a database","0"},
                {"Which JavaScript keyword declares a block-scoped variable?","var","let","define","dim","1"},
                {"Which CSS layout is designed for one-dimensional rows or columns?","Flexbox","Table only","Float","Inline text","0"},
                {"What does DOM stand for?","Document Object Model","Data Output Mode","Digital Order Map","Document Option Method","0"},
                {"Which attribute provides alternative text for an image?","alt","src","href","class","0"},
                {"Which JavaScript method selects an element by id?","getElementById","queryAll","selectClass","findNode","0"},
                {"Which CSS unit is relative to the root font size?","px","rem","cm","pt","1"}};
            case APTITUDE -> new String[][]{
                {"A mixture has milk and water in ratio 3:1. What fraction is water?","1/4","1/3","3/4","1/2","0"},
                {"An item bought for ₹400 is sold for ₹460. Profit percent?","10%","12%","15%","20%","2"},
                {"A finishes a job in 6 days. What fraction is done in one day?","1/6","1/5","1/3","6","0"},
                {"Simplify the ratio 18:24.","2:3","3:4","4:5","6:7","1"},
                {"Cost price ₹500, loss 10%. Selling price?","₹450","₹460","₹490","₹550","0"},
                {"If A does 1/4 of work per day, how many days for the whole job?","2","3","4","5","2"},
                {"Divide 40 in the ratio 3:5. What is the smaller part?","15","18","20","25","0"},
                {"A 20% profit on ₹250 gives selling price:","₹275","₹300","₹320","₹350","1"},
                {"If 5 workers finish a job in 8 days, total worker-days are:","13","35","40","45","2"},
                {"In a 2:3 mixture of 25 litres, the first ingredient is:","10 L","12 L","15 L","20 L","0"}};
        };
        java.util.ArrayList<QuizQuestion> questions=new java.util.ArrayList<>();for(int i=0;i<rows.length;i++){String[] r=rows[i];questions.add(new QuizQuestion(i+1,r[0],r[1],r[2],r[3],r[4],Integer.parseInt(r[5])));}return questions;
    }
}
