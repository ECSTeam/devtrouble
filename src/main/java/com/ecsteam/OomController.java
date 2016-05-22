package com.ecsteam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;


@Controller
public class OomController {
	
	private static final String MEM_INCREASE_DELAY="MEM_INCREASE_DELAY";

	@Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/oom")
    public String index(Model model) {
    	try {
			this.generateOOM();
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
	        this.template.convertAndSend("/topic/message", sw.toString());
		}
        return "index";
    }

    // This method mostly came from the most excellent blog:
    // http://crunchify.com/how-to-generate-out-of-memory-oom-in-java-programatically/
    //
    // Additions we're adding some additional Thread.sleep() for demo timing of websockets
    // and publishing those messages to the open websocket
    public void generateOOM() throws Exception {
    	
    	String memtimedelay = System.getenv(OomController.MEM_INCREASE_DELAY);
    	if (memtimedelay == null) {
    		memtimedelay = "2"; // a lazy way to set this default if env isn't set
    	}
    	
    	//Map<String, String> env = System.getenv();
        //for (String envName : env.keySet()) {
        //    System.out.format("%s=%s%n",
        //                      envName,
        //                      env.get(envName));
        //}
    	
		Thread.sleep(Long.parseLong(memtimedelay)*1000); // added because the socket isn't done opening in this demo
		int iteratorValue = 20;
		StringBuilder logMessage = new StringBuilder();
		logMessage.append("\n=================> OOM test started..\n");
		logMessage.append("\n==> time delay between memory increase loops = " + memtimedelay + "sec\n");
		System.out.println(logMessage.toString());
        this.template.convertAndSend("/topic/message", logMessage.toString());
		Thread.sleep(1000); // added to read messages on client for demo
		for (int outerIterator = 1; outerIterator < 20; outerIterator++) {
	        logMessage.delete(0, logMessage.length());
			logMessage.append("\nIteration " + outerIterator + " Free Mem: " + Runtime.getRuntime().freeMemory());
			System.out.println(logMessage.toString());
	        this.template.convertAndSend("/topic/message", logMessage.toString());
			Thread.sleep(1000); // added to read messages on client for demo
			int loop1 = 2;
			int[] memoryFillIntVar = new int[iteratorValue];
			do {
				memoryFillIntVar[loop1] = 0;
				loop1--;
			} while (loop1 > 0);
			iteratorValue = iteratorValue * 5;
	        logMessage.delete(0, logMessage.length());
			logMessage.append("\nRequired Memory for next loop: " + iteratorValue);
			System.out.println(logMessage.toString());
	        this.template.convertAndSend("/topic/message", logMessage.toString());
			Thread.sleep(Long.parseLong(memtimedelay)*1000); // added to read messages on client for demo
		}
	}

}
