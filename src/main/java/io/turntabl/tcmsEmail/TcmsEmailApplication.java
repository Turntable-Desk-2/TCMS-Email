package io.turntabl.tcmsEmail;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class TcmsEmailApplication {


	public static void main(String[] args) {

		SpringApplication.run(TcmsEmailApplication.class, args);

		Jedis jedis = null;

		jedis = new Jedis(System.getenv("REDIS_URL"));

		JedisPubSub jedisPubSub = new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) {
				System.out.println("Channel " + channel + " has sent a message : " + message );
				Gson gson = new Gson();
				Client client = gson.fromJson(message, Client.class);

				System.out.println("Convert Client | " + client);
				Receiver redisMessageSubscriber = new Receiver();
				redisMessageSubscriber.messageListener(client);
			}

			@Override
			public void onSubscribe(String channel, int subscribedChannels) {
				System.out.println("Client is Subscribed to channel : "+ channel);
				System.out.println("Client is Subscribed to "+ subscribedChannels + " no. of channels");
			}
		};

		jedis.subscribe(jedisPubSub, "customers");


	}
}