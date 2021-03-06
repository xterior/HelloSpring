package com.mycompany.myapp;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.mycompany.myapp.dao.NotificationDAO;
import com.mycompany.myapp.domain.TxnApprovalStatus;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/app")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final int TTL = 255;
	
	@Autowired
	private NotificationDAO notifcationDao;
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws InterruptedException 
	 * @throws ExecutionException 
	 * @throws JoseException 
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	@RequestMapping(value="/app1", method=RequestMethod.GET)
	public String home(Locale locale, Model model) throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		/*String consumerKey = "uyfZ4gDakeXHknpfY4mVtwosp"; // The application's consumer key
		String consumerSecret = "n03eLHN4JbBPRqPw4RZZJt1PsaEfAN5PH7kOoAzwgk1bMePDek"; // The application's consumer secret
		String accessToken = "2770259821-z0dqklgALDUXasEtdiVF9fDU3a9szDw6IYxJEJK"; // The access token granted after OAuth authorization
		String accessTokenSecret = "Q3J1sOzktP4zXsy9fACqkNaCjZ14rhvmTK9NjVwMgmBr6"; // The access token secret granted after OAuth authorization
		Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		CursoredList<TwitterProfile> followers = twitter.friendOperations().getFollowers("NitinG25");
		for(TwitterProfile follower : followers) {
			System.out.println(follower.getScreenName() + ":" + follower.getId());
		}
		
		twitter.directMessageOperations().sendDirectMessage("pheberama", "Hello Testing "  + System.currentTimeMillis());*/
		
		
		
		/*Subscription sub = new Subscription();
		sub.setEndpoint("https://fcm.googleapis.com/fcm/send/eaSUtPQP9Pk:APA91bE7SMP7Zeeq3G88oG0YiwUjodt232RzoK7vJpo40arZGTqDAqyy-8IOEWfpk2elZcEgrHAR9SbKFvgFOZQ4MR5tGzxTmQQ_9_XYiypctKFtH7JRJL7--A2guJv6BfNrCvpxkAeb");
		sub.setKey(new String(Base64.getEncoder().encode(("mfEXhUBv5UgvpaJndbsxrlK3bjWWj2dLpPdzGUnqCms".getBytes()))));
		sub.setAuth("pezxmIEXpWBg-tHngEIDpQ==");
		
		sendPushMessage(sub, "Hello Testing!!".getBytes());*/
		
		sendPushMessage();
		return "home";
	}
	
	/*public void sendPushMessage(Subscription sub, byte[] payload) throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException {

		  // Figure out if we should use GCM for this notification somehow
		  //boolean useGcm = shouldUseGcm(sub);
		boolean useGcm = true;
		
		  Notification notification;
		  PushService pushService;

		  if (useGcm) {
		    // Create a notification with the endpoint, userPublicKey from the subscription and a custom payload
		    notification = new Notification(
		      sub.getEndpoint(),
		      sub.getUserPublicKey(),
		      sub.getAuthAsBytes(),
		      payload
		    );

		    // Instantiate the push service, no need to use an API key for Push API
		    pushService = new PushService();
		  } else {
		    // Or create a GcmNotification, in case of Google Cloud Messaging
		    notification = new Notification(
		      sub.getEndpoint(),
		      sub.getUserPublicKey(),
		      sub.getAuthAsBytes(),
		      payload,
		      TTL
		    );

		    // Instantiate the push service with a GCM API key
		    pushService = new PushService("gcm-api-key");
		  }

		  // Send the notification
		  pushService.send(notification);
		}*/
	
		public void sendPushMessage() throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException {
			Security.addProvider(new BouncyCastleProvider());
			
			PushService pushService = new PushService()
		            .setPublicKey("BMtx9eBN_TyKYIN4ia6TW2ySCVffeH64VkflG8lQTkDss502fm6nSL9blTP9ZBIOQScOWKL6rwO3DzBLq8kD-ys")
		            .setPrivateKey("mfEXhUBv5UgvpaJndbsxrlK3bjWWj2dLpPdzGUnqCms")
		            .setSubject("mailto:admin@domain.com");
			
			//String subscriptionStr = "{'endpoint':'https://fcm.googleapis.com/fcm/send/eaSUtPQP9Pk:APA91bE7SMP7Zeeq3G88oG0YiwUjodt232RzoK7vJpo40arZGTqDAqyy-8IOEWfpk2elZcEgrHAR9SbKFvgFOZQ4MR5tGzxTmQQ_9_XYiypctKFtH7JRJL7--A2guJv6BfNrCvpxkAeb','expirationTime':null,'keys':{'p256dh':'BIEPzraQaQYR5PA8TlCO-bFR56410mQk7qS2KdlJ4FL69PLGFMC2xE05Vbb2hNdDepAZEZ6Bxt4EGV09MFFHXqE','auth':'pezxmIEXpWBg-tHngEIDpQ'}}";
			//String subscriptionStr = "{'endpoint':'https://fcm.googleapis.com/fcm/send/fwF67TxjoFA:APA91bFhbmj3XVxXyp10gckhdl9syZkvqftZNYymjr0zRDUqe5brv-SYyzg1UOwtBesDAgahcp9o1j_xJbOQkEf1OMH-9dLUndQo18AfPo0uLvdzIIzOS3gykcHIlXc0sMW6jBlmj_1b','expirationTime':null,'keys':{'p256dh':'BPtduX2X9hu9bWzMAQQvGvqu7xOHornPQaTAqUP-FC9OkHHaDskWWmEQODtpdlkNj0AhfiUSBv-EUP0Au1mK1vM','auth':'Uo-qI2ajNoT8kpDsAzln6Q'}}";
			//String subscriptionStr = "{'endpoint':'https://fcm.googleapis.com/fcm/send/flLRx4pj-jI:APA91bE0-5P_W989p3CJsVO86SADscFv6WjHQN8cM2da5beBi0Dmf9ID1XPyTUho60JkAdmiRyIgWgEkJNjfYYadvTISYO9y6ml4AqulgsBXZwfkvFQRrKDvhC0LXCX52BG3_t1GLIlB','expirationTime':null,'keys':{'p256dh':'BPL89a053ocRpvHDlVqtjNHZBnELCHfY4esN9JsiX_hvYQVShD2xfg0ZyTjsoqhiOJn0FzfRUZoLMvdF_qlrGjM','auth':'nBE7BHowqXNjsvFa27pjqA'}}";
			//String subscriptionStr = "{'endpoint':'https://fcm.googleapis.com/fcm/send/cILmB-kupuA:APA91bHrzsk_8bdNxuYPuZCM8MYRhh6gNiqNk6zY6g_LbAiTBzXMSevJwPm7b9yUuRpTCLVyl8KR1Hiy1rEDse4bTq6zlLuQWSi4APOHzUr9gcLNVchb1XuYLeODjUPxtVYev0zl4EAz','expirationTime':null,'keys':{'p256dh':'BNDuR5YLkdesstXIIBcLOnyuUiYEe2GyrEdGXawapkNZuVq7GsJyTNoa8tfo4oVyIVRgmkHpJVRswRXrRpn6gcE','auth':'y52BDDM40tcmgzdDRrbInw'}}";
			String subscriptionStr = "{'endpoint':'https://fcm.googleapis.com/fcm/send/cILmB-kupuA:APA91bHrzsk_8bdNxuYPuZCM8MYRhh6gNiqNk6zY6g_LbAiTBzXMSevJwPm7b9yUuRpTCLVyl8KR1Hiy1rEDse4bTq6zlLuQWSi4APOHzUr9gcLNVchb1XuYLeODjUPxtVYev0zl4EAz','expirationTime':null,'keys':{'p256dh':'BNDuR5YLkdesstXIIBcLOnyuUiYEe2GyrEdGXawapkNZuVq7GsJyTNoa8tfo4oVyIVRgmkHpJVRswRXrRpn6gcE','auth':'y52BDDM40tcmgzdDRrbInw'}}";
			Gson gson = new Gson();
			Subscription subscription = gson.fromJson(subscriptionStr, Subscription.class);
			
			Notification notification = new Notification(subscription, "Hello Sudhakar");
			
			HttpResponse response = pushService.send(notification);
		}

		@RequestMapping("/approve")
		public String showApprovalPage(Model model) {
			String page = "approve";
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			String userID = authentication.getName();
			System.out.println(userID);
			
			TxnApprovalStatus txnInfo = notifcationDao.getTransactioInfoByUserId(userID);
			if(null != txnInfo) {
				model.addAttribute("txnInfo", txnInfo);
			} else {
				model.addAttribute("message", "No Active transactions found for the userId");
				page = "error";
			}
				
			
			return page;
		}
		
		@RequestMapping(value="/updateTxnStatus",method= RequestMethod.GET)
		public String txnUpdate(HttpServletRequest req,Model model) {
			String status = req.getParameter("status");
			
			TxnApprovalStatus approvalStatus = new TxnApprovalStatus();
			approvalStatus.setUserId(req.getParameter("userId"));
			approvalStatus.setTxnReqId(req.getParameter("txnNumber"));
			
			if("Approve".equals(status)) {
				approvalStatus.setStatus("A");
			} else if("Decline".equals(status)) {
				approvalStatus.setStatus("D");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			approvalStatus.setTxnApprTime(sdf.format(new Date()));
			notifcationDao.updateTxnInfo(approvalStatus);
			
			model.addAttribute("message",req.getParameter("status"));
			model.addAttribute("txNo",req.getParameter("txnNumber"));
			model.addAttribute("user",req.getParameter("userId"));
			
			
			String page = "Txstatus";
			
			return page;
		}
		
		@RequestMapping(value="/logout", method = RequestMethod.GET)
		public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    if (auth != null){    
		        new SecurityContextLogoutHandler().logout(request, response, auth);
		    }
		    return "welcome";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
		}
		
		/*@RequestMapping(value="/welcome",method= RequestMethod.GET)
		public String welcome(HttpServletRequest req,Model model) {
			return  "welcome";
		}*/
}
